package com.ahmetfahriyener.loodos_afy.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmetfahriyener.loodos_afy.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    private static final int LOADING_TIME = 1500;

    private FirebaseAnalytics mFirebaseAnalytics;

    private TextView movie_type;
    private TextView movie_imdbRating;
    private TextView movie_actors;
    private TextView movie_year;
    private TextView movie_genre;
    private TextView movie_runTime;
    private TextView movie_released;
    private TextView movie_title;
    private TextView movie_director;
    private TextView movie_writer;
    private TextView movie_production;
    private TextView movie_plot;

    private ImageView movie_poster;

    private RequestOptions option_poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        movie_type = (TextView) findViewById(R.id.da_movie_type_tv);
        movie_imdbRating = (TextView) findViewById(R.id.da_movie_imdbRating_tv);
        movie_actors = (TextView) findViewById(R.id.da_movie_actors_tv);
        movie_year = (TextView) findViewById(R.id.da_movie_year_tv);
        movie_genre = (TextView) findViewById(R.id.da_movie_genre_tv);
        movie_runTime = (TextView) findViewById(R.id.da_movie_runTime_tv);
        movie_released = (TextView) findViewById(R.id.da_movie_released_tv);
        movie_title = (TextView) findViewById(R.id.da_movie_title_tv);
        movie_director = (TextView) findViewById(R.id.da_movie_director_tv);
        movie_writer = (TextView) findViewById(R.id.da_movie_writer_tv);
        movie_production = (TextView) findViewById(R.id.da_movie_production_tv);
        movie_plot = (TextView) findViewById(R.id.da_movie_plot_tv);
        movie_poster = (ImageView) findViewById(R.id.da_poster_imgv);

        option_poster = new RequestOptions().centerCrop().placeholder(R.drawable.poster_background).error(R.drawable.poster_background);


        String select_imdbID = getIntent().getExtras().getString("select_imdbID");
        String url = "http://www.omdbapi.com/?i="+select_imdbID+"&plot=full&apikey=f5115498";
        requestFunc(url);

    }

    private void requestFunc(String url) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(DetailsActivity.this);

        final CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collap_toolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        StringRequest mStringrequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject details_movie = new JSONObject(response);
                            String result = details_movie.getString("Response");

                            if (result.equals("True"))
                            {
                                String title = details_movie.getString("Title");
                                String year = details_movie.getString("Year");
                                String released = details_movie.getString("Released");
                                String runTime = details_movie.getString("Runtime");
                                String genre = details_movie.getString("Genre");
                                String director = details_movie.getString("Director");
                                String writer = details_movie.getString("Writer");
                                String actors = details_movie.getString("Actors");
                                String plot = details_movie.getString("Plot");
                                String poster = details_movie.getString("Poster");
                                String imdbRating = details_movie.getString("imdbRating");
                                String type = details_movie.getString("Type");
                                String production = details_movie.getString("Production");
                                String imdbID = details_movie.getString("imdbID");



                                movie_title.setText(title);
                                movie_year.setText(year);
                                movie_released.setText(released);
                                movie_runTime.setText(runTime);
                                movie_genre.setText(genre);
                                movie_director.setText(director);
                                movie_writer.setText(writer);
                                movie_actors.setText(actors);
                                movie_plot.setText(plot);
                                movie_imdbRating.setText(imdbRating);
                                movie_type.setText(type);
                                movie_production.setText(production);

                                // Firebase Analytics Loglama için fonsiyona gönderiyorum
                                firebase_analytics_log(imdbID, title, type);

                                collapsingToolbarLayout.setTitle(title);
                                Glide.with(DetailsActivity.this).load(poster).apply(option_poster).into(movie_poster);
                                dismisLoadingActivity();
                            }
                            else
                            {
                                Toast.makeText(DetailsActivity.this,"No movie found!", Toast.LENGTH_LONG).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(mStringrequest);

    }

    public void dismisLoadingActivity() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                LoadingActivity.getInstance().finish();
            }
        }, LOADING_TIME);
    }
    public void firebase_analytics_log(String imdbID, String title, String type)
    {
        Bundle bundle = new Bundle();
        bundle.putString("imdbID", imdbID);
        bundle.putString("Movie_Title", title);
        bundle.putString("MOvie_Type", type);
        mFirebaseAnalytics.logEvent("Movie", bundle);
    }
}

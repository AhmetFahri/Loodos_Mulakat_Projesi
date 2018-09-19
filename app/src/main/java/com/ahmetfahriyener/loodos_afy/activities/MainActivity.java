package com.ahmetfahriyener.loodos_afy.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.ahmetfahriyener.loodos_afy.R;
import com.ahmetfahriyener.loodos_afy.adapters.MainMovieAdapter;
import com.ahmetfahriyener.loodos_afy.models.MainMovie;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.FoldingCube;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.app.AlertDialog.*;


public class MainActivity extends AppCompatActivity {

    private static final int LOADING_TIME = 1500;

    private RequestQueue mRequestQueue;
    private EditText search_et;
    private LinearLayout search_button;
    //private ImageView search_image;
    private JsonObjectRequest mJsonObjectRequest;
    private List<MainMovie> movieList;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        search_et = (EditText) findViewById(R.id.search_et);
        search_button = (LinearLayout) findViewById(R.id.search_button);
        //search_image = (ImageView) findViewById(R.id.search_image);
        recyclerView = findViewById(R.id.recyclerview_movies);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLoadingActivity();
                String searched_word = "";
                searched_word = search_et.getText().toString().trim();
                if (searched_word.isEmpty()) {
                    dismisLoadingActivity();
                    Toast.makeText(MainActivity.this, "Please don't leave it blank", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    String url = "http://www.omdbapi.com/?s=" + searched_word + "&plot=full&apikey=f5115498";
                    jsonRequestFunc(url);
                }
            }
        });
    }

    private void jsonRequestFunc(String url) {
        movieList.clear();
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray mJsonArray = response.getJSONArray("Search");

                            for (int i = 0; i < mJsonArray.length(); i++) {
                                JSONObject mJsonObject = mJsonArray.getJSONObject(i);
                                MainMovie movie = new MainMovie();
                                movie.setTitle(mJsonObject.getString("Title"));
                                movie.setYear(mJsonObject.getString("Year"));
                                movie.setType(mJsonObject.getString("Type"));
                                movie.setImdbID(mJsonObject.getString("imdbID"));
                                movie.setPoster(mJsonObject.getString("Poster"));
                                movieList.add(movie);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(MainActivity.this, "No movie found!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        setupRecyclerView(movieList);
                        dismisLoadingActivity();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue = Volley.newRequestQueue(MainActivity.this);
        mRequestQueue.add(mJsonObjectRequest);
    }

    private void setupRecyclerView(List<MainMovie> movieList) {

        MainMovieAdapter mMainMovieAdapter = new MainMovieAdapter(this, movieList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mMainMovieAdapter);
    }

    public void showLoadingActivity() {
        Intent intent = new Intent(MainActivity.this, LoadingActivity.class);
        startActivity(intent);
    }

    public void dismisLoadingActivity() {

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                LoadingActivity.getInstance().finish();
            }
        }, LOADING_TIME);


    }
    @Override
    public void onBackPressed() {
        new Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        MainActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}
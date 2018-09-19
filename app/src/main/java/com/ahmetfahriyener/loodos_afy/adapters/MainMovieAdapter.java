package com.ahmetfahriyener.loodos_afy.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmetfahriyener.loodos_afy.R;
import com.ahmetfahriyener.loodos_afy.activities.DetailsActivity;
import com.ahmetfahriyener.loodos_afy.activities.LoadingActivity;
import com.ahmetfahriyener.loodos_afy.activities.MainActivity;
import com.ahmetfahriyener.loodos_afy.models.MainMovie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class MainMovieAdapter extends RecyclerView.Adapter<MainMovieAdapter.MyViewHolder> {

    private static final int LOADING_TIME = 1500;

    private Context mContext;
    private List<MainMovie> mData;
    RequestOptions option_poster;
    RequestOptions option_background;

    public MainMovieAdapter(Context mContext, List<MainMovie> mData) {
        this.mContext = mContext;
        this.mData = mData;

        //Glide için Request Option
        option_poster = new RequestOptions().centerCrop().placeholder(R.drawable.poster_background).error(R.drawable.poster_background);
        option_background = new RequestOptions().centerCrop().placeholder(R.drawable.poster_background).error(R.drawable.poster_background);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.movie_row_item, null, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        myViewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                intent.putExtra("select_imdbID", mData.get(myViewHolder.getAdapterPosition()).getImdbID());
                mContext.startActivity(intent);
                showLoadingActivity();
            }
        });



        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        myViewHolder.tv_title.setText(mData.get(i).getTitle());
        myViewHolder.tv_year.setText(mData.get(i).getYear());
        myViewHolder.tv_type.setText(mData.get(i).getType());
        //Resim yüklemek için Glide kullanıyorum.

        Glide.with(mContext).load(mData.get(i).getPoster()).apply(option_poster).into(myViewHolder.img_poster);
        Glide.with(mContext).load(mData.get(i).getPoster()).apply(option_background).into(myViewHolder.img_background);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_title;
        TextView tv_year;
        //TextView tv_imdbID;
        TextView tv_type;
        ImageView img_poster;
        ImageView img_background;
        RelativeLayout view_container;




        public MyViewHolder(View itemView){
            super(itemView);

            tv_title = itemView.findViewById(R.id.movie_title_tv);
            tv_year = itemView.findViewById(R.id.movie_year_tv);
            tv_type = itemView.findViewById(R.id.movie_type_tv);
            img_poster = (ImageView) itemView.findViewById(R.id.poster_imgv);
            img_background = (ImageView) itemView.findViewById(R.id.movie_row_background);
            view_container = itemView.findViewById(R.id.container);
        }
    }
    public void showLoadingActivity() {
        Intent intent = new Intent(mContext, LoadingActivity.class);
        mContext.startActivity(intent);
    }
}




















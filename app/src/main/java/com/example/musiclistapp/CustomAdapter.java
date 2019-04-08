package com.example.musiclistapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    private List<MusicDbEntry> musicList;
    private Context context;

    public CustomAdapter(Context context, List<MusicDbEntry> musicList) {
        this.musicList = musicList;
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_layout, parent, false);
        ConstraintLayout cl = itemLayout.findViewById(R.id.cl_item_layout);
        if (cl.getParent() != null) {
            ((ViewGroup) cl.getParent()).removeView(cl);
        }
        return new CustomViewHolder(cl);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        MusicDbEntry music = musicList.get(position);

        holder.tvCollectionName.setText(music.getCollectionName());
        holder.tvTrackPrice.setText(music.getTrackPrice());
        holder.tvArtistName.setText(music.getArtistName());
        holder.clItemView.setOnClickListener(new MediaOnClickListener(context, music.getPreviewUrl()));

        Picasso.get().load(music.getArtworkUrl60()).into(holder.ivMusicPic);
    }


    @Override
    public int getItemCount() {
        return musicList.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMusicPic;
        TextView tvCollectionName;
        TextView tvTrackPrice;
        TextView tvArtistName;
        ConstraintLayout clItemView;

        CustomViewHolder(View itemView) {
            super(itemView);
            clItemView = (ConstraintLayout) itemView;
            ivMusicPic = itemView.findViewById(R.id.iv_entry_pic);
            tvCollectionName = itemView.findViewById(R.id.tv_collection_name);
            tvTrackPrice = itemView.findViewById(R.id.tv_track_price);
            tvArtistName = itemView.findViewById(R.id.tv_artist_name);
        }
    }


    class MediaOnClickListener implements View.OnClickListener{
        Context context;
        String previewUrl;
        MediaPlayer mp = null;

        MediaOnClickListener(Context context, String previewUrl){
            this.context = context;
            this.previewUrl = previewUrl;
        }

        @Override
        public void onClick(View view) {
            Log.d("layout clicked", "prepare to play music ");
            if (MainActivity.INTERNET_AVAILABLE) {
                if(mp == null){
                    mp = new MediaPlayer();
                    mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
                }
                Log.d("mp test", " " + mp.isPlaying());
                if (!mp.isPlaying()) {
                    try {
                        mp.setDataSource(context, Uri.parse(previewUrl));
                        Log.d("music player", "preparing");
                        mp.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mp.start();
                }
                else {
                    mp.stop();
                    mp.reset();
                }
            }
        }
    }
}
package com.example.musiclistapp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {

    MusicApi musicApi;
    String musicType;

    public RetrofitHelper(String musicType) {
        this.musicType = musicType;
        initializeRetrofit();
    }


    public void makeRetrofitCall() {
        Log.d("retrofit helper", "make call type = " + musicType);
        if (musicType.equals("Rock")) {
            musicApi.getRockMusic().enqueue(new Callback<MusicList>() {
                @Override
                public void onResponse(Call<MusicList> call, Response<MusicList> response) {
                    processResponse(response);
                }

                @Override
                public void onFailure(Call<MusicList> call, Throwable t) {
                }
            });
        }

        if (musicType.equals("Classic")) {
            musicApi.getClassicMusic().enqueue(new Callback<MusicList>() {
                @Override
                public void onResponse(Call<MusicList> call, Response<MusicList> response) {
                    processResponse(response);
                }

                @Override
                public void onFailure(Call<MusicList> call, Throwable t) {
                }
            });
        } else if (musicType.equals("Pop")){
            musicApi.getPopMusic().enqueue(new Callback<MusicList>() {
                @Override
                public void onResponse(Call<MusicList> call, Response<MusicList> response) {
                    processResponse(response);
                }

                @Override
                public void onFailure(Call<MusicList> call, Throwable t) {
                }
            });
        }
    }

    private void processResponse(Response<MusicList> response) {
        Realm realm = Realm.getDefaultInstance();
        List<Music> musicList = response.body().getMusics();


        for (int i = 0; i < musicList.size(); i++) {
            // realm db entry
            Music current = musicList.get(i);
            try {
                if (current.getArtistName() != null
                        && current.getArtworkUrl60() != null
                        && current.getTrackPrice() != null
                        && current.getPreviewUrl() != null
                        && current.getCollectionName() != null) {
                    if (!realm.isInTransaction()) {
                        realm.beginTransaction();
                    }
                    MusicDbEntry entry = realm.createObject(MusicDbEntry.class);
                    entry.setMusicType(musicType);
                    entry.setCollectionName(current.getCollectionName());
                    entry.setArtistName(current.getArtistName());
                    entry.setArtworkUrl60(current.getArtworkUrl60());
                    entry.setTrackPrice(current.getTrackPrice().toString());
                    entry.setPreviewUrl(current.getPreviewUrl());
                    realm.insert(entry);
                    realm.commitTransaction();
                }
            }
            catch (RealmPrimaryKeyConstraintException e) {
                Log.d("entry exception: ", "  existing primary key " + String.valueOf(i));
            }

        }
    }


    private void initializeRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        musicApi = retrofit.create(MusicApi.class);
    }


}

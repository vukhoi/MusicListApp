package com.example.musiclistapp;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicApi {
    @GET("search?term=rock&amp;media=music&amp;entity=song&amp;limit=50")
    Call<MusicList> getRockMusic();

    @GET("search?term=pop&amp;media=music&amp;entity=song&amp;limit=50")
    Call<MusicList> getPopMusic();

    @GET("search?term=classick&amp;media=music&amp;entity=song&amp;limit=50")
    Call<MusicList> getClassicMusic();
}

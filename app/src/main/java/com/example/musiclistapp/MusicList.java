package com.example.musiclistapp;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicList {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<Music> results = null;

    public Integer getMusicCount() {
        return resultCount;
    }

    public void setMusicCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<Music> getMusics() {
        return results;
    }

    public void setMusics(List<Music> results) {
        this.results = results;
    }

}
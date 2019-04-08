package com.example.musiclistapp;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MusicDbEntry extends RealmObject {
    @Required
    private String musicType;
    @Required
    private String artistName;
    @Required
    private String artworkUrl60;
    @Required
    private String trackPrice;
    @Required
    private String collectionName;
    @Required
    private String previewUrl;

    public MusicDbEntry(){}

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getMusicType() {
        return musicType;
    }

    public void setMusicType(String type) {
        this.musicType = type;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtworkUrl60() {
        return artworkUrl60;
    }

    public void setArtworkUrl60(String artworkUrl60) {
        this.artworkUrl60 = artworkUrl60;
    }

    public String getTrackPrice() {
        return trackPrice;
    }

    public void setTrackPrice(String trackPrice) {
        this.trackPrice = trackPrice;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}

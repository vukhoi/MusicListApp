package com.example.musiclistapp;


import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


public class ClassicFragment extends Fragment {

    static final String MUSIC_TYPE = "Classic";
    List<MusicDbEntry> dbMusicList = new ArrayList<>();
    RecyclerView recyclerView;
    SwipeRefreshLayout sRL;

    public ClassicFragment() {
        getMusicFromDb();

        if (dbMusicList.size() == 0){
            if (MainActivity.INTERNET_AVAILABLE) {
                RetrofitHelper rH = new RetrofitHelper(this.MUSIC_TYPE);
                rH.makeRetrofitCall();
                getMusicFromDb();
            }
        }
    }

    private void getMusicFromDb() {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<MusicDbEntry> results = realm.where(MusicDbEntry.class).findAll();
        for (MusicDbEntry entry : results) {
            if (entry.getMusicType().equals(MUSIC_TYPE)) {
                dbMusicList.add(entry);
            }
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rock, container, false);
        view.setBackgroundColor(Color.BLUE);
        recyclerView = view.findViewById(R.id.recyclerView);
        sRL = view.findViewById(R.id.swipeRefreshLayout);
        recyclerView.setAdapter(new CustomAdapter(getContext(), dbMusicList));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        sRL.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().post(new Runnable() {
                    @Override public void run() {
                        sRL.setRefreshing(false);
                        RetrofitHelper rH = new RetrofitHelper(ClassicFragment.MUSIC_TYPE);
                        rH.makeRetrofitCall();
                        getMusicFromDb();
                    }
                });

            }
        });

        return view;
    }

}

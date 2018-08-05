package com.example.sirisha.bakeguide;

import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sirisha.bakeguide.dummy.DummyContent;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class ItemDetailFragment extends Fragment {
    ArrayList<IngredientsModel> ingredietModel;
    TextView textView;
    String description;
    String videourl;
    String thumbnail;
    Uri videoURI;
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView exoPlayerView;

    public ItemDetailFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (exoPlayer != null) {
            outState.putLong("pos", exoPlayer.getCurrentPosition());
            outState.putBoolean("play_back", exoPlayer.getPlayWhenReady());
        }
    }

    @Override
    public void onPause() {

        if (exoPlayer!=null){
            exoPlayer.release();
        }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (exoPlayer!=null){
            exoPlayer.stop();
        }
        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey("ingredients")) {
            ingredietModel = getArguments().getParcelableArrayList("ingredients");
        }
        if (getArguments().containsKey("video")) {
            description = getArguments().getString("description");
            videourl = getArguments().getString("video");
            thumbnail = getArguments().getString("thumbnail");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        textView = (TextView) rootView.findViewById(R.id.item_detail);
        textView.setFocusable(false);
        if (getArguments().containsKey("ingredients")) {
            for (int i = 0; i < ingredietModel.size(); i++) {
                textView.append("Ingredients:-" + ingredietModel.get(i).getIngredient() + "\n");
                textView.append("Quantity:-" + ingredietModel.get(i).getQuantity() + "\n");
                textView.append("Measure:-" + ingredietModel.get(i).getMeasure() + "\n");
            }
        }
        if (getArguments().containsKey("video")) {
            textView.setText("Description:"+ description);
            exoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exo_player);
            try {
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelector trackSelector = new
                        DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
                exoPlayerView.setVisibility(View.VISIBLE);
                videoURI = Uri.parse(videourl);
                if (videourl.equals("")) {
                    videoURI = Uri.parse(thumbnail);
                }

                if (!(videourl.isEmpty() && thumbnail.isEmpty())) {
                    DefaultHttpDataSourceFactory dataSourceFactory =
                            new DefaultHttpDataSourceFactory("sirisha");
                    ExtractorsFactory extractorsFactory = new
                            DefaultExtractorsFactory();
                    MediaSource mediasource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);
                    exoPlayerView.setPlayer(exoPlayer);
                    exoPlayer.prepare(mediasource);
                    exoPlayer.setPlayWhenReady(false);
                    if (savedInstanceState != null) {
                        exoPlayer.setPlayWhenReady(savedInstanceState
                                .getBoolean("play_back"));
                        exoPlayer.seekTo(savedInstanceState.getLong("pos"));
                    }

                } else {
                    exoPlayerView.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rootView;
    }

}

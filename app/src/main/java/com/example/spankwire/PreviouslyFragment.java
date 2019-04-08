package com.example.spankwire;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spankwire.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviouslyFragment extends Fragment implements View.OnClickListener {

    TextView name;
    TextView duration;
    TextView description;
    TextView viewed;
    TextView categories;
    TextView categoriesOne;
    TextView categoriesTwo;
    TextView categoriesThree;
    TextView categoriesFour;
    TextView categoriesFive;
    TextView categoriesSix;
    ImageView poster;
    VideoItems videoItem;
    private PornoVideosFragment.OnFragmentInteractionListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.previously_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name = view.findViewById(R.id.name_previously);
        duration = view.findViewById(R.id.duration_previously);
        description = view.findViewById(R.id.description_previously);
        viewed = view.findViewById(R.id.viewed_previously);
        categories = view.findViewById(R.id.categories);
        poster = view.findViewById(R.id.poster_previously);
        poster.setOnClickListener(this);

        categoriesOne = view.findViewById(R.id.categories_previously_one);
        categoriesOne.setOnClickListener(this);
        categoriesTwo = view.findViewById(R.id.categories_previously_two);
        categoriesTwo.setOnClickListener(this);
        categoriesThree = view.findViewById(R.id.categories_previously_three);
        categoriesThree.setOnClickListener(this);
        categoriesFour = view.findViewById(R.id.categories_previously_four);
        categoriesFour.setOnClickListener(this);
        categoriesFive = view.findViewById(R.id.categories_previously_five);
        categoriesFive.setOnClickListener(this);
        categoriesSix = view.findViewById(R.id.categories_previously_six);
        categoriesSix.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void getVideoUrl(int videoId) {

        NetworkService.getService().getJsonApi().getVideo(videoId).enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null) {
                    VideoItems videoItems = response.body();
                    videoItem = videoItems;
                    setItems(videoItems);
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("TEST", "Clik me exe" + t.toString());
            }
        });
    }

    @SuppressLint("SimpleDateFormat")
    private void setItems(VideoItems videoItems) {

        name.setText(videoItems.title);
        duration.setText(String.valueOf(new SimpleDateFormat("HH:mm:ss").
                format(new Date(TimeUnit.SECONDS.toMillis(videoItems.duration)))));
        description.setText(videoItems.description);
        viewed.setText(videoItems.viewed);
        getNameCategories(videoItems);
        Picasso.get().load(videoItems.poster2x).into(poster);
    }

    private void getNameCategories(VideoItems videoItems) {
        if (videoItems.categories.size() == 1) {
            categoriesOne.setText(videoItems.categories.get(0).name);
        } else if (videoItems.categories.size() == 2) {
            categoriesOne.setText(videoItems.categories.get(0).name.concat(","));
            categoriesTwo.setText(videoItems.categories.get(1).name);
        } else if (videoItems.categories.size() == 3) {
            categoriesOne.setText(videoItems.categories.get(0).name.concat(","));
            categoriesTwo.setText(videoItems.categories.get(1).name.concat(","));
            categoriesThree.setText(videoItems.categories.get(2).name);
        } else if (videoItems.categories.size() == 4) {
            categoriesOne.setText(videoItems.categories.get(0).name.concat(","));
            categoriesTwo.setText(videoItems.categories.get(1).name.concat(","));
            categoriesThree.setText(videoItems.categories.get(2).name.concat(","));
            categoriesFour.setText(videoItems.categories.get(3).name);
        } else if (videoItems.categories.size() == 5) {
            categoriesOne.setText(videoItems.categories.get(0).name.concat(","));
            categoriesTwo.setText(videoItems.categories.get(1).name.concat(","));
            categoriesThree.setText(videoItems.categories.get(2).name.concat(","));
            categoriesFour.setText(videoItems.categories.get(3).name.concat(","));
            categoriesFive.setText(videoItems.categories.get(4).name);
        } else if (videoItems.categories.size() == 6) {
            categoriesOne.setText(videoItems.categories.get(0).name.concat(","));
            categoriesTwo.setText(videoItems.categories.get(1).name.concat(","));
            categoriesThree.setText(videoItems.categories.get(2).name.concat(","));
            categoriesFour.setText(videoItems.categories.get(3).name.concat(","));
            categoriesFive.setText(videoItems.categories.get(4).name.concat(","));
            categoriesSix.setText(videoItems.categories.get(5).name);
        }
    }

    private void setVideoUrl(String urlVideo, String title) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(Uri.parse(urlVideo), "video/*");
        startActivity(Intent.createChooser(intent, title));
    }

    @Override
    public void onClick(View v) {
        try {
            if (videoItem != null) {
                switch (v.getId()) {
                    case R.id.categories_previously_one:
                        listener.onFragmentInteraction(videoItem.categories.get(0).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.categories_previously_two:
                        listener.onFragmentInteraction(videoItem.categories.get(1).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.categories_previously_three:
                        listener.onFragmentInteraction(videoItem.categories.get(2).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.categories_previously_four:
                        listener.onFragmentInteraction(videoItem.categories.get(3).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.categories_previously_five:
                        listener.onFragmentInteraction(videoItem.categories.get(4).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.categories_previously_six:
                        listener.onFragmentInteraction(videoItem.categories.get(5).url);
                        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
                        break;
                    case R.id.poster_previously:
                        if (videoItem.videos.quality_720p != null) {
                            setVideoUrl(videoItem.videos.quality_720p,videoItem.title);

                        } else if (videoItem.videos.quality_480p != null) {
                            setVideoUrl(videoItem.videos.quality_480p,videoItem.title);

                        } else if (videoItem.videos.quality_240p != null) {
                            setVideoUrl(videoItem.videos.quality_240p,videoItem.title);

                        } else if (videoItem.videos.quality_180p != null) {
                            setVideoUrl(videoItem.videos.quality_180p,videoItem.title);
                        }
                }
            }
        } catch (Exception e) {
            Log.d("TEST", e.toString());
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PornoVideosFragment.OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }
}

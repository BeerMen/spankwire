package com.example.spankwire;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spankwire.adapters.RecyclerAdapter;
import com.example.spankwire.network.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PornoVideosFragment extends Fragment implements RecyclerAdapter.ItemClickListener {

    private RecyclerView recyclerView;
    protected RecyclerAdapter recyclerAdapter;
    private VideoItems videoItems;
    private OnFragmentInteractionListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.porno_videos_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new RecyclerAdapter();
        recyclerAdapter.setClickListener(this);
        recyclerView = view.findViewById(R.id.recycler_porno_videos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListVideoPopularHome();
    }

    public void getListVideoPopularHome() {
        NetworkService.getService().getJsonApi().getHomeVideo("Straight",
                100, 1, "Relevance").enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null) {
                    setVideoRecyclerView(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }

    public void getListVideoTopRated() {
        NetworkService.getService().getJsonApi().getTopRated("straight",
                100, 1,
                "rating",
                "Relevance",
                "all_time").enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null) {
                    setVideoRecyclerView(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }

    public void getListVideoCategories(int idCategories) {
        NetworkService.getService().
                getJsonApi().
                getVideoCategories("straight",
                        100, 1,
                        "rating",
                        "Relevance",
                        "all_time", idCategories)
                .enqueue(new Callback<VideoItems>() {
                    @Override
                    public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                        if (response.body() != null){
                            setVideoRecyclerView(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoItems> call, Throwable t) {
                        Log.d("DBAGME", t.toString());
                    }
                });
    }

    public void getListPornoStars(int id) {

        NetworkService.getService().getJsonApi().getPornoStarListVideo(id,
                1,
                "recent",
                100).enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null){
                    setVideoRecyclerView(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }

    public void setVideoRecyclerView(VideoItems videoItems) {
        List<String> posterUrl = new ArrayList<>();
        List<String> title = new ArrayList<>();
        List<Float> rating = new ArrayList<>();

        this.videoItems = videoItems;
        if (videoItems != null) {
            for (int i = 0; i < videoItems.items.size(); i++) {
                posterUrl.add(videoItems.items.get(i).poster2x);
                title.add(videoItems.items.get(i).title);
                rating.add(videoItems.items.get(i).rating);
            }

            recyclerView.post(() -> {
                recyclerAdapter.clear();
                recyclerAdapter.addUrls(posterUrl, title, rating);
            });
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(1);
        listener.onFragmentInteraction(videoItems.items.get(position).videoId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс");
        }
    }


    interface OnFragmentInteractionListener {

        void onCategoriesInteraction(int id);

        void onFragmentInteraction(int idUrl);

        void onFragmentInteraction(String ulr);
    }
}

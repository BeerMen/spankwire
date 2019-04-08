package com.example.spankwire;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spankwire.adapters.RecyclerAdapterPornoStar;
import com.example.spankwire.network.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PornoStarFragment extends Fragment implements RecyclerAdapterPornoStar.ItemClickListenerPornoStar {

    private RecyclerView recyclerView;
    protected RecyclerAdapterPornoStar recyclerAdapter;
    private OnPornoStarsInteractionListener listener;
    private VideoItems videoItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.porno_star_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerAdapter = new RecyclerAdapterPornoStar();
        recyclerAdapter.setClickListener(this);
        recyclerView = view.findViewById(R.id.recycler_porno_star);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void getListPornoStars() {
        NetworkService.getService().getJsonApi().getPornoStarsList(1,
                "popular",
                100).enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null){
                    videoItems = response.body();
                    setPornoStars(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }

    private void setPornoStars(VideoItems videoItems) {
        List<String> posterUrl = new ArrayList<>();
        List<String> name = new ArrayList<>();
        List<Integer> videos = new ArrayList<>();

        for (int i = 0; i < videoItems.items.size(); i++) {
            posterUrl.add(videoItems.items.get(i).thumb);
            name.add(videoItems.items.get(i).name);
            videos.add(videoItems.items.get(i).videos);
        }
        recyclerView.post(() -> {
            recyclerAdapter.clear();
            recyclerAdapter.addUrlsAndNames(posterUrl, name, videos);
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PornoStarFragment.OnPornoStarsInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс");
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
        listener.onPornoStarsInteraction(videoItems.items.get(position).id);
    }

    interface OnPornoStarsInteractionListener {

        void onPornoStarsInteraction(int id);
    }
}

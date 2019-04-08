package com.example.spankwire;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.spankwire.adapters.RecyclerAdapter;
import com.example.spankwire.adapters.RecyclerAdapterCategories;
import com.example.spankwire.network.NetworkService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment implements RecyclerAdapterCategories.ItemClickListenerCategories{
    private RecyclerView recyclerView;
    protected RecyclerAdapterCategories recyclerAdapter;
    private VideoItems videoItems;
    private PornoVideosFragment.OnFragmentInteractionListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.categories_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerAdapter = new RecyclerAdapterCategories();
        recyclerAdapter.setClickListener(this);
        recyclerView = view.findViewById(R.id.recycler_categories);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void getListCategories(){
        NetworkService.getService().getJsonApi().getCategoriesList(1,
                0,
                "recent",
                100).enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null){
                    videoItems = response.body();
                    setCategories(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }
    private void setCategories(VideoItems videoItems){
        List<String> posterUrl = new ArrayList<>();
        List<String> name = new ArrayList<>();
        for (int i = 0; i < videoItems.items.size(); i++) {
            posterUrl.add(videoItems.items.get(i).image);
            name.add(videoItems.items.get(i).name);
        }
        recyclerView.post(() -> {
            recyclerAdapter.clear();
            recyclerAdapter.addUrlsAndNames(posterUrl, name);
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        ((MainActivity) Objects.requireNonNull(getActivity())).visibilityFragments(2);
        listener.onCategoriesInteraction(videoItems.items.get(position).id);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (PornoVideosFragment.OnFragmentInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс");
        }
    }
}

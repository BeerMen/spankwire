package com.example.spankwire;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spankwire.network.NetworkService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreviouslyPornoStarsFragment extends Fragment implements View.OnClickListener {

    TextView name;
    TextView born;
    TextView height;
    TextView weight;
    TextView hair;
    TextView description;
    ImageView poster;
    Button video;
    private OnPreviouslyPornoStarsInteractionListener listener;
    private PornoStar pornoStar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.previously_porno_stars_fragmetn,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        name = view.findViewById(R.id.previously_porno_stars_name);
        born = view.findViewById(R.id.previously_porno_stars_born);
        height = view.findViewById(R.id.previously_porno_stars_height);
        weight = view.findViewById(R.id.previously_porno_stars_weight);
        hair = view.findViewById(R.id.previously_porno_stars_hair);
        description = view.findViewById(R.id.previously_porno_stars_description);
        poster = view.findViewById(R.id.previously_porno_stars_poster);
        video = view.findViewById(R.id.previously_porno_stars_video);
        video.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public void getInfoPornoStars(int id){

        NetworkService.getService().getJsonApi().getInfoPornoStar(id).enqueue(new Callback<PornoStar>() {
            @Override
            public void onResponse(Call<PornoStar> call, Response<PornoStar> response) {
                pornoStar = response.body();
                setView(response.body());
            }

            @Override
            public void onFailure(Call<PornoStar> call, Throwable t) {

                Log.d("TEST", t.toString());
            }
        });
    }
    private void setView(PornoStar pornoStar){
        name.setText(pornoStar.name);
        born.setText(pornoStar.birthday);
        height.setText(pornoStar.height);
        weight.setText(pornoStar.weight);
        hair.setText(pornoStar.hair_color);
        description.setText(pornoStar.description);

        Picasso.get().load(pornoStar.thumb).into(poster);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnPreviouslyPornoStarsInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getActivity()).visibilityFragments(2);
        listener.onPreviouslyPornoStarsInteraction(pornoStar.id);
    }

    interface OnPreviouslyPornoStarsInteractionListener {

        void onPreviouslyPornoStarsInteraction(int id);
    }
}

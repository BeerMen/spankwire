package com.example.spankwire.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spankwire.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterPornoStar extends RecyclerView.Adapter<RecyclerAdapterPornoStar.PornoStarViewHolder>{

    private List<String> names = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private List<Integer> videos = new ArrayList<>();
    private ItemClickListenerPornoStar listener;

    public void addUrlsAndNames(List<String> urls,List<String> names,List<Integer> videos) {
        this.urls.addAll(urls);
        this.names.addAll(names);
        this.videos.addAll(videos);
        notifyDataSetChanged();
    }

    public void clear(){
        urls.clear();
        names.clear();
        videos.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PornoStarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PornoStarViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycler_item_porno_star,
                        viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PornoStarViewHolder pornoStarViewHolder, int i) {

        pornoStarViewHolder.bind(urls.get(i),names.get(i),videos.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class PornoStarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView title;
        TextView name;
        TextView videos;

        private PornoStarViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.poster_porno_star);
            name = itemView.findViewById(R.id.name_porno_star);
            videos = itemView.findViewById(R.id.videos_porno_star);
            itemView.setOnClickListener(this);
        }
        private void bind(String urlTitle, String name,int videos){
            Picasso.get().load(urlTitle).into(title);
            this.name.setText(name);
            String s = videos + " Videos";
            this.videos.setText(s);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                listener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public void setClickListener(ItemClickListenerPornoStar itemClickListener) {
        this.listener = itemClickListener;
    }

    public interface ItemClickListenerPornoStar {
        void onItemClick(View view, int position);
    }
}

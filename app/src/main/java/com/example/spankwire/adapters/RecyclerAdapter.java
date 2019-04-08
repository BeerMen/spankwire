package com.example.spankwire.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.spankwire.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PornoViewHolder>{

    private List<String> urls = new ArrayList<>();
    private List<String> title = new ArrayList<>();
    private List<Float> rating = new ArrayList<>();
    private ItemClickListener clickListener;

    public void addUrls(List<String> urls,List<String> title,List<Float> rating) {
        this.urls.addAll(urls);
        this.title.addAll(title);
        this.rating.addAll(rating);
        notifyDataSetChanged();
    }

    public void clear(){
        urls.clear();
        title.clear();
        rating.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PornoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PornoViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PornoViewHolder pornoViewHolder, int i) {
        pornoViewHolder.bind(urls.get(i),title.get(i),rating.get(i));
    }

    @Override
    public int getItemCount() {
        return urls.size();
    }

    class PornoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView imageView;
        TextView titleTextView;
        TextView ratingTextView;

        private PornoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster);
            titleTextView = itemView.findViewById(R.id.title);
            ratingTextView = itemView.findViewById(R.id.rating);
            itemView.setOnClickListener(this);
        }
        void bind(String imageUrl, String title, Float rating){
            Picasso.get().load(imageUrl).into(imageView);
            titleTextView.setText(title);
            ratingTextView.setText(String.valueOf(rating));
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

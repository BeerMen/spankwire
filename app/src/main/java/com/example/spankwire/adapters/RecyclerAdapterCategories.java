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

public class RecyclerAdapterCategories extends RecyclerView.Adapter<RecyclerAdapterCategories.CategoriesViewHolder> {

    private List<String> names = new ArrayList<>();
    private List<String> urls = new ArrayList<>();
    private ItemClickListenerCategories clickListener;

    public void addUrlsAndNames(List<String> urls,List<String> names) {
        this.urls.addAll(urls);
        this.names.addAll(names);
        notifyDataSetChanged();
    }

    public void clear(){
        urls.clear();
        names.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CategoriesViewHolder(LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.recycler_item_categories,
                viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder categoriesViewHolder, int i) {
        categoriesViewHolder.bind(urls.get(i),names.get(i));
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView title;
        TextView name;

        private CategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.poster_categories);
            name = itemView.findViewById(R.id.name_categories);
            itemView.setOnClickListener(this);
        }
        private void bind(String url, String name){
            Picasso.get().load(url).into(title);
            this.name.setText(name);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                Log.d("TEST", "CLICK POSITION recycler");
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public void setClickListener(ItemClickListenerCategories itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public interface ItemClickListenerCategories {
        void onItemClick(View view, int position);
    }
}

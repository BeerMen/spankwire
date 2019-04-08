package com.example.spankwire.network;

import android.content.Context;
import android.util.Log;

import com.example.spankwire.VideoItems;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchNetwork {
    private OnSearchInteractionListener listener;

//    public void getResult(String key) {
//        new NetworkService(ConstantApi.SEARCH_URL).getJsonApi().getSearchResult(key).enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                String body = response.body();
//
//                List<String> items = Arrays.asList(body.split("\\s*,\\s*"));
//                List<String> test = new ArrayList<>(items);
//                for (int i = 0; i < test.size(); i++) {
//                    if (i == 0) {
//                        test.set(i, test.get(i).substring(2, test.get(i).length() - 1));
//                    } else if (i == test.size() - 1) {
//                        test.set(i, test.get(i).substring(1, test.get(i).length() - 2));
//                    } else {
//                        test.set(i, test.get(i).substring(1, test.get(i).length() - 1));
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//
//            }
//        });
//    }

    public void initListener(Context context) {
        try {
            listener = (OnSearchInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс");
        }
    }

    public void setResult(String category) {
        NetworkService.getService().getJsonApi().getVideoCategoriesSearch("straight",
                100,
                1,
                "recent",
                "Relevance",
                "all_time",
                category).enqueue(new Callback<VideoItems>() {
            @Override
            public void onResponse(Call<VideoItems> call, Response<VideoItems> response) {
                if (response.body() != null) {
                    listener.onSearchInteraction(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoItems> call, Throwable t) {
                Log.d("DBAGME", t.toString());
            }
        });
    }

    public interface OnSearchInteractionListener {

        void onSearchInteraction(VideoItems result);
    }
}
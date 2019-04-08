package com.example.spankwire.network;

import com.example.spankwire.PornoStar;
import com.example.spankwire.VideoItems;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PlaceHolderApi {

    @GET("/api/video/popular.json")
    Call<VideoItems> getHomeVideo(@Query("segment") String straight,
                                  @Query("limit") int limit,
                                  @Query("page") int page,
                                  @Query("sort") String Relevance);

    @GET("api/video/list.json")
    Call<VideoItems> getTopRated(@Query("segment") String straight,
                                 @Query("limit") int limit,
                                 @Query("page") int page,
                                 @Query("sortby") String rating,
                                 @Query("sort") String Relevance,
                                 @Query("period") String all_time);

    @GET("/api/video/{idVideo}.json")
    Call<VideoItems> getVideo(@Path("idVideo") int idVideo);

    @GET("api/video/list.json")
    Call<VideoItems> getVideoCategories(@Query("segment") String straight,
                                        @Query("limit") int limit,
                                        @Query("page") int page,
                                        @Query("sortby") String rating,
                                        @Query("sort") String Relevance,
                                        @Query("period") String all_time,
                                        @Query("category") int category);

    @GET("api/categories/list.json")
    Call<VideoItems> getCategoriesList(@Query("page") int page,
                                       @Query("segmentId") int segmentId,
                                       @Query("sort") String recent,
                                       @Query("limit") int limit);

    @GET("api/pornstars")
    Call<VideoItems> getPornoStarsList(@Query("page") int page,
                                       @Query("sort") String popular,
                                       @Query("limit") int limit);

    @GET("api/video/list.json")
    Call<VideoItems> getPornoStarListVideo(@Query("pornstarId") int id,
                                           @Query("page") int page,
                                           @Query("sortby") String recent,
                                           @Query("limit") int limit);

    @GET("api/pornstars/{id}")
    Call<PornoStar> getInfoPornoStar(@Path("id") int id);

    @GET("/autosuggest")
    Call<String> getSearchResult(@Query("key") String kay);

    @GET("api/video/list.json")
    Call<VideoItems> getVideoCategoriesSearch(@Query("segment") String straight,
                                        @Query("limit") int limit,
                                        @Query("page") int page,
                                        @Query("sortby") String rating,
                                        @Query("sort") String Relevance,
                                        @Query("period") String all_time,
                                        @Query("category") String category);

}

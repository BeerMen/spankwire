package com.example.spankwire;

import java.util.List;

public class VideoItems {

    public List<Items> items;
    public List<Categories> categories;
    public Items.Videos videos;
    public int duration;
    public String viewed;
    public float rating;
    public String title;
    public String description;
    public String poster2x;
}
class Categories {
    String name;
    String url;
}

class Items {
    float rating;
    int videoId;
    String title;
    public String poster2x;
    String name;
    int id;
    int videosNumber;
    String image;
    String url;
    String thumb;
    int videos;


    class Videos {
        String quality_180p;
        String quality_240p;
        String quality_480p;
        String quality_720p;
    }
}

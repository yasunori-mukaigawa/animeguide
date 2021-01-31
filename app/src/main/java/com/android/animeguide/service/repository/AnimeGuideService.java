package com.android.animeguide.service.repository;

import com.android.animeguide.service.model.AnimeInfo;
import com.android.animeguide.service.model.ScreenShotInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeGuideService {

    //Retrofitインターフェース(APIリクエストを管理)
    String HTTPS_API_ANIME_URL = "http://api.moemoe.tokyo/anime/v1/master/";
    String HTTPS_API_SCREENSHOT_URL = "http://snap-api.1-one.one:8080";
    String HTTPS_API_ANNICT = "https://api.annict.com/v1/works";
    String TOAKEN = "hISjgZ1iFjUJQwB9NG_qBunfeZey97BDmpK3_TvkZZU";

    @GET("{year}/{id}")
    Call<List<AnimeInfo>> getAnimeInfoList(@Path("year") int year, @Path("id") int id);

    @Headers("Aniapp: test")
    @GET("/")
    Call<ScreenShotInfo> getScreenShot(@Query("url") String url);

    @Headers("Authorization: Bearer " + TOAKEN)
    @GET("/")
    Call<ScreenShotInfo> getAnnict(@Query("filter_title") String title);
}
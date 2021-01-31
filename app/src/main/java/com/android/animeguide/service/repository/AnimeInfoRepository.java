package com.android.animeguide.service.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.animeguide.service.model.AnimeInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeInfoRepository {

    //Retrofitインターフェース
    private final AnimeGuideService animeGuideService;

    //staticに提供できるRepository
    private static AnimeInfoRepository sAnimeInfoRepository;


    //コンストラクタでRetrofitインスタンスを生成
    public AnimeInfoRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AnimeGuideService.HTTPS_API_ANIME_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        animeGuideService = retrofit.create(AnimeGuideService.class);
    }

    //singletonでRepositoryインスタンスを取得
    public synchronized static AnimeInfoRepository getInstance() {
        if (sAnimeInfoRepository == null) {
            sAnimeInfoRepository = new AnimeInfoRepository();
        }
        return sAnimeInfoRepository;
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(一覧)
    public LiveData<List<AnimeInfo>> getAnimeInfoList(int year, int id) {
        final MutableLiveData<List<AnimeInfo>> data = new MutableLiveData<>();

        //Retrofitで非同期リクエスト->CallbackでAnimeInfo型ListのMutableLiveDataにセット
        animeGuideService.getAnimeInfoList(year,id).enqueue(new Callback<List<AnimeInfo>>() {
            @Override
            public void onResponse(@NonNull Call<List<AnimeInfo>> call, @Nullable Response<List<AnimeInfo>> response) {
                Log.e("TAG","onResponse : " + response);
                data.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<AnimeInfo>> call, Throwable t) {
                data.postValue(null);
                Log.d("logs:", "getProjectList:onFailure" + t);
            }
        });

        return data;
    }
}

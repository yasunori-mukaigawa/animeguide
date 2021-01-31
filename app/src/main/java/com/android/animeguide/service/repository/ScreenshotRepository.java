package com.android.animeguide.service.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.animeguide.service.model.ScreenShotInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScreenshotRepository {

    //Retrofitインターフェース
    private final AnimeGuideService animeGuideService;

    //staticに提供できるRepository
    private static ScreenshotRepository sScreenshotRepository;

    //コンストラクタでRetrofitインスタンスを生成
    public ScreenshotRepository() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AnimeGuideService.HTTPS_API_SCREENSHOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        animeGuideService = retrofit.create(AnimeGuideService.class);
    }

    //singletonでRepositoryインスタンスを取得
    public synchronized static ScreenshotRepository getInstance() {
        if (sScreenshotRepository == null) {
            sScreenshotRepository = new ScreenshotRepository();
        }
        return sScreenshotRepository;
    }

    //APIにリクエストし、レスポンスをLiveDataで返す(一覧)
    public LiveData<ScreenShotInfo> getScreenShot(String url) {
        final MutableLiveData<ScreenShotInfo> data = new MutableLiveData<>();

        //Retrofitで非同期リクエスト->CallbackでString型ListのMutableLiveDataにセット
        animeGuideService.getScreenShot(url).enqueue(new Callback<ScreenShotInfo>() {
            @Override
            public void onResponse(Call<ScreenShotInfo> call, Response<ScreenShotInfo> response) {
                Log.e("TAG","onResponse : " + response);
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ScreenShotInfo> call, Throwable t) {
                data.postValue(null);
                Log.e("logs:", "getScreenShot:onFailure" + t);
            }
        });

        return data;
    }
}

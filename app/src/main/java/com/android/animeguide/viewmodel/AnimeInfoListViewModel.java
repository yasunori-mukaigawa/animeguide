package com.android.animeguide.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.animeguide.service.model.AnimeInfo;
import com.android.animeguide.service.repository.AnimeInfoRepository;

import java.util.List;

public class AnimeInfoListViewModel extends ViewModel {

    //監視対象のLiveData
    private LiveData<List<AnimeInfo>> mAnimeInfoListObservable;

    public int mYear;

    public AnimeInfoListViewModel() {
        super();
    }

    public void getAnimeInfo(int year, int id){
        //Repositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
        //変換が必要な場合、これをTransformationsクラスで単純に行うことができる。
        mYear = year;
        mAnimeInfoListObservable = AnimeInfoRepository.getInstance().getAnimeInfoList(year,id);
    }

    //UIが観察できるようにコンストラクタで取得したLiveDataを公開する
    public LiveData<List<AnimeInfo>> getAnimeInfoListObservable() {
        return mAnimeInfoListObservable;
    }

    public int getmYear() {
        return mYear;
    }
}
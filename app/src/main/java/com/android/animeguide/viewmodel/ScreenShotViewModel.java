package com.android.animeguide.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.android.animeguide.service.model.ScreenShotInfo;
import com.android.animeguide.service.repository.ScreenshotRepository;

public class ScreenShotViewModel extends ViewModel {

    //監視対象のLiveData
    private LiveData<ScreenShotInfo> mScreenshotObservable;

    public int getPosition() {
        return mPosition;
    }

    public int mPosition;

    public ScreenShotViewModel() {
        super();
    }

    public void setPosition(int position){
        mPosition = position;
    }

    public void getScreenShot(String url){
        //Repositoryからインスタンスを取得し、getProjectListを呼び出し、LiveDataオブジェクトに。
        //変換が必要な場合、これをTransformationsクラスで単純に行うことができる。
        mScreenshotObservable = ScreenshotRepository.getInstance().getScreenShot(url);
    }

    //UIが観察できるようにコンストラクタで取得したLiveDataを公開する
    public LiveData<ScreenShotInfo> getScreenshotObservable() {
        return mScreenshotObservable;
    }

}

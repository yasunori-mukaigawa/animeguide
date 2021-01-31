package com.android.animeguide.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectableYearViewModel extends ViewModel {

    @SuppressWarnings("unchecked")
    final
    MutableLiveData<String> mYear = new MutableLiveData();
    //監視対象のLiveData
    private final LiveData<String> mSelectableYearObservable = mYear;

    //UIが観察できるようにコンストラクタで取得したLiveDataを公開する
    public LiveData<String> getSelectableYearViewModel() {
        return mSelectableYearObservable;
    }

    public void setYear(String year){
        mYear.postValue(year);
    }

    public String getYear(){
        if(mYear.getValue() != null){
            return mYear.getValue();
        }
        return "2019";
    }
}

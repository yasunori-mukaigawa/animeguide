package com.android.animeguide.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.animeguide.R;
import com.android.animeguide.service.model.AnimeInfo;
import com.android.animeguide.adapter.AnimeListAdapter;
import com.android.animeguide.service.model.ScreenShotInfo;
import com.android.animeguide.viewmodel.AnimeInfoListViewModel;
import com.android.animeguide.viewmodel.ScreenShotViewModel;


import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnimeListFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_YEAR_NUMBER = "year_number";
    public static final String ANIME_INFO_KEY = "anime_info_key";

    public AnimeInfoListViewModel mAnimeInfoListViewModel;
    RecyclerView mRecyclerView;
    AnimeListAdapter mAdapter;

    public static AnimeListFragment newInstance(int year, int index) {
        AnimeListFragment fragment = new AnimeListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_YEAR_NUMBER, year);
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int index = 1;
        int year = 2020;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            year = getArguments().getInt(ARG_YEAR_NUMBER);
        }

        //ViewModelのインスタンスをActivityに紐づけ、Fragmentが死んでも、
        //同じインスタンスを使用する
        mAnimeInfoListViewModel = ViewModelProviders.of(getActivity()).get(String.valueOf(index), AnimeInfoListViewModel.class);

        //ViewModelで保持する年代が変更されていたら、AnimeInfoを再取得する
        if(mAnimeInfoListViewModel.getmYear() != year) {
            mAnimeInfoListViewModel.getAnimeInfo(year, index);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = root.findViewById(R.id.recycler_view);
        mAdapter = new AnimeListAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItemOnClickListner(new AnimeListAdapter.setItemOnClickListner() {
            @Override
            public void onClick(AnimeInfo animeInfo) {
                startAnimeInformation(animeInfo);
            }
        });

        observeAnimeInfoListViewModel(mAnimeInfoListViewModel);

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy");
    }

    private void observeAnimeInfoListViewModel(final AnimeInfoListViewModel viewModel){
        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
        viewModel.getAnimeInfoListObservable().observe(getViewLifecycleOwner(), new Observer<List<AnimeInfo>>() {
            @Override
            public void onChanged(@Nullable List<AnimeInfo> animeInfoList) {
                if(animeInfoList != null && animeInfoList.size() != 0) {
                    mAdapter.setProjectList(animeInfoList);
                }
            }
        });
    }

    public void observeScreenshotViewModel(final ScreenShotViewModel viewModel){

        //データが更新されたらアップデートするように、LifecycleOwnerを紐付け、ライフサイクル内にオブザーバを追加
        //オブザーバーは、STARTED かRESUMED状態である場合にのみ、イベントを受信する
        viewModel.getScreenshotObservable().observe(getViewLifecycleOwner(), new Observer<ScreenShotInfo>() {
            @Override
            public void onChanged(ScreenShotInfo screenShotInfo) {
                if(screenShotInfo != null && screenShotInfo.thumbnail != null) {
                    mAdapter.setImageView(screenShotInfo, viewModel);
                } else {
                    mAdapter.goneImageView();
                }
            }
        });
    }

    private void startAnimeInformation(AnimeInfo animeInfo){
        Intent intent = new Intent(getActivity(), AnimeInformationActivity.class);
        if(animeInfo != null) intent.putExtra(ANIME_INFO_KEY, animeInfo);
        startActivity(intent);
    }

}
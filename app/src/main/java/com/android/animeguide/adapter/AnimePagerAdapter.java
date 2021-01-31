package com.android.animeguide.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.android.animeguide.R;
import com.android.animeguide.view.ui.AnimeListFragment;

public class AnimePagerAdapter extends FragmentStatePagerAdapter {

    private static final int[] TAB_TITLES =
            new int[]{R.string.tab_text_1, R.string.tab_text_2,
                R.string.tab_text_3,R.string.tab_text_4};
    private static final int MAX_PAGE = 4;
    private final Context mContext;
    private int mYear;

    public AnimePagerAdapter(Context context, FragmentManager fm, int year) {
        super(fm);
        mContext = context;
        mYear = year;
    }

    @NonNull
    @Override
    public AnimeListFragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a AnimeDetailsFragment (defined as a static inner class below).
        return AnimeListFragment.newInstance(mYear, position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 4 total pages.
        return MAX_PAGE;
    }

    @Override
    public int getItemPosition(Object object) {
        //notifyDataSetChangedが呼ばれた際は、UIを更新する
        return POSITION_NONE;
    }

    public void setYear(int year){
        mYear = year;
    }
}
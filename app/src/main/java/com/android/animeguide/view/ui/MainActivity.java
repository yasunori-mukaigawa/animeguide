package com.android.animeguide.view.ui;

import android.os.Bundle;

import com.android.animeguide.R;
import com.android.animeguide.databinding.ActivityMainBinding;
import com.android.animeguide.util.PageSwipeTransformer;
import com.android.animeguide.view.ui.dialog.SetYearDialogFragment;
import com.android.animeguide.viewmodel.SelectableYearViewModel;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.android.animeguide.adapter.AnimePagerAdapter;

public class MainActivity extends AppCompatActivity {

    public SetYearDialogFragment dialogFragment;

    AnimePagerAdapter mAnimePagerAdapter;
    SelectableYearViewModel viewModel;
    private final int FIRST_YEAR = 2019;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpView();
        setSelectYearDialog();
    }

    private void setUpView(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(SelectableYearViewModel.class);
        binding.setViewModel(viewModel);
        observeViewModel(viewModel);
        viewModel.setYear("2019");
    }

    private void freshAnimePager(int year){
        mAnimePagerAdapter.setYear(year);
        mAnimePagerAdapter.notifyDataSetChanged();
    }

    private void setAdapter(){
        mAnimePagerAdapter = new AnimePagerAdapter(this, getSupportFragmentManager(),FIRST_YEAR);
        binding.viewPager.setAdapter(mAnimePagerAdapter);
        binding.tabs.setupWithViewPager(binding.viewPager);
        binding.viewPager.setPageTransformer(true, new PageSwipeTransformer());
    }

    private void setSelectYearDialog(){
        binding.setYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFragment = new SetYearDialogFragment(viewModel);
                dialogFragment.show(getSupportFragmentManager(), "test");
            }
        });
    }

    private void observeViewModel(final SelectableYearViewModel viewModel){

        viewModel.getSelectableYearViewModel().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s != null) {
                    if (mAnimePagerAdapter != null) {
                        freshAnimePager(Integer.parseInt(s));
                    } else {
                        setAdapter();
                    }
                    binding.setViewModel(viewModel);
                }
            }
        });
    }
}
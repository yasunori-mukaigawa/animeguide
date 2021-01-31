package com.android.animeguide.view.ui.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.DialogFragment;

import com.android.animeguide.R;
import com.android.animeguide.service.model.SelectableYear;
import com.android.animeguide.util.AnimeGuideUtil;
import com.android.animeguide.adapter.SelectYearAdapter;
import com.android.animeguide.viewmodel.SelectableYearViewModel;

import java.util.Objects;

public class SetYearDialogFragment extends DialogFragment {

    String mYear;
    final SelectableYearViewModel mViewModel;

    public SetYearDialogFragment(SelectableYearViewModel viewModel){
        mViewModel = viewModel;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = new Dialog(getActivity());

        setUpDialog(dialog);

        setUpSelectYearSpinner(dialog);

        return dialog;
    }

    private void setUpDialog(Dialog dialog){
        // タイトル非表示
        Objects.requireNonNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.yaer_dialog);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // OK ボタンのリスナ
        dialog.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mViewModel.setYear(mYear);
            }
        });

        // Close ボタンのリスナ
        dialog.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


    public void setUpSelectYearSpinner(Dialog dialog){
        SelectYearAdapter adapter = new SelectYearAdapter(getContext(), AnimeGuideUtil.sDropdownItems);
        final AppCompatSpinner spinner = dialog.findViewById(R.id.year_spinner);
        spinner.setAdapter(adapter);
        //　スピナー選択時のリスナー
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYear = ((SelectableYear)parent.getAdapter().getItem(position)).getYear();
           }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });
    }
}

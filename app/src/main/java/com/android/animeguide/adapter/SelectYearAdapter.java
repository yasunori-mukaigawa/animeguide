package com.android.animeguide.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.animeguide.R;
import com.android.animeguide.service.model.SelectableYear;

import java.util.Objects;

public class SelectYearAdapter extends ArrayAdapter<SelectableYear> {

    public SelectYearAdapter(@NonNull Context context, @NonNull SelectableYear[] objects) {
        super(context, 0 , objects);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_year_list_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.list_year);
        textView.setText(Objects.requireNonNull(getItem(position)).getYear());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //getDropDownView()では展開したリストのレイアウトを作成する
        //ここではアイコンとテキストを並べたカスタムレイアウトを適用している
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.select_year_list_item, parent, false);
        }

        SelectableYear item = getItem(position);

        TextView textView = convertView.findViewById(R.id.list_year);
        textView.setText(item.getYear());

        return convertView;
    }
}

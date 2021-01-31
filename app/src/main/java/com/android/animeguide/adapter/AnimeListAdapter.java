package com.android.animeguide.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.animeguide.R;
import com.android.animeguide.databinding.AnimeListItemBinding;
import com.android.animeguide.service.model.AnimeInfo;
import com.android.animeguide.service.model.ScreenShotInfo;
import com.android.animeguide.util.FeatureFlag;
import com.android.animeguide.view.ui.AnimeListFragment;
import com.android.animeguide.viewmodel.ScreenShotViewModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Target;

import java.util.List;


public class AnimeListAdapter extends RecyclerView.Adapter<AnimeListAdapter.AnimeInfoViewHolder> {

    List<? extends AnimeInfo> mAnimeInfoList;
    private setItemOnClickListner listener;
    private Context mContext;
    private AnimeListFragment mFragment;
    AnimeListItemBinding binding;

    public AnimeListAdapter(AnimeListFragment fragment){
        mFragment = fragment;
    }

    //現状との差分をListとしてRecyclerViewにセットする
    public void setProjectList(final List<? extends AnimeInfo> animeInfoList) {

        if (this.mAnimeInfoList == null) {
            this.mAnimeInfoList = animeInfoList;
            //positionStartの位置からitemCountの範囲において、データの変更があったことを登録されているすべてのobserverに通知する。
            notifyItemRangeInserted(0, animeInfoList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return AnimeListAdapter.this.mAnimeInfoList.size();
                }

                @Override
                public int getNewListSize() {
                    return animeInfoList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return isImageViewDrawable(oldItemPosition, newItemPosition, animeInfoList);
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Log.e("TAG","set drawable");
                    return animeInfoList.get(newItemPosition).drawable == null;
                }
            });
            this.mAnimeInfoList = animeInfoList;

            result.dispatchUpdatesTo(this);
        }
    }

    /**
     * 受け取ったスクリーンショットのURLからbitmapを生成する
     * @param info スクリーンショットの情報
     * @param viewModel　ScreenShotInfoの監視用ViewModel
     */
    public void setImageView(ScreenShotInfo info, final ScreenShotViewModel viewModel){
        Log.e("TAG","screenShotInfo : " + info.thumbnail);
        RequestCreator creator = Picasso.with(mContext).load(info.thumbnail);
        creator.into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                updateImageView(new BitmapDrawable(mContext.getResources(), bitmap), viewModel);
            }


            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                updateImageView(errorDrawable, viewModel);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                updateImageView(placeHolderDrawable, viewModel);
            }
        });
    }

    /**
     *
     * @param drawable
     * @param viewModel
     */
    private void updateImageView(Drawable drawable, ScreenShotViewModel viewModel){
        if(mAnimeInfoList != null) {
            mAnimeInfoList.get(viewModel.getPosition()).drawable = drawable;
        }
        setProjectList(mAnimeInfoList);
    }

    private boolean isImageViewDrawable(int oldItemPosition, int newItemPosition, List<? extends AnimeInfo> animeInfoList){
        return AnimeListAdapter.this.mAnimeInfoList.get(oldItemPosition).drawable == animeInfoList.get(newItemPosition).drawable;
    }

    //継承したインナークラスのViewholderをレイアウトとともに生成
    //bindするビューにコールバックを設定 -> ビューホルダーを返す
    @NonNull
    @Override
    public AnimeInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        mContext = parent.getContext();
        binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.anime_list_item, parent, false);

        return new AnimeInfoViewHolder(binding);
    }

    //ViewHolderをDataBindする
    @Override
    public void onBindViewHolder(AnimeInfoViewHolder holder, final int position) {
        holder.binding.setAnimeInfo(mAnimeInfoList.get(position));
        holder.binding.executePendingBindings();
        if(FeatureFlag.IMAGE_VIEW_VISIBLE) {
            setScreenShot(holder, position);
        } else {
            goneImageView();
        }
        holder.binding.animeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(mAnimeInfoList.get(position));
            }
        });
    }

    private void setScreenShot(AnimeInfoViewHolder holder, final int position){
        ScreenShotViewModel screenShotViewModel =
                ViewModelProviders.of((FragmentActivity) mContext).get(String.valueOf(position),
                        ScreenShotViewModel.class);
        if (mAnimeInfoList.get(position).drawable == null) {
            screenShotViewModel.getScreenShot(mAnimeInfoList.get(position).public_url);
            screenShotViewModel.setPosition(position);
            mFragment.observeScreenshotViewModel(screenShotViewModel);
        } else {
            holder.binding.imageView.setImageDrawable(mAnimeInfoList.get(position).drawable);
            Log.i("TAG", "position : " + position);
        }
    }

    public void goneImageView(){
        binding.imageView.setVisibility(View.GONE);
    }

    //リストのサイズを返す
    @Override
    public int getItemCount() {
        return mAnimeInfoList == null ? 0 : mAnimeInfoList.size();
    }

    //インナークラスにViewHolderを継承し、 Bindingを設定
    static class AnimeInfoViewHolder extends RecyclerView.ViewHolder {

        final AnimeListItemBinding binding;

        public AnimeInfoViewHolder(AnimeListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    //リストタップ時のListener
    public interface setItemOnClickListner {
        void onClick( AnimeInfo animeInfo);
    }

    public void setItemOnClickListner(setItemOnClickListner listner){
        this.listener = listner;
    }
}
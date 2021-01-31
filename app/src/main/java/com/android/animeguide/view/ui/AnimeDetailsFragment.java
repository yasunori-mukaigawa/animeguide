package com.android.animeguide.view.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.animeguide.R;
import com.android.animeguide.service.model.AnimeInfo;
import com.android.animeguide.viewmodel.AnimeDetailsViewModel;

import java.util.Objects;

/**
 * A placeholder fragment containing a simple view.
 */
public class AnimeDetailsFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String TWITTER_PREFIX ="https://twitter.com/";

    public static AnimeDetailsFragment newInstance(int index) {
        AnimeDetailsFragment fragment = new AnimeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AnimeDetailsViewModel animeDetailsViewModel;
        animeDetailsViewModel = ViewModelProviders.of(this).get(AnimeDetailsViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        animeDetailsViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        return createFragment(inflater, container);
    }


    @SuppressLint("SetJavaScriptEnabled")
    public View createFragment(LayoutInflater inflater, ViewGroup container){
        View root = inflater.inflate(R.layout.anime_infomation_fragment, container, false);
        AnimeInfo animeInfo = (AnimeInfo) getActivity().getIntent().getSerializableExtra(AnimeListFragment.ANIME_INFO_KEY);

        if(animeInfo == null) {
            getActivity().finish();
            return root;
        }

        WebView webView = createWebView(root);

        String result = getUrl(animeInfo);

        if(result != null) webView.loadUrl(result);

        return root;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private WebView createWebView(View root){
        WebView webView = root.findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient());
        //WebViewがJSを使用できるように設定する
        webView.getSettings().setJavaScriptEnabled(true);

        return webView;
    }

    public String getUrl(AnimeInfo animeInfo){
        String result;
        //公式ページであればpublic_urlを取得
        if(isOfficialSite()) {
            result = animeInfo.public_url;
        }//その他であれば、twitter_urlを取得
        else{
            result = TWITTER_PREFIX + animeInfo.twitter_account;
        }

        return result;
    }

    public boolean isOfficialSite(){
        return Objects.requireNonNull(getArguments()).getInt(ARG_SECTION_NUMBER) == 1;
    }
}
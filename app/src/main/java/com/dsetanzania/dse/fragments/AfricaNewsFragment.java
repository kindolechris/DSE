package com.dsetanzania.dse.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dsetanzania.dse.R;
import com.dsetanzania.dse.activities.NewsActivity;
import com.dsetanzania.dse.activities.NewsWebActivity;
import com.dsetanzania.dse.adapters.MainArticleAdapter;
import com.dsetanzania.dse.api.news.ApiNews;
import com.dsetanzania.dse.api.news.RetrofitClientNews;
import com.dsetanzania.dse.interfaces.OnRecyclerViewItemClickListener;
import com.dsetanzania.dse.models.news.Article;
import com.dsetanzania.dse.models.news.ResponseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AfricaNewsFragment extends Fragment implements OnRecyclerViewItemClickListener {
    private static final String API_KEY = "f3b46ae9ed9a43b19f6dc1c45479ea6e";
    private String title;
    private int page;
    private RecyclerView newsrecyclerview;
    View view;
    ProgressBar progressBar;

    // newInstance constructor for creating fragment with arguments
    public static AfricaNewsFragment newInstance(int page, String title) {
        AfricaNewsFragment africaNewsFragment = new AfricaNewsFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        africaNewsFragment.setArguments(args);
        return africaNewsFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if(view == null){
            view = inflater.inflate(R.layout.fragment_africa_news, container, false);
            // Inflate the layout for this fragment
            newsrecyclerview = (RecyclerView) view.findViewById(R.id.africanewsrecyclerview);
            progressBar = (ProgressBar) view.findViewById(R.id.africannewsLoader);
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            newsrecyclerview.setLayoutManager(linearLayoutManager);
            getNews();
        }

        return view;
    }

    public void getNews(){
        final ApiNews apiService = RetrofitClientNews.getClient().create(ApiNews.class);
        Call<ResponseModel> call = apiService.getAfricaNews("za","health",API_KEY);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().getStatus().equals("ok")) {
                    List<Article> articleList = response.body().getArticles();
                    if(articleList.size()>0) {
                        progressBar.setVisibility(View.INVISIBLE);
                        final MainArticleAdapter mainArticleAdapter = new MainArticleAdapter(articleList);
                        mainArticleAdapter.setOnRecyclerViewItemClickListener(AfricaNewsFragment.this::onItemClick);
                        newsrecyclerview.setAdapter(mainArticleAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.e("out", t.toString());
            }
        });

    }

    @Override
    public void onItemClick(int adapterPosition, View view) {
        switch (view.getId()) {
            case R.id.article_adapter_ll_parent:
                Article article = (Article) view.getTag();
                if (!TextUtils.isEmpty(article.getUrl())) {
                    Log.e("clicked url", article.getUrl());
                    Intent webActivity = new Intent(getContext(), NewsWebActivity.class);
                    webActivity.putExtra("url", article.getUrl());
                    webActivity.putExtra("source", article.getSource().getName());
                    startActivity(webActivity);
                } break;
        }
    }
}

package com.brook.demo.presenter;

import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import com.brook.common.manager.RetrofitServiceManager;
import com.brook.common.presenter.LifecycleBasePresenter;
import com.brook.common.view.MvpView;
import com.brook.demo.bean.MovieList;
import com.brook.demo.service.MovieService;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class DemoPresenter extends LifecycleBasePresenter<DemoPresenter.DemoView> {
    private static final String TAG = "DemoPresenter";
    private static final int PAGE_SIZE = 10;
    private int pageOffset = 10;
    private MovieService movieService = RetrofitServiceManager.getInstance().create(MovieService.class);

    public DemoPresenter(DemoView view, @NotNull LifecycleOwner owner) {
        super(view, owner);
    }

    public void timer() {
        Observable.interval(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "Unsubscribing subscription from onCreate()");
                    }
                })
                .compose(lifecycleTransformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long num) throws Exception {
                        Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
                        getView().showTime(num.toString());
                    }
                });
    }

    public void loadMoreMovies() {
        String tag = "热门";
//        try {
//            tag = URLEncoder.encode("热门", "utf-8");
//        } catch (UnsupportedEncodingException e) {
//        }

        movieService.searchSubjects("movie", tag, PAGE_SIZE, 10).doOnDispose(new Action() {
            @Override
            public void run() throws Exception {
                Log.i(TAG, "cancel loadMoreMovies.");
            }
        }).compose(lifecycleTransformer()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                new Consumer<MovieList>() {
                    @Override
                    public void accept(MovieList movieList) throws Exception {
                        Log.i(TAG, "get movieList: " + movieList.toString());
                        getView().showMovies(movieList);
                    }
                }
                , new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        try {
                            Log.i(TAG, "get movieList error: " + throwable.toString());
                        } catch (Exception e) {
                        }
                    }
                });
    }

    public interface DemoView extends MvpView {
        void showTime(String time);

        void showMovies(MovieList movieList);
    }

}

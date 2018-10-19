package com.brook.demo.presenter;

import android.util.Log;
import androidx.lifecycle.LifecycleOwner;
import com.brook.common.presenter.LifecycleBasePresenter;
import com.brook.common.view.MvpView;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class DemoPresenter extends LifecycleBasePresenter<DemoPresenter.DemoView> {
    private static final String TAG = "DemoPresenter";

    public DemoPresenter(DemoView view, @NotNull LifecycleOwner owner) {
        super(view, owner);
    }

    public void timer() {
        Observable.interval(1, TimeUnit.SECONDS)
                .doOnDispose(new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.i(TAG, "Unsubscribing subscription from onCreate()");
                        //return (String) new Long(System.currentTimeMillis() / 1000).toString();
                    }
                })
                .compose(lifecycleTransformer())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String num) throws Exception {
                        Log.i(TAG, "Started in onCreate(), running until onPause(): " + num);
                    }
                });
        ;
    }

    public interface DemoView extends MvpView {
        void showTime(String time);
    }

}

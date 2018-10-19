package com.brook.common.presenter;


import android.util.Log;
import androidx.annotation.UiThread;
import com.brook.common.view.MvpView;
import java.lang.ref.WeakReference;

/**
 * Mvp Presenter 抽象类
 * 用WeakReference解决了内存泄漏的风险
 * Created by zhangliang on 2017/7/24.
 */

public abstract class BasePresenter<V extends MvpView> implements IPresenter<V> {
    private static final String TAG = "BasePresenter";
    protected WeakReference<V> viewRef;

    @UiThread
    public BasePresenter(V view) {
        Log.d(TAG, "BasePresenter new " + this.getClass().toString());
        attachView(view);
    }

    @UiThread
    public V getView() {
        if (viewRef != null && viewRef.get() != null) {
            return viewRef.get();
        }
        return null;
    }

    @UiThread
    public boolean isViewAttached() {
        return viewRef != null && viewRef.get() != null;
    }

    @Override
    public void attachView(V view) {
        viewRef = new WeakReference<V>(view);
    }

    @Override
    public void detachView() {
        Log.d(TAG, "BasePresenter.onDestroy" + this.getClass().toString());
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

}

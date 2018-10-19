package com.brook.common.activity;

import android.os.Bundle;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;

import androidx.lifecycle.LifecycleOwner;
import com.brook.common.presenter.LifecycleBasePresenter;
import com.brook.common.view.MvpView;

/**
 * MVP Activity 基类
 * Created by zhangliang on 2017/7/25.
 */

public abstract class MvpBaseActivity<V extends MvpView, P extends LifecycleBasePresenter<V>> extends BaseActivity implements MvpView {
    protected P presenter;

    @CallSuper
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter((V) this, this);
        getLifecycle().addObserver(presenter);//添加LifecycleObserver
    }

    @CallSuper
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @NonNull
    public abstract P createPresenter(V v, LifecycleOwner owner);

    @NonNull
    public P getPresenter() {
        return presenter;
    }
}

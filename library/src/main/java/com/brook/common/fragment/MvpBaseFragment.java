package com.brook.common.fragment;


import android.os.Bundle;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import com.brook.common.presenter.BasePresenter;
import com.brook.common.presenter.LifecycleBasePresenter;
import com.brook.common.view.MvpView;

/**
 *
 */
public abstract class MvpBaseFragment<V extends MvpView, P extends LifecycleBasePresenter<V>> extends BaseFragment {

    protected P presenter;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = createPresenter((V) this, this);
        getLifecycle().addObserver(presenter);//添加LifecycleObserver
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @NonNull
    public abstract P createPresenter(V v, LifecycleOwner owner);

}

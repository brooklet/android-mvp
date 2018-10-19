package com.brook.common.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(getLayoutResId() , null , false);
        initView(view , savedInstanceState);
        initData();
        return view;
    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();

}

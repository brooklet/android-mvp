package com.brook.demo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import com.brook.common.activity.MvpBaseActivity;
import com.brook.demo.presenter.DemoPresenter;

public class MainActivity extends MvpBaseActivity<DemoPresenter.DemoView, DemoPresenter> implements DemoPresenter.DemoView {

    private static final String TAG = "MainActivity";
    private TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tt = findViewById(R.id.test);
    }

    @NonNull
    @Override
    public DemoPresenter createPresenter(DemoPresenter.DemoView mvpView, LifecycleOwner owner) {
        return new DemoPresenter(mvpView, owner);
    }

    @Override
    public void showTime(String time) {
        tt.setText(time);
    }

}

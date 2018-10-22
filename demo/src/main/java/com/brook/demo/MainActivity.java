package com.brook.demo;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.brook.common.activity.MvpBaseActivity;
import com.brook.demo.bean.MovieList;
import com.brook.demo.presenter.DemoPresenter;
import butterknife.BindView;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpBaseActivity<DemoPresenter.DemoView, DemoPresenter> implements DemoPresenter.DemoView {
    public CompositeDisposable mCompositeDisposable;
    private static final String TAG = "MainActivity";

    private Unbinder unbinder = null;

    @BindView(R.id.test)
    private TextView tt;

    @BindView(R.id.move_count)
    private TextView move_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        mCompositeDisposable = new CompositeDisposable();

        this.getPresenter().timer();
        this.getPresenter().loadMoreMovies();
    }

    /**
     * 添加订阅
     */
    public void addDisposable(Disposable mDisposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(mDisposable);
    }

    /**
     * 取消所有订阅
     */
    public void clearDisposable() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearDisposable();
        unbinder.unbind();
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public void showMovies(MovieList movieList) {
        move_count.setText(movieList.subjects.size()+"");
    }

}

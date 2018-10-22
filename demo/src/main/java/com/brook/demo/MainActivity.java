package com.brook.demo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.brook.common.activity.MvpBaseActivity;
import com.brook.demo.bean.MovieList;
import com.brook.demo.presenter.DemoPresenter;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.TimeUnit;

public class MainActivity extends MvpBaseActivity<DemoPresenter.DemoView, DemoPresenter> implements DemoPresenter.DemoView {
    public CompositeDisposable mCompositeDisposable;
    private static final String TAG = "MainActivity";

    private Unbinder unbinder = null;

    @BindView(R.id.test)
    TextView tt;

    @BindView(R.id.move_count)
    TextView move_count;

    @BindView(R.id.send)
    Button btnGetCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);

        mCompositeDisposable = new CompositeDisposable();

        this.getPresenter().timer();
        this.getPresenter().loadMoreMovies();

        onViewCreated(savedInstanceState);
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

    protected void onViewCreated(Bundle savedInstanceState) {
        //点击事件
        addDisposable(RxView.clicks(btnGetCode)
                //1s防抖
                .throttleFirst(1, TimeUnit.SECONDS)
                //UI线程
                .observeOn(AndroidSchedulers.mainThread())
                //点击后设置为不可点击
                .doOnNext(o -> RxView.enabled(btnGetCode).accept(false))
                .subscribe(o -> {
                            //从0开始发射11个数字为：0-10依次输出，延时0s执行，每1s发射一次。
                            addDisposable(Flowable.intervalRange(0, 11, 0, 1, TimeUnit.SECONDS)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    //每次发射数字更新UI
                                    .doOnNext(aLong -> {
                                        RxTextView.text(btnGetCode).accept("重新获取(" + (10 - aLong) + ")");
                                    })
                                    //倒计时完毕更新UI，设置为可点击
                                    .doOnComplete(() -> {
                                        RxView.enabled(btnGetCode).accept(true);
                                        RxTextView.text(btnGetCode).accept("获取验证码");
                                    })
                                    .subscribe());
                        }
                )
        );
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
        move_count.setText(movieList.subjects.size() + "");
    }

}

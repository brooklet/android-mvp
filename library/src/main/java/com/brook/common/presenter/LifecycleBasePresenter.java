package com.brook.common.presenter;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import com.brook.common.ILifecycle;
import com.brook.common.view.MvpView;
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.LifecycleTransformer;
import io.reactivex.Observable;
import org.jetbrains.annotations.NotNull;

/**
 * 带生命周期管理的 Mvp Presenter 抽象类
 * 用WeakReference解决了内存泄漏的风险
 * Created by zhangliang on 2017/7/24.
 */
public abstract class LifecycleBasePresenter<V extends MvpView> extends BasePresenter<V> implements ILifecycle {

    private static final String TAG = "LifecycleBasePresenter";

    protected LifecycleOwner owner;
    protected LifecycleProvider<Lifecycle.Event> provider;

    public LifecycleBasePresenter(V view, @NotNull LifecycleOwner owner) {
        super(view);
        this.owner = owner;
        provider = AndroidLifecycle.createLifecycleProvider(this.owner);
    }

    @Override
    public void onCreate(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "LifecycleBasePresenter.onCreate" + this.getClass().toString());
    }

    @Override
    public void onDestroy(@NotNull LifecycleOwner owner) {
        Log.d(TAG, "LifecycleBasePresenter.onDestroy" + this.getClass().toString());
        detachView();
        this.owner.getLifecycle().removeObserver(this);
        this.owner = null;
        this.provider = null;
    }

    @Override
    public void onLifecycleChanged(@NotNull LifecycleOwner owner, @NotNull Lifecycle.Event event) {
        Log.d(TAG, "LifecycleBasePresenter.onLifecycleChanged" + this.getClass().toString() + " " + event.name() + " " + owner.getLifecycle().getCurrentState());
    }

    protected Observable composeLifecycle(Observable ob) {
        return ob.compose(provider.bindToLifecycle());
    }

    protected LifecycleTransformer lifecycleTransformer() {
        return provider.bindToLifecycle();
    }
}

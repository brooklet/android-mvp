package com.brook.common.presenter;


import androidx.annotation.UiThread;
import com.brook.common.view.MvpView;

/**
 * presenter interface,所有Presenter必须实现此接口
 *
 * @author zhangliang
 */
public interface IPresenter<V extends MvpView> {

    /**
     * Set or attach the view to this presenter
     */
    @UiThread
    void attachView(V view);

    /**
     * Will be called if the view has been destroyed. Typically this method will be invoked from
     * <code>Activity.detachView()</code> or <code>Fragment.onDestroyView()</code>
     */
    @UiThread
    void detachView();

}

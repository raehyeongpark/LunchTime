package com.bicos.lunchtime.sidemenu;

import android.app.Activity;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.net.Uri;
import android.widget.ImageView;

import com.android.databinding.library.baseAdapters.BR;
import com.bicos.lunchtime.util.CircleTransformation;
import com.bumptech.glide.Glide;

/**
 * Created by raehyeong.park on 2017. 2. 22..
 */

public class SideMenuViewModel extends BaseObservable {

    private SideMenuContract.View mView;

    private SideMenuContract.Model mModel;

    public SideMenuViewModel(SideMenuContract.View mView, SideMenuContract.Model mModel) {
        this.mView = mView;
        this.mModel = mModel;
    }

    @Bindable
    public String getUserName() {
        return mModel.getCurrentUser() != null ? mModel.getCurrentUser().getDisplayName() : null;
    }

    @Bindable
    public Uri getUserProfileImg() {
        return mModel.getCurrentUser() != null ? mModel.getCurrentUser().getPhotoUrl() : null;
    }

    @Bindable
    public String getProviderName() {
        if (mModel.getCurrentUser() == null)
            return null;

        if (mModel.getCurrentUser().getProviders() == null || mModel.getCurrentUser().getProviders().isEmpty())
            return null;

        return mModel.getCurrentUser().getProviders().get(0) + "(으)로 로그인";
    }

    @Bindable
    public boolean isLogin() {
        return mModel.getCurrentUser() != null;
    }

    @BindingAdapter("profileUrl")
    public static void setProfileUrl(ImageView imageView, Uri uri) {
        if (imageView != null && imageView.getContext() instanceof Activity) {
            Glide.with((Activity) imageView.getContext())
                    .load(uri)
                    .transform(new CircleTransformation(imageView.getContext()))
                    .into(imageView);
        }
    }

    public void clickLogin() {
        mModel.requestLogin(SideMenuFragment.RC_LOGIN_SIDEMENU);
    }

    public void clickLogout() {
        mModel.requestLogout();
    }

    public void notifyLoginChanged() {
        notifyPropertyChanged(BR.login);
        notifyPropertyChanged(BR.userName);
        notifyPropertyChanged(BR.userProfileImg);
        notifyPropertyChanged(BR.providerName);
    }
}

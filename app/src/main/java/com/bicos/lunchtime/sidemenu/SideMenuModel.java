package com.bicos.lunchtime.sidemenu;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

/**
 * Created by raehyeong.park on 2017. 2. 23..
 */

public class SideMenuModel implements SideMenuContract.Model {

    private FirebaseAuth mAuth;

    private Fragment mFragment;

    public SideMenuModel(Fragment fragment) {
        mAuth = FirebaseAuth.getInstance();
        mFragment = fragment;
    }

    @Override
    public void requestLogin(int requestCode) {
        mFragment.startActivityForResult(AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                requestCode);
    }

    @Override
    public void requestLogout() {
        mAuth.signOut();
    }

    @Override
    public boolean isLogin() {
        return getCurrentUser() != null;
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }
}

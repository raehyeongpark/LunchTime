package com.bicos.lunchtime.sidemenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bicos.lunchtime.databinding.FragmentSideMenuBinding;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by raehyeong.park on 2017. 2. 22..
 */

public class SideMenuFragment extends Fragment implements SideMenuContract.View {

    public static final int RC_LOGIN_SIDEMENU = 100;

    private FirebaseAuth mAuth;

    private SideMenuViewModel mViewModel;

    private FirebaseAuth.AuthStateListener mAuthStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            mViewModel.notifyLoginChanged();
        }
    };

    public SideMenuFragment() {
    }

    public static SideMenuFragment newInstance() {

        Bundle args = new Bundle();

        SideMenuFragment fragment = new SideMenuFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentSideMenuBinding viewBinding = FragmentSideMenuBinding.inflate(inflater, container, false);

        mViewModel = new SideMenuViewModel(this, new SideMenuModel(this));

        viewBinding.setViewModel(mViewModel);

        return viewBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    public void onStop() {
        mAuth.removeAuthStateListener(mAuthStateListener);
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_LOGIN_SIDEMENU) {
            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                Toast.makeText(getContext(), "Login Successe", Toast.LENGTH_SHORT).show();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    Toast.makeText(getContext(), "Login cancel", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(getContext(), "network error", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(getContext(), "unknown error", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Toast.makeText(getContext(), "unknown error", Toast.LENGTH_SHORT).show();
        }
    }
}

package com.bicos.lunchtime.sidemenu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by raehyeong.park on 2017. 2. 22..
 */

public class SideMenuContract {

    public interface View {

    }

    public interface Model {
        void requestLogin(int requestCode);
        void requestLogout();
        boolean isLogin();
        FirebaseUser getCurrentUser();
    }
}

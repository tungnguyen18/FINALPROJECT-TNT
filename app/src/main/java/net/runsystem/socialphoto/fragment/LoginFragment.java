package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.runsystem.socialphoto.API.Request.LoginRequest;
import net.runsystem.socialphoto.API.Response.LoginResponse;
import net.runsystem.socialphoto.Constant.ApiConstance;
import net.runsystem.socialphoto.MainActivity;
import net.runsystem.socialphoto.R;
import net.runsystem.socialphoto.commonclass.StringEncryption;
import net.runsystem.socialphoto.manager.UserManager;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.OnClick;
import vn.app.base.api.volley.callback.ApiObjectCallBack;
import vn.app.base.util.DialogUtil;
import vn.app.base.util.FragmentUtil;
import vn.app.base.util.KeyboardUtil;
import vn.app.base.util.SharedPrefUtils;
import vn.app.base.util.StringUtil;

/**
 * Created by admin on 10/14/2016.
 */

public class LoginFragment extends NoHeaderFragment {

    String userID;
    String pass;

    LoginResponse loginResponse;
    @BindView(R.id.etLogin)
    EditText etLogin;

    @BindView(R.id.etPassword)
    EditText etPass;

    @BindView(R.id.btnLogin)
    Button btnLogin;

    @BindView(R.id.btnCreateAccount)
    Button btnCreate;

    public static LoginFragment newInstance(){
        LoginFragment loginFragment = new LoginFragment();
        return loginFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView(View root) {
        super.initView(root);
        ((MainActivity) getActivity()).setToolbar(0);
        etPass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {
    }

    @OnClick(R.id.btnCreateAccount)
    public void goToRegisterFragment(){
        FragmentUtil.pushFragment(getActivity(),RegisterFragment.newInstance(),null);
    }

    @OnClick(R.id.btnLogin)
    public void login(){
        userID = etLogin.getText().toString().trim();
        pass = etPass.getText().toString().trim();
        try {
            pass = StringEncryption.SHA1(etPass.getText().toString().trim());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (!StringUtil.checkStringValid(userID) || !StringUtil.checkStringValid(pass)){
            DialogUtil.showOkBtnDialog(getActivity(),getString(R.string.missing_input_title),getString(R.string.missing_input_message));
            return;
        }
        final LoginRequest loginRequest = new LoginRequest(userID,pass);
        loginRequest.setRequestCallBack(new ApiObjectCallBack<LoginResponse>() {
            @Override
            public void onSuccess(LoginResponse data) {
                if (data.status == 1) {
                    SharedPrefUtils.saveAccessToken(data.data.token);
                    UserManager.saveCurrentUser(data.data);

                    if(SharedPrefUtils.getBoolean(ApiConstance.ISLOGINYET,false) == false){
                        FragmentUtil.pushFragmentWithAnimation(getActivity(), new TutorialFragment(), null);
                        SharedPrefUtils.putBoolean(ApiConstance.ISLOGINYET,true);
                    } else FragmentUtil.pushFragmentWithAnimation(getActivity(), new HomeFragment(), null);

                } else Toast.makeText(getActivity(), "Fail", Toast.LENGTH_SHORT).show();
                loginResponse = data;
               // handleLoginSuccess(data);
            }

            @Override
            public void onFail(int failCode, String message) {
                hideCoverNetworkLoading();
            }
        });
        loginRequest.execute();
        KeyboardUtil.hideKeyboard(getActivity());
        showCoverNetworkLoading();
    }
//    private void handleLoginSuccess(LoginResponse loginResponse){
//        if (loginResponse.user != null){
//            SharedPrefUtils.saveAccessToken(loginResponse.token);
//          //  UserManager.saveCurrentUser(loginResponse.dataResponse);
//            UserManager.saveCurrentUser(loginResponse.user);
//            FragmentUtil.replaceFragment(getActivity(),TutorialFragment.newInstance(),null);
//        }
//    }
}

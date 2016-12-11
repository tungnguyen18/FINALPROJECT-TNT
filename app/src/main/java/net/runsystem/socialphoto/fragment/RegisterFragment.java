package net.runsystem.socialphoto.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import net.runsystem.socialphoto.R;

import butterknife.BindView;

/**
 * Created by admin on 11/2/2016.
 */

public class RegisterFragment extends NoHeaderFragment {
    @BindView(R.id.ivAvatar)
    ImageView ivAvatar;

    @BindView(R.id.etUser)
    EditText etUser;

    @BindView(R.id.etEmail)
    EditText etEmail;

    @BindView(R.id.etPass)
    EditText etPass;

    @BindView(R.id.etConfirm)
    EditText etConfirm;

    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    public static RegisterFragment newInstance(){
        RegisterFragment registerFragment = new RegisterFragment();
        return registerFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView(View root) {

    }

    @Override
    protected void getArgument(Bundle bundle) {

    }

    @Override
    protected void initData() {

    }
}

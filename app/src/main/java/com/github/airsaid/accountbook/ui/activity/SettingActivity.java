package com.github.airsaid.accountbook.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.avos.avoscloud.AVUser;
import com.github.airsaid.accountbook.R;
import com.github.airsaid.accountbook.base.BaseActivity;
import com.github.airsaid.accountbook.data.User;
import com.github.airsaid.accountbook.util.ClearUtils;
import com.github.airsaid.accountbook.util.ToastUtils;
import com.github.airsaid.accountbook.util.UiUtils;
import com.github.airsaid.accountbook.util.UserUtils;
import com.github.airsaid.accountbook.widget.CommonItemLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Airsaid
 * @github https://github.com/airsaid
 * @date 2017/4/12
 * @desc 设置 Activity
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.cil_update_phone)
    CommonItemLayout mCilUpdatePhone;
    @BindView(R.id.cil_update_password)
    CommonItemLayout mCilUpdatePassword;
    @BindView(R.id.cil_clear)
    CommonItemLayout mCilClear;
    @BindView(R.id.cil_about)
    CommonItemLayout mCilAbout;

    @Override
    public int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    public void onCreateActivity(@Nullable Bundle savedInstanceState) {
        initToolbar(UiUtils.getString(R.string.setting));
        setData();
    }

    private void setData(){
        User user = UserUtils.getUser();
        if(user != null){
            String phone = user.getMobilePhoneNumber();
            mCilUpdatePhone.setRightText(phone);
            mCilClear.setRightText(ClearUtils.getCacheSize());
        }
    }

    @OnClick({R.id.cil_update_phone, R.id.cil_update_password, R.id.cil_clear, R.id.cil_about, R.id.llt_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cil_update_phone:     // 修改手机号
                ToastUtils.show(mContext, "开发中~");
                break;
            case R.id.cil_update_password:  // 修改密码
                ToastUtils.show(mContext, "开发中~");
                break;
            case R.id.cil_clear:            // 清除缓存
                showClearCacheDialog();
                break;
            case R.id.cil_about:            // 关于 APP
                startActivity(new Intent(mContext, AboutPageActivity.class));
                break;
            case R.id.llt_login_out:        // 退出登录
                showLoginOutDialog();
                break;
        }
    }

    /**
     * 显示确认清除缓存 Dialog
     */
    private void showClearCacheDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(UiUtils.getString(R.string.dialog_title))
                .setMessage(UiUtils.getString(R.string.dialog_content_clear_cache))
                .setNegativeButton(UiUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(UiUtils.getString(R.string.dialog_affirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClearUtils.clearCache();
                        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_clear_cache_success));
                        setData();
                    }
                }).create().show();
    }

    /**
     * 显示确认退出 Dialog
     */
    private void showLoginOutDialog(){
        new AlertDialog.Builder(mContext)
                .setTitle(UiUtils.getString(R.string.dialog_title))
                .setMessage(UiUtils.getString(R.string.dialog_content_login_out))
                .setNegativeButton(UiUtils.getString(R.string.dialog_cancel), null)
                .setPositiveButton(UiUtils.getString(R.string.dialog_affirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AVUser.logOut();
                        ToastUtils.show(mContext, UiUtils.getString(R.string.toast_login_out_success));
                        UiUtils.enterLoginPage(mContext, true);
                    }
                }).create().show();
    }
}
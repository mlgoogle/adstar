package com.yundian.star.ui.main.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.utils.LogUtils;
import com.yundian.star.utils.ToastUtils;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 用户资产管理
 * Created by Administrator on 2017/5/23.
 */

public class UserAssetsManageActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.nt_title)
    NormalTitleBar ntTitle;
    @Bind(R.id.ll_recharge)
    LinearLayout recharge;
    @Bind(R.id.ll_user_fudai)
    LinearLayout fudai;
    private PopupWindow popupWindow;

    @Override
    public int getLayoutId() {
        return R.layout.activity_assets_manage;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        ntTitle.setTitleText(getResources().getString(R.string.user_asset_manage));
        ntTitle.setBackVisibility(true);
        ntTitle.setRightImagSrc(R.drawable.about_logo);

        showPopupWindow();
        ntTitle.setOnRightImagListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(ntTitle.getRightImage(), 0, 0);
//                popupWindow.showAtLocation(ntTitle.getRightImage(),  Gravity.CENTER|Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    private void showPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.popup_money_bag, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        contentview.findViewById(R.id.tv_money_detail).setOnClickListener(this);
        contentview.findViewById(R.id.tv_bank_info).setOnClickListener(this);

    }


    @OnClick({R.id.ll_recharge, R.id.ll_user_fudai})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_recharge:
                ToastUtils.showShort("充值");
                startActivity(RechargeActivity.class);
                break;
            case R.id.ll_user_fudai:
                ToastUtils.showShort("模拟进行身份认证");
                showIdentityDialog();
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_money_detail:
                ToastUtils.showShort("钱包明细");
                startActivity(MoneyBagDetailActivity.class);
                popupWindow.dismiss();
                break;
            case R.id.tv_bank_info:
                ToastUtils.showShort("银行卡");
                popupWindow.dismiss();
                break;
        }
    }

    private void showIdentityDialog() {
        final Dialog mDetailDialog = new Dialog(this, R.style.custom_dialog);
        mDetailDialog.setContentView(R.layout.dialog_identity_authentivation);
        final Button startIdentity = (Button) mDetailDialog.findViewById(R.id.btn_start_identity);
        ImageView closeImg = (ImageView) mDetailDialog.findViewById(R.id.iv_dialog_close);
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.setCancelable(false);
        startIdentity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.logd("进入身份认证页面-----");
                startActivity(IdentityAuthenticationActivity.class);
                mDetailDialog.dismiss();
            }
        });
        mDetailDialog.show();
    }

}

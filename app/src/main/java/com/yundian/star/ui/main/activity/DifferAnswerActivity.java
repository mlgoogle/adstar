package com.yundian.star.ui.main.activity;


import android.widget.LinearLayout;

import com.yundian.star.R;
import com.yundian.star.base.BaseActivity;
import com.yundian.star.ui.im.activity.StarCommunicationBookActivity;
import com.yundian.star.ui.im.activity.SystemMessagesActivity;
import com.yundian.star.utils.JudgeIdentityUtils;
import com.yundian.star.utils.JudgeIsSetPayPwd;
import com.yundian.star.widget.NormalTitleBar;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 系统消息
 * Created by sll on 2017/7/4.
 */

public class DifferAnswerActivity extends BaseActivity {

    @Bind(R.id.nt_title)
    NormalTitleBar nt_title;
    @Bind(R.id.ll_star_address_book)
    LinearLayout ll_star_address_book;


    @OnClick(R.id.ll_star_address_book)
    public void gotoAddressBook() {
        if (JudgeIdentityUtils.isIdentityed(this)) {
            startActivity(StarCommunicationBookActivity.class);
        }
    }

    @OnClick(R.id.ll_system_news)
    public void gotoSystemMessage() {
        if (!JudgeIsSetPayPwd.isSetPwd(this)) {
            return;
        }
        startActivity(SystemMessagesActivity.class);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_diff_answer;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        nt_title.setTvLeftVisiable(false);
        nt_title.setTitleText(getString(R.string.diff_answer));
    }

}

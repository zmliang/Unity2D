package com.jinkegroup.edu.se.fragment;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jinke.group.baselib.BaseListViewAdapter;
import com.jinkegroup.AppUpdater;
import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.LoginContract;
import com.jinkegroup.edu.se.utils.DialogUtils;
import com.jinkegroup.edu.se.utils.adapter.LearnDateilsAdapter;
import com.jinkegroup.edu.se.utils.adapter.UserSettingAdapter;
import com.jinkegroup.edu.se.utils.data.User;
import com.jinkegroup.edu.se.view.LearnDetailsGridView;
import com.jinkegroup.listener.OnUpdateCheckResultListener;

import butterknife.BindView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class ParentCenterFragment extends TomBaseFragment<LoginContract.Presenter> implements AdapterView.OnItemClickListener ,View.OnClickListener{

    private User user;

    @BindView(R.id.user_nickname)
    TextView mUserName;

    @BindView(R.id.user_age)
    TextView mUserAge;

    @BindView(R.id.user_starcount)
    TextView mUserStarCount;

    @BindView(R.id.logout)
    Button mLogoutBtn;

    @BindView(R.id.user_head)
    ImageView mUserHead;

    @BindView(R.id.user_learn_details)
    LearnDetailsGridView mDetailsContainer;

    @BindView(R.id.set_list)
    ListView mSetList;

    BaseListViewAdapter<String> adapter;
    UserSettingAdapter  settingAdapter;

    public void setUser(User u){
        this.user = u;
    }

    public User getUser(){
        return user;
    }

    private void setUserInfor(User userInfor){
        mUserAge.setText(String.valueOf(userInfor.getUserAge()));
        mUserName.setText(userInfor.getNickName());
        mUserStarCount.setText(String.valueOf(userInfor.getStarCount()));
    }


    @Override
    protected void initView(){
        super.initView();
        user = getPresenter().getUser();
        adapter = new LearnDateilsAdapter(getContext(),user.getDetails());
        settingAdapter = new UserSettingAdapter(getContext());
        settingAdapter.setArrayList(getResources().getStringArray(R.array.set_item));
        adapter.setArrayList(getResources().getStringArray(R.array.learn_details));
        mDetailsContainer.setAdapter(adapter);
        mSetList.setAdapter(settingAdapter);
        mSetList.setOnItemClickListener(this);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToastMessage("已退出登陆",2000);
                getActivity().finish();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_center;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0:

                break;
            case 1:
             //   DialogUtils.showNewVersionAlert(getContext(),this);
                /*自动检查更新
                    AppUpdater.with(getContext())
                    .setHostUpdateCheckUrl("https://github.com/JebySun/AppUpdater/raw/master/other_files/server_data/app_version.js")
                    .check();
                 */
                checkNewVersion();
                break;
            case 2:
                DialogUtils.showSharedDialog(getActivity(),this);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.shared_wechat_friend:
                break;
            case R.id.shared_qq:
                break;
            case R.id.shared_wechat_zone:
                break;
            case R.id.shared_weibo:
                break;
            case R.id.btn_neg:
                break;
            case R.id.btn_pos:
                break;
            default:
                break;
        }
    }

    private void checkNewVersion(){
        showLoadingControl();
        AppUpdater.with(getContext())
                .setForceMode(true)
                .setHostUpdateCheckUrl("https://github.com/JebySun/AppUpdater/raw/master/other_files/server_data/app_version.js")
                .setOnUpdateCheckResultListener(new OnUpdateCheckResultListener() {
                    @Override
                    public void onSuccess(boolean hasNew) {
                        dismisLoadingControl();
                        if (!hasNew)
                            showToastMessage("你已经安装了最新版本",3000);
                    }

                    @Override
                    public void onError(String msg) {
                        dismisLoadingControl();
                        showToastMessage(msg,3000);
                    }
                }).check();
    }
}

package com.jinkegroup.edu.se.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.jinke.group.baselib.BaseActivity;
import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.MainContract;
import com.jinkegroup.edu.se.contracts.MainPresenter;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.MainView{

    @BindView(R.id.talking_english)
    Button mTalkEnglishBtn;

    @BindView(R.id.listening_songs)
    Button mListenSongsBtn;

    @BindView(R.id.watching_carton)
    Button mWatchVideoBtn;

    @BindView(R.id.parent_center)
    Button mParentCenterBtn;

    @BindView(R.id.tom_animation)
    LottieAnimationView animationView;

    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenter(this);
        mPresenter.create();
        mTalkEnglishBtn.setOnClickListener(mPresenter);
        mListenSongsBtn.setOnClickListener(mPresenter);
        mWatchVideoBtn.setOnClickListener(mPresenter);
        mParentCenterBtn.setOnClickListener(mPresenter);
    }

    @Override
    protected void back() {
        finish();
    }

    @Override
    public int getLayoutId(){
        return R.layout.activity_main;
    }


    private void enterActivity(Class cls){
        startActivity(new Intent(this,cls));
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        animationView.playAnimation();
    }

    @Override
    public void enter(int id) {
        animationView.pauseAnimation();
        switch (id){
            case R.id.talking_english:
                enterActivity(GameMapActivity.class);
                break;
            case R.id.listening_songs:
                enterActivity(MusicActivity.class);
                break;
            case R.id.watching_carton:
                enterActivity(VideoActivity.class);
                break;
            case R.id.parent_center:
                enterActivity(LoginActivity.class);
                break;
            default:
                break;
        }

    }
}

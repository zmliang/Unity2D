package com.jinkegroup.edu.se.activity;

import android.os.Bundle;
import android.view.View;

import com.jinke.group.baselib.BaseFragment;
import com.jinkegroup.edu.se.fragment.MusicListFragment;
import com.jinkegroup.edu.se.fragment.MusicPlayerFragment;
import com.jinkegroup.supportlib.util.Logger;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/25
 * CopyRight:  JinkeGroup
 */

public class MusicActivity extends BaseFragmentActivity implements MusicListFragment.OnCardViewItemListener{

    private MusicListFragment mMusicListFrag;
    private MusicPlayerFragment mMusicPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        mMusicListFrag = new MusicListFragment();
        mMusicPlayer = new MusicPlayerFragment();
        mMusicListFrag.setCardViewClickedListener(this);
        addFragment(mMusicListFrag);

    }


    @Override
    public void onItemClicked(View view, Object data) {
        //音乐播放列表的item被点击的回调方法，回调出被点击的Item和Item上的数据
        Logger.i("zml","MusicActivity received the clicked");
        addFragment(mMusicPlayer);
    }
}

package com.jinkegroup.edu.se.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.PlayerContract;
import com.jinkegroup.edu.se.view.AlbumCoverView;

import butterknife.BindView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/26
 * CopyRight:  JinkeGroup
 */

public class MusicPlayerFragment extends TomBaseFragment<PlayerContract.PlayMusicPresenter> {

    @BindView(R.id.album_cover_view)
    AlbumCoverView coverView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initView(){
        super.initView();
        coverView.initNeedle(true);
        coverView.setCoverBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher));
        coverView.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.music_player;
    }

}

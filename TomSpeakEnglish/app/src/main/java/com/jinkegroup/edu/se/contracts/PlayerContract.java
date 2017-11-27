package com.jinkegroup.edu.se.contracts;

import android.graphics.Bitmap;

import com.jinke.group.baselib.presenter.BasePresenter;
import com.jinke.group.baselib.view.BaseView;

import java.util.List;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/26
 * CopyRight:  JinkeGroup
 */

public interface PlayerContract {

    interface PlayerBaseView extends BaseView<PlayerBasePresenter>{
        void onPlaying(int progress);
        void playCompleted();
        void setDescription(String description);
    }

    interface PlayerBasePresenter extends BasePresenter{
        void play(int index);
        void pause();
        void share();
        void setPlayState(boolean state);
    }

    interface PlayMusicView extends PlayerBaseView{
        void diagram(Bitmap bmp);
    }

    interface PlayMusicPresenter extends PlayerBasePresenter{
        void next();
        void previous();
        void timingPlay(int time);
        void loopPlay();
    }

    interface PlayVideoView extends PlayerBaseView{
        void playError(String error);

    }

    interface PlayVideoPresenter extends PlayerBasePresenter{
        List getVideoList();
        String getCurrVideoDescr();

    }

}

package com.jinkegroup.edu.se.fragment;

import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.PlayerContract;
import com.jinkegroup.edu.se.utils.adapter.RecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import tv.danmaku.ijk.media.widget.media.AndroidMediaController;
import tv.danmaku.ijk.media.widget.media.IjkVideoView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/27
 * CopyRight:  JinkeGroup
 */

public class VideoFragment extends TomBaseFragment<PlayerContract.PlayVideoPresenter> {

    @BindView(R.id.ijkPlayer)
    IjkVideoView videoPlayer;

    @BindView(R.id.video_play_btn)
    Button videoPlayBtn;

    @BindView(R.id.video_list)
    RecyclerView videoList;

    private List<Map<String,Object>> data = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void initView(){
        super.initView();
        initPlayer();
        initVideoList();
    }

    private void initPlayer(){
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        AndroidMediaController controller = new AndroidMediaController(getContext(),false);
        videoPlayer.setMediaController(controller);

        videoPlayer.setVideoURI(Uri.parse("http://mp4.vjshi.com/2017-07-13/b5e7d987643a0e1c53fcfb0a8baa3db6.mp4"));
        videoPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                videoPlayer.start();
            }
        });
    }

    private void initVideoList(){
        adapter = new RecyclerViewAdapter(getContext(),data);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        videoList.setLayoutManager(layoutManager);
        videoList.setAdapter(adapter);
        videoList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");
            }
        });

        //添加点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("test", "item position = " + position);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        getData();
    }

    /**
     * 获取测试数据
     */
    private void getData() {
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            data.add(map);
        }
        adapter.notifyDataSetChanged();
        adapter.notifyItemRemoved(adapter.getItemCount());
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_fragment;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (videoPlayer!=null)
        videoPlayer.pause();
    }

}

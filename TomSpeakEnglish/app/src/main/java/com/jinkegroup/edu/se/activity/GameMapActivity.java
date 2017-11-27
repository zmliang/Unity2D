package com.jinkegroup.edu.se.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.OnLevelItemClickListener;
import com.jinkegroup.supportlib.util.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.util.Pair.create;

/**
 * Author   :  Tomcat
 * Date     :  2017/11/1
 * CopyRight:  JinkeGroup
 */

public class GameMapActivity extends BaseFragmentActivity implements OnLevelItemClickListener {
    private final String TAG = GameMapActivity.class.getSimpleName();
    @BindView(R.id.game_map)
    RecyclerView gameMapView;

    @BindView(R.id.game_map_container)
    RelativeLayout container;


    private List<String> mDatas;
    private MapAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        initData();
        gameMapView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MapAdapter(this);
        gameMapView.setAdapter(adapter);
        gameMapView.setHasFixedSize(true);

        Fade slideExitTransition = new Fade(Visibility.MODE_OUT);
        slideExitTransition.excludeTarget(R.id.tom_custom_title,true);
        slideExitTransition.setDuration(800);
        slideExitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideExitTransition.excludeTarget(android.R.id.statusBarBackground, true);
        getWindow().setExitTransition(slideExitTransition);

        Fade enter = new Fade(Visibility.MODE_IN);
        enter.excludeTarget(R.id.tom_custom_title,true);
        enter.setDuration(800);
        enter.excludeTarget(android.R.id.navigationBarBackground, true);
        enter.excludeTarget(android.R.id.statusBarBackground, true);
        enter.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getEnterTransition().removeListener(this);
                container.setAnimation(AnimationUtils.makeInAnimation(GameMapActivity.this,true));
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
        getWindow().setEnterTransition(enter);
    }

    @Override
    public int getLayoutId(){
        return R.layout.game_map;
    }

    protected void initData()
    {
        mDatas = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public void onGameLevelItemClicked(View view,int pos) {
        Logger.i("onGameLevelItemClicked","pos:"+pos);
        int id = view.getId();
        Intent intent = new Intent(GameMapActivity.this, SceneDialogueActivity.class);
        ActivityOptions transitionActivityOptions = ActivityOptions
                .makeSceneTransitionAnimation(
                        GameMapActivity.this,
                        create(view, "square"));
        startActivity(intent,transitionActivityOptions.toBundle());
    }

    class MapAdapter extends RecyclerView.Adapter<MapAdapter.MyViewHolder>
    {
        private OnLevelItemClickListener levelItemClickListener;

        public MapAdapter(OnLevelItemClickListener listener){
            this.levelItemClickListener = listener;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    GameMapActivity.this).inflate(R.layout.game_level_icon, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position)
        {
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (levelItemClickListener!=null)
                        levelItemClickListener.onGameLevelItemClicked(view, position);
                }
            };
            holder.ib1.setOnClickListener(listener);
            holder.ib2.setOnClickListener(listener);
            holder.ib3.setOnClickListener(listener);
            holder.ib4.setOnClickListener(listener);
            holder.ib5.setOnClickListener(listener);
            holder.ib6.setOnClickListener(listener);
         //   if (position!=0)
             //   holder.level.setBackgroundResource(R.drawable.course_level50_level_path1);
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }


        class MyViewHolder extends RecyclerView.ViewHolder
        {
            ImageButton ib1;
            ImageButton ib2;
            ImageButton ib3;
            ImageButton ib4;
            ImageButton ib5;
            ImageButton ib6;
            View level;

            public MyViewHolder(View view)
            {
                super(view);
                level =  view.findViewById(R.id.level_container);
                ib1 = level.findViewById(R.id.imageButton6);
                ib2 = level.findViewById(R.id.imageButton7);
                ib3 = level.findViewById(R.id.imageButton8);
                ib4 = level.findViewById(R.id.imageButton9);
                ib5 = level.findViewById(R.id.imageButton10);
                ib6 = level.findViewById(R.id.imageButton11);
            }
        }
    }
}

package com.jinkegroup.edu.se.fragment;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.jinkegroup.edu.se.R;
import com.jinkegroup.edu.se.contracts.PlayerContract;
import com.jinkegroup.edu.se.utils.adapter.RecyclerViewAdapter;
import com.jinkegroup.supportlib.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/26
 * CopyRight:  JinkeGroup
 */

public class MusicListFragment extends TomBaseFragment<PlayerContract.PlayMusicPresenter>{
    private final String TAG = MusicListFragment.class.getSimpleName();

    @BindView(R.id.music_list)
    RecyclerView mMusicListContainer;

    @BindView(R.id.SwipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    boolean isLoading;
    private List<Map<String,Object>> data = new ArrayList<>();
    RecyclerViewAdapter adapter;

    Handler handler = new Handler();

    OnCardViewItemListener listener;

    @Override
    public void initView(){
        super.initView();
        adapter = new RecyclerViewAdapter(getContext(),data);
        initData();
        swipeRefreshLayout.setColorSchemeResources(R.color.color_blue);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        data.clear();
                        getData();
                    }
                }, 2000);
            }
        });

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mMusicListContainer.setLayoutManager(layoutManager);
        mMusicListContainer.setAdapter(adapter);
        mMusicListContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d("test", "onScrolled");

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                    Log.d("test", "loading executed");

                    boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                    if (isRefreshing) {
                        adapter.notifyItemRemoved(adapter.getItemCount());
                        return;
                    }
                    if (!isLoading) {
                        isLoading = true;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getData();
                                Log.d("test", "load more completed");
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });

        //添加点击事件
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("test", "item position = " + position);
                if (listener!=null){
                    listener.onItemClicked(view,data.get(position));
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    public void initData() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 1500);

    }

    /**
     * 获取测试数据
     */
    private void getData() {
        for (int i = 0; i < 6; i++) {
            Map<String, Object> map = new HashMap<>();
            data.add(map);
        }
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
            if (swipeRefreshLayout != null)
                swipeRefreshLayout.setRefreshing(false);
            adapter.notifyItemRemoved(adapter.getItemCount());
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.music_list;
    }

    public void setCardViewClickedListener(OnCardViewItemListener l){
        listener = l;
    }

    public interface OnCardViewItemListener{

        void onItemClicked(View view,Object data);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}

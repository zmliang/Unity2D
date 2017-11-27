package com.goldplusgold.widget.recyclerview;


import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ProgressBar;

import com.goldplusgold.widget.R;
import com.goldplusgold.widget.recyclerview.adapter.RecyclerViewBaseAdapter;

import java.util.ArrayList;

/**
 * User: guangming.chengm
 * Date: 2015-09-21
 * Time: 10:31
 * Add header and footer view
 * Add load more view.
 */
public class RecyclerViewExtended extends RecyclerView {

    public static final int ITEM_VIEW_TYPE_MASK = 0x8000;
    public static final int ITEM_POSITION_MASK = 0x7fff;

    private ArrayList<View> mHeaderViews = new ArrayList<View>();
    private ArrayList<View> mFooterViews = new ArrayList<View>();

    private Adapter mAdapterCustomer;

    private View mEmptyView;

    private boolean isSupportPullUp = false;
    private int mSupportPullPageSize = 20;
    private OnScrollListener mOnScrollListener;
    private View mViewPullLoadMore = null;
    private OnLoadMoreListener mOnLoadMoreListener = null;
    private boolean isLoading = false;

    public RecyclerViewExtended(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecyclerViewExtended(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerViewExtended(Context context) {
        this(context, null);
    }

    /**
     *
     * @param v
     */
    public final void addHeaderView(View v) {
        if (v == null) {
            return;
        }
        mHeaderViews.add(v);

        final Adapter adapter = getAdapter();
        if (adapter != null) {
            if (!(adapter instanceof AdapterHeaderViewList)) {
                setAdapter(new AdapterHeaderViewList(mHeaderViews, mFooterViews, adapter, this));
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        final LayoutManager lm = getLayoutManager();
        if (lm != null) {
            setLayoutManager(lm);
        }
    }

    public final void addFooterView(View v) {
        if (v == null) {
            return;
        }
        mFooterViews.add(v);

        final Adapter adapter = getAdapter();
        if (adapter != null) {
            if (!(adapter instanceof AdapterHeaderViewList)) {
                setAdapter(new AdapterHeaderViewList(mHeaderViews, mFooterViews, adapter, this));
            } else {
                adapter.notifyDataSetChanged();
            }
        }

        final LayoutManager lm = getLayoutManager();
        if (lm != null) {
            setLayoutManager(lm);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout == null) {
            return;
        }
        if (layout instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layout;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (getAdapter().getItemViewType(position) == (RecyclerViewExtended.ITEM_VIEW_TYPE_MASK | position)) { // headerView and footerView
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return 1;
                    }
                }
            });
        }
        super.setLayoutManager(layout);
    }

    public final boolean removeHeaderView(View v) {
        if (mHeaderViews.size() > 0) {
            boolean result = false;
            final Adapter adapter = getAdapter();
            if (adapter != null && ((AdapterHeaderViewList) adapter).removeHeader(v)) {
                adapter.notifyDataSetChanged();
                result = true;
            }
            removeFixedViewInfo(v, mHeaderViews);
            return result;
        }
        return false;
    }

    public final boolean removeFooterView(View v) {
        if (mFooterViews.size() > 0) {
            boolean result = false;
            final Adapter adapter = getAdapter();
            if (adapter != null && ((AdapterHeaderViewList) adapter).removeFooter(v)) {
                adapter.notifyDataSetChanged();
                result = true;
            }
            removeFixedViewInfo(v, mFooterViews);
            return result;
        }
        return false;
    }

    private void removeFixedViewInfo(View v, ArrayList<View> where) {
        int len = where.size();
        for (int i = 0; i < len; ++i) {
            View view = where.get(i);
            if (view == v) {
                where.remove(i);
                break;
            }
        }
    }

    public int getHeaderViewsCount() {
        return mHeaderViews.size();
    }

    public int getFooterViewsCount() {
        return mFooterViews.size();
    }

    public int getItemCount() {
        return getTotalCount() - getHeaderViewsCount() - getFooterViewsCount();
    }

    public int getTotalCount() {
        Adapter adapter = getAdapter();
        if (null == adapter) {
            return 0;
        } else {
            return adapter.getItemCount();
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        this.mAdapterCustomer = adapter;
        if (null != adapter && !(adapter instanceof AdapterHeaderViewList) && (mHeaderViews.size() > 0 || mFooterViews.size() > 0)) {
            super.setAdapter(new AdapterHeaderViewList(mHeaderViews, mFooterViews, adapter, this));
        } else {
            super.setAdapter(adapter);
        }
        if (mAdapterCustomer instanceof RecyclerViewBaseAdapter) {
            ((RecyclerViewBaseAdapter) mAdapterCustomer).setRecyclerViewExtended(this);
        }
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        this.mAdapterCustomer = adapter;
        if (null != adapter && !(adapter instanceof AdapterHeaderViewList)) {
            if (mHeaderViews.size() > 0 || mFooterViews.size() > 0) {
                super.swapAdapter(new AdapterHeaderViewList(mHeaderViews, mFooterViews, adapter, this), removeAndRecycleExistingViews);
                return;
            }
        }
        super.swapAdapter(adapter, removeAndRecycleExistingViews);
    }

    public void setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        emptyView.setVisibility(getCount() == 0 ? View.VISIBLE : View.GONE);
    }

    private int getCount() {
        if (mAdapterCustomer != null) {
            return mAdapterCustomer.getItemCount();
        }
        return 0;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    private void setLoading(boolean isLoading) {
        this.isLoading = isLoading;

        if (mViewPullLoadMore == null) {
            return;
        }
        if (this.isLoading) {
            mViewPullLoadMore.setVisibility(VISIBLE);
        } else {
            mViewPullLoadMore.setVisibility(INVISIBLE);
        }
    }

    /**
     * @param currentPageIndex 0 = firstPageIndex
     */
    public void onLoadError(int currentPageIndex) {
        if (currentPageIndex == 0) {
            // remove loadmore
            onLoadCompletedAction(20, 20, 0);
        } else {
            // add load more
            onLoadCompletedAction(20, 20, 20);
        }

    }

    public void onLoadCompletedAction(boolean isNeedLoadMore) {
        if (isNeedLoadMore) {
            onLoadCompletedAction(20, 20, 20);
        } else {
            onLoadCompletedAction(20, 20, 0);
        }

    }

    public void onLoadCompletedAction(int currentPageIndex, int pageSize, int responseSize) {
        if (responseSize < pageSize) {
            doChangeSupportPullUpStatus(false);
        } else {
            if (currentPageIndex <= 0) {
                mSupportPullPageSize = pageSize;
                setSupportPullUp(null, true, mSupportPullPageSize);
            } else {
                doChangeSupportPullUpStatus(true);
            }
        }
    }

    public void doChangeSupportPullUpStatus(boolean isSupportPullUp) {
        this.isSupportPullUp = isSupportPullUp;
        if (mViewPullLoadMore == null) {
            return;
        }
        if (!isSupportPullUp) {
            removeFooterView(mViewPullLoadMore);
        } else {
            if (mFooterViews.contains(mViewPullLoadMore)) {
                return;
            }
            addFooterView(mViewPullLoadMore);
        }
    }

    /**
     *
     * @param isSupportPullUp
     * @param pageSize
     */
    private void setSupportPullUp(View view, boolean isSupportPullUp, int pageSize) {
        if (this.isSupportPullUp == isSupportPullUp) {
            return;
        }
        this.isSupportPullUp = isSupportPullUp;
        this.mSupportPullPageSize = pageSize;

        if (mOnScrollListener != null) {
            removeOnScrollListener(mOnScrollListener);
        }
        if (isSupportPullUp) {
            if (mOnScrollListener == null) {
                mOnScrollListener = new OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        // 向下滑动，判断最后一个item是不是显示中
                        if (getLayoutManager().getItemCount() >= mSupportPullPageSize && isLastRawVisible(getLayoutManager())) {
                            if (mOnLoadMoreListener == null) {
                                return;
                            }
                            setLoading(true);
                            mOnLoadMoreListener.onLoadMore();
                        }
                    }
                };
            }
            addOnScrollListener(mOnScrollListener);

            if (mViewPullLoadMore != null) {
                mFooterViews.remove(mViewPullLoadMore);
            }
            if (mViewPullLoadMore == null) {
                if (view == null) {
                    mViewPullLoadMore = LayoutInflater.from(getContext()).inflate(R.layout.item_load_more, null);
                    ProgressBar progressBar = (ProgressBar) mViewPullLoadMore.findViewById(R.id.pull_to_refresh_progress);
                    progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.orange_theme_primary), PorterDuff.Mode.SRC_IN);
                } else {
                    mViewPullLoadMore = view;
                }
//                mViewPullLoadMore.setVisibility(INVISIBLE);
            }
            Adapter adapter = null;
            if (getAdapter() != null) {
                adapter = mAdapterCustomer;
                setAdapter(null);
            }
            mFooterViews.add(mViewPullLoadMore);
            setAdapter(adapter);
        } else {
            if (mViewPullLoadMore != null) {
//                Adapter adapter = null;
//                if (getAdapter() != null) {
//                    adapter = mAdapterCustomer;
//                    setAdapter(null);
//                }
                mFooterViews.remove(mViewPullLoadMore);
                setAdapter(mAdapterCustomer);
            }
        }

        if (mViewPullLoadMore != null) {
            mViewPullLoadMore.setVisibility(isSupportPullUp ? VISIBLE : INVISIBLE);
        }
    }

    private boolean isLastRawVisible(LayoutManager layoutManager) {
        if (mFooterViews == null || mFooterViews.isEmpty()) {
            return false;
        }
        if (layoutManager instanceof LinearLayoutManager) { // 向下滑动，判断最后一个item是不是显示中
            int positionLast = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            int itemCount = ((LinearLayoutManager) layoutManager).getItemCount();
            if (positionLast >= (itemCount * 0.8)) {
                return true;
            }
        } else {
            // TODO: implements GirdLayoutManager and StaggeredGridLayoutManager
        }
//        参数：LayoutManager layoutManager, int pos, int spanCount, int childCount
//        } else if (layoutManager instanceof GridLayoutManager) {
//            childCount = childCount - childCount % spanCount;
//            if (pos >= childCount) {// 如果是最后一行，则不需要绘制底部
//                return true;
//            }
//        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
//            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
//            // StaggeredGridLayoutManager 且纵向滚动
//            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
//                childCount = childCount - childCount % spanCount;
//                // 如果是最后一行，则不需要绘制底部
//                if (pos >= childCount)
//                    return true;
//            } else { // StaggeredGridLayoutManager 且横向滚动
//                // 如果是最后一行，则不需要绘制底部
//                if ((pos + 1) % spanCount == 0) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public static class AdapterHeaderViewList extends Adapter implements Filterable {

        private ArrayList<View> mHeaderViews;
        private ArrayList<View> mFooterViews;
        private static final ArrayList<View> EMPTY_INFO_LIST = new ArrayList<View>();

        private final RecyclerView mRecyclerView;
        private final Adapter mAdapter;

        private final boolean mIsFilterable;

        public AdapterHeaderViewList(ArrayList<View> headerViews, ArrayList<View> footerViews, Adapter adapter, RecyclerView recyclerView) {
            mAdapter = adapter;
            mRecyclerView = recyclerView;
            mIsFilterable = adapter instanceof Filterable;

            if (headerViews == null) {
                mHeaderViews = EMPTY_INFO_LIST;
            } else {
                mHeaderViews = headerViews;
            }

            if (footerViews == null) {
                mFooterViews = EMPTY_INFO_LIST;
            } else {
                mFooterViews = footerViews;
            }
        }

        public int getHeadersCount() {
            return mHeaderViews.size();
        }

        public int getFootersCount() {
            return mFooterViews.size();
        }

        public boolean isEmpty() {
            return mAdapter == null || 0 == mAdapter.getItemCount();
        }

        public boolean removeHeader(View v) {
            for (int i = 0; i < mHeaderViews.size(); i++) {
                View view = mHeaderViews.get(i);
                if (view == v) {
                    mHeaderViews.remove(i);
                    return true;
                }
            }

            return false;
        }

        public boolean removeFooter(View v) {
            for (int i = 0; i < mFooterViews.size(); i++) {
                View view = mFooterViews.get(i);
                if (view == v) {
                    mFooterViews.remove(i);
                    return true;
                }
            }
            return false;
        }

        @Override
        public long getItemId(int position) {
            int numHeaders = getHeadersCount();
            if (mAdapter != null && position >= numHeaders) {
                int adjPosition = position - numHeaders;
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemId(adjPosition);
                }
            }
            return NO_ID;
        }

        @Override
        public int getItemCount() {
            if (mRecyclerView instanceof RecyclerViewExtended) {
                RecyclerViewExtended extendedRecyclerView = (RecyclerViewExtended) mRecyclerView;
                boolean isShowEmptyView = (mHeaderViews == null || mHeaderViews.isEmpty())
                        && (mFooterViews == null || mFooterViews.isEmpty())
                        && (mAdapter == null || mAdapter.getItemCount() == 0);
                if (extendedRecyclerView.mEmptyView != null)
                    extendedRecyclerView.mEmptyView.setVisibility(isShowEmptyView ? View.VISIBLE : View.GONE);
                if (isShowEmptyView) {
                    return 0;
                }
            }

            if (mAdapter != null) {
                return getFootersCount() + getHeadersCount() + mAdapter.getItemCount();
            } else {
                return getFootersCount() + getHeadersCount();
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (0 == (viewType & ITEM_VIEW_TYPE_MASK)) {
                if (mAdapter != null) {
                    return mAdapter.onCreateViewHolder(parent, viewType);
                }
            } else {
                int position = viewType & ITEM_POSITION_MASK;
                int numHeaders = getHeadersCount();
                if (position < numHeaders) {
                    return new HeaderViewHolder(mHeaderViews.get(position));
                } else {
                    final int adjPosition = position - numHeaders;
                    int adapterCount = 0;
                    if (mAdapter != null) {
                        adapterCount = mAdapter.getItemCount();
                    }
                    return new HeaderViewHolder(mFooterViews.get(adjPosition - adapterCount));
                }
            }

            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            int numHeaders = getHeadersCount();
            int numFooters = getFootersCount();
            if (position < numHeaders || position >= getItemCount() - numFooters) {
                ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
                if (null == lp) {
                    lp = mRecyclerView.getLayoutManager().generateDefaultLayoutParams();
                    holder.itemView.setLayoutParams(lp);
                }

                if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                    ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
                } else if (lp instanceof LayoutParams) {
                    lp.width = LayoutParams.MATCH_PARENT;
                    lp.height = LayoutParams.MATCH_PARENT;
                }
            } else {
                final int adjPosition = position - numHeaders;
                if (mAdapter != null) {
                    mAdapter.onBindViewHolder(holder, adjPosition);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            int numHeaders = getHeadersCount();
            if (mAdapter != null && position >= numHeaders) {
                int adjPosition = position - numHeaders;
                int adapterCount = mAdapter.getItemCount();
                if (adjPosition < adapterCount) {
                    return mAdapter.getItemViewType(adjPosition);
                }
            }

            return position | ITEM_VIEW_TYPE_MASK;
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            if (mAdapter != null) {
                mAdapter.registerAdapterDataObserver(observer);
            }
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            if (mAdapter != null) {
                mAdapter.unregisterAdapterDataObserver(observer);
            }
        }

        public Filter getFilter() {
            if (mIsFilterable) {
                return ((Filterable) mAdapter).getFilter();
            }
            return null;
        }

        public Adapter getWrappedAdapter() {
            return mAdapter;
        }
    }

    public static class HeaderViewHolder extends ViewHolder {
        public HeaderViewHolder(View view) {
            super(view);
        }
    }
}

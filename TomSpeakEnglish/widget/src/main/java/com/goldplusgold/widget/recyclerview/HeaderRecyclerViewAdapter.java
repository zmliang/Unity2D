package com.goldplusgold.widget.recyclerview;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by sebnapi on 08.11.14.
 * <p/>
 * If you extend this Adapter you are able to add a Header, a Footer or both
 * by a similar ViewHolder pattern as in RecyclerView.
 * <p/>
 * If you want to omit changes to your class hierarchy you can try the Plug-and-Play
 * approach HeaderRecyclerViewAdapterV1.
 * <p/>
 * Don't override (Be careful while overriding)
 * - onCreateViewHolder
 * - onBindViewHolder
 * - getItemCount
 * - getItemViewType
 * <p/>
 * You need to override the abstract methods introduced by this class. This class
 * is not using generics as RecyclerView.Adapter make yourself sure to cast right.
 * <p/>
 * TOTALLY UNTESTED - USE WITH CARE - HAVE FUN :)
 */
public abstract class HeaderRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected static final int TYPE_HEADER = Integer.MIN_VALUE;
    protected static final int TYPE_FOOTER = Integer.MIN_VALUE + 1;

    protected Context mContext;
    protected ArrayList<T> mArrayList;
    protected WeakReference<Fragment> mFragmentRef;

    public HeaderRecyclerViewAdapter(Context ctx) {
        mContext = ctx;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            return onCreateHeaderViewHolder(parent, viewType);
        } else if (viewType == TYPE_FOOTER) {
            return onCreateFooterViewHolder(parent, viewType);
        }
        return onCreateBasicItemViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 0 && holder.getItemViewType() == TYPE_HEADER) {
            onBindHeaderView(holder, position);
        } else if (position == getBasicItemCount() && holder.getItemViewType() == TYPE_FOOTER) {
            onBindFooterView(holder, position);
        } else {
            onBindBasicItemView(holder, position - (useHeader() ? 1 : 0));
        }
    }

    public Context getContext() {
        return mContext;
    }

    public void setFragmentRef(Fragment fragment) {
        mFragmentRef = new WeakReference<Fragment>(fragment);
    }

    public Fragment getFragment() {
        if (mFragmentRef != null) {
            return mFragmentRef.get();
        }
        return null;
    }

    public void addArrayList(ArrayList<T> tArray) {
        if (mArrayList == null) {
            return;
        }

        mArrayList.addAll(tArray);
        notifyDataSetChanged();
    }

    public void addArrayList(int index, ArrayList<T> tArray) {
        if (mArrayList == null) {
            return;
        }

        mArrayList.addAll(index, tArray);
        notifyDataSetChanged();
    }

    public ArrayList<T> getArrayList() {
        return mArrayList;
    }

    public void setArrayList(ArrayList<T> pArray) {
        mArrayList = pArray;

        notifyDataSetChanged();
    }

    public void removeItem(T object) {
        mArrayList.remove(object);
        notifyDataSetChanged();
    }

    public void removeAll() {
        if (mArrayList == null || mArrayList.size() == 0) {
            return;
        }
        mArrayList.clear();
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }


    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (mArrayList != null) {
            itemCount = mArrayList.size();
        }
        if (useHeader()) {
            itemCount += 1;
        }
        if (useFooter()) {
            itemCount += 1;
        }
        return itemCount;
    }

    public int getHeaderCount(){
        return useHeader() ? 1 : 0;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && useHeader()) {
            return TYPE_HEADER;
        }
        if (position == getBasicItemCount() + (useHeader() ? 1 : 0) && useFooter()) {
            return TYPE_FOOTER;
        }
        return getBasicItemType(position - (useHeader() ? 1 : 0));
    }

    public abstract boolean useHeader();

    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindHeaderView(RecyclerView.ViewHolder holder, int position);

    public abstract boolean useFooter();

    public abstract RecyclerView.ViewHolder onCreateFooterViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindFooterView(RecyclerView.ViewHolder holder, int position);

    public abstract RecyclerView.ViewHolder onCreateBasicItemViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindBasicItemView(RecyclerView.ViewHolder holder, int position);

    public abstract int getBasicItemCount();

    /**
     * make sure you don't use [Integer.MAX_VALUE-1, Integer.MAX_VALUE] as BasicItemViewType
     */
    public abstract int getBasicItemType(int position);

}

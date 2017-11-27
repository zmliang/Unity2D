package com.goldplusgold.widget.recyclerview.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.goldplusgold.widget.recyclerview.RecyclerViewExtended;
import com.goldplusgold.widget.recyclerview.listener.OnItemClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by guangming.chengm on 15/9/8.
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RecyclerViewBaseAdapter.ViewHolder> {
    private final String TAG = RecyclerViewBaseAdapter.class.getSimpleName();
    protected Context mContext;
    protected ArrayList<T> mArrayList;
    protected WeakReference<Fragment> mFragmentRef;
    private RecyclerViewExtended mRecyclerViewExtended;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public RecyclerViewBaseAdapter(Context ctx) {
        mContext = ctx;
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

    public ArrayList<T> getArrayList() {
        return mArrayList;
    }

    public void setArrayList(ArrayList<T> pArray) {
        mArrayList = pArray;
        notifyDataSetChanged();

//        notifyItemRangeChanged(0, getItemCount());
    }

    public void addArrayList(ArrayList<T> pArray) {
        if (mArrayList == null) {
            setArrayList(pArray);
            notifyDataSetChanged();
        } else {
            int posStart = mArrayList.size();
            mArrayList.addAll(pArray);
            notifyItemRangeChanged(posStart, pArray.size());
        }
    }

    public boolean isEmpty() {
        return mArrayList == null || mArrayList.isEmpty();
    }

    public void removeItem(int position) {
        mArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeAll() {
        if (mArrayList == null || mArrayList.size() == 0) {
            return;
        }
        int itemCount = getItemCount();
        mArrayList.clear();
        notifyItemRangeRemoved(0, itemCount);
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(getContext());
    }

    @Override
    public void onBindViewHolder(RecyclerViewBaseAdapter.ViewHolder holder, int position) {
        if (holder != null) {
            if (mOnItemClickListener != null) {
                holder.setOnItemClickListener(mOnItemClickListener);
            }
            holder.bindViewHolderAction(position);
        }
    }

    @Override
    public int getItemCount() {
        if (mArrayList != null) {
            return mArrayList.size();
        }
        Log.i(TAG,"getItemCount == 0");
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItem(int position) {
        if (mArrayList != null) {
            if (position >= 0 && position < mArrayList.size()) {
                return mArrayList.get(position);
            }
        }
        return null;
    }

    public void startActivityForResult(Intent intent, int requestCode) {
        if (getFragment() != null) {
            getFragment().startActivityForResult(intent, requestCode);
        } else {
            ((Activity) getContext()).startActivityForResult(intent, requestCode);
        }
    }

    public void setRecyclerViewExtended(RecyclerViewExtended recyclerViewExtended) {
        mRecyclerViewExtended = recyclerViewExtended;
    }


    /**
     * Created by guangming.chengm on 15/9/23.
     */
    public abstract class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        protected OnItemClickListener mOnItemClickListener = null;

        public ViewHolder(View contentView) {
            super(contentView);
            createViewHolderAction(contentView);

            contentView.setOnClickListener(this);
            contentView.setOnLongClickListener(this);
        }

        public ViewHolder(View contentView, OnItemClickListener onItemClickListener) {
            super(contentView);
            createViewHolderAction(contentView);

            contentView.setOnClickListener(this);
            contentView.setOnLongClickListener(this);

            if (onItemClickListener != null) {
                mOnItemClickListener = onItemClickListener;
            }
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.mOnItemClickListener = onItemClickListener;
        }

        protected abstract void createViewHolderAction(View contentView);

        public abstract void bindViewHolderAction(int position);

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getRealPosition());
            }
        }

        @Override
        public boolean onLongClick(View view) {
            return mOnItemClickListener != null && mOnItemClickListener.onItemLongClick(view, getRealPosition());
        }

        public int getRealPosition() {
            int position = getLayoutPosition();
            if (mRecyclerViewExtended != null) {
                position -= mRecyclerViewExtended.getHeaderViewsCount();
            }
            return position;
        }
    }
}

package com.jinke.group.baselib;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public abstract class BaseFragment extends Fragment {

    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    protected Dialog mDialog;

    private RxPermissions rxPermissions;
    private Unbinder unbinder;
    private Toast mToast;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        //绑定fragment
        unbinder = ButterKnife.bind(this, view);
        initView();

        return view;
    }

    protected void initView() {
    }


    abstract public int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public boolean isActivityAvailable() {
        return !(getActivity() == null || getActivity().isFinishing()
                || !isAdded());
    }

    public void showLoadingControl() {
        if (isActivityAvailable()) {
            showLoadingControl(null, true);
        }
    }

    protected void showLoadingControl(String msg, boolean isCancelable) {

        if (mDialog == null) {
            mDialog = new Dialog(getActivity(), R.style.loading_dialog_style);
            mDialog.setContentView(R.layout.layout_loading_dialog);
            mDialog.setCanceledOnTouchOutside(isCancelable);
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    onDialogLoadingCancel();
                }
            });
        }
        mDialog.show();
    }

    public void dismisLoadingControl() {
        dismissDialog();
    }

    private void dismissDialog() {

        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        } catch (Exception ignored) {
        } finally {
            mDialog = null;
        }
    }

    public void onDialogLoadingCancel() {
        //mDialog.cancel();
    }

    protected void showToastMessage(int msgId, int duration) {
        if (!isActivityAvailable()) {
            return;
        }
        showToastMessage(getString(msgId), duration);
    }

    protected void showToastMessage(String msg, int duration) {
        if (getActivity() == null) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), msg, duration);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    public void onPermissions(String... parms) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(getActivity());
        }

        rxPermissions.request(parms).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {

                } else {
                    showToastMessage("权限授予失败", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public ActionBar getSupportActionBar() {
        return ((BaseActivity) getActivity()).getSupportActionBar();
    }

}

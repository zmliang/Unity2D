package com.jinke.group.baselib;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.jinkegroup.supportlib.util.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.functions.Consumer;

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = BaseActivity.class.getSimpleName();
    protected Toast mToast;
    protected Dialog mDialog;
    protected Unbinder unbinder;
    private RxPermissions rxPermissions;




    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);

        ActionBar bar = getSupportActionBar();
        if (bar!=null){
            Logger.i(TAG,"actionbar not null");
            View view = LayoutInflater.from(this).inflate(getLayoutTitleId(),null);
            ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
                    ActionBar.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL | Gravity.CENTER_HORIZONTAL;
            bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            bar.setDisplayShowHomeEnabled(false);
            bar.setDisplayShowCustomEnabled(true);
            bar.setDisplayShowTitleEnabled(false);
            bar.setCustomView(view, layoutParams);
            Toolbar parent = (Toolbar) view.getParent();
            parent.setContentInsetsAbsolute(0, 0);
            findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //finish();
                    back();
                }
            });
        }
    }

    protected abstract void back();

    /**
     * 布局文件
     *
     * @return
     */
    public int getLayoutId() {
        return R.layout.activity_base_layout;
    }

    public int getLayoutTitleId() {
        return R.layout.titleview;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    public void showToastMessage(int msgId, int duration) {
        if (isFinishing()) {
            return;
        }
        showToastMessage(getString(msgId), duration);
    }

    public void showToastMessage(String msg, int duration) {
        if (isFinishing()) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this.getApplicationContext(), msg, duration);
        }
        mToast.setText(msg);
        mToast.setDuration(duration);
        mToast.show();
    }

    public void showLoadingControl() {
        showLoadingControl(null, true);
    }

    protected void showLoadingControl(String msg, boolean isCancelable) {

        if (mDialog == null) {
            mDialog = new Dialog(this, R.style.loading_dialog_style);
            mDialog.setContentView(R.layout.layout_loading_dialog);
            if (msg!=null )
            ((TextView)mDialog.findViewById(R.id.id_tv_loading_dialog_text)).setText(msg);
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

    protected void showLoadingControl(int stringID, boolean isCancelable){
        showLoadingControl(getString(stringID),isCancelable);
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
    }

    public void onPermissions(final Activity activity, final String... parms) {
        if (rxPermissions == null) {
            rxPermissions = new RxPermissions(this);
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


}

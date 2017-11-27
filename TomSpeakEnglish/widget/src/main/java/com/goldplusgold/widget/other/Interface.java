package com.goldplusgold.widget.other;

import android.graphics.Bitmap;
import android.os.Bundle;

public class Interface {

    public interface OnValueSetClickListener {
        public void onValueSet(String value);
    }

    public interface setOnGetImageListener {
        void onImageTask(Bitmap result);
    }

    public interface onBannerClickListener {
        public void clickImage();
    }

    public interface CallBack {
        public void call(Bundle arg);
    }
}

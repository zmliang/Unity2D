package com.jinkegroup.iflyteklib;

import android.content.Context;

import com.iflytek.cloud.EvaluatorListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechEvaluator;
import com.jinkegroup.iflyteklib.ise.result.entity.Parameters;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/19
 * CopyRight:  JinkeGroup
 */

public class SpeechEvaluateHelper {
    private static SpeechEvaluateHelper instance;

    private SpeechEvaluator mIse = null;
    private Parameters mParameters = null;

    //测评监听接口
    private EvaluatorListener mEvaluatorListener =   null;
    /*new EvaluatorListener() {
        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            //volume :当前音量， data：返回的音频数据
        }

        @Override
        public void onBeginOfSpeech() {
            //此回调表示：SDK内部录音机已经准备好了，用户可以开始语音输入。
        }

        @Override
        public void onEndOfSpeech() {
            //此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入。
        }

        @Override
        public void onResult(EvaluatorResult evaluatorResult, boolean isLast) {
            if (isLast){
                StringBuilder builder = new StringBuilder();
                builder.append(evaluatorResult.getResultString());
                if (!TextUtils.isEmpty(builder)){

                }
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            //
        }

        @Override
        public void onEvent(int eventType, int i1, int i2, Bundle bundle) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };
    */
    private SpeechEvaluateHelper(){

    }

    static {
        instance = new SpeechEvaluateHelper();
    }

    public static synchronized SpeechEvaluateHelper getInstance(){
        return instance;
    }

    public void createSE(Context context, EvaluatorListener listener){
        if (mIse == null){
            mIse = SpeechEvaluator.createEvaluator(context,null);
        }
        this.mEvaluatorListener = listener;
        this.mParameters = new Parameters();
    }

    public void setEvaluatorListener(EvaluatorListener listener){
        this.mEvaluatorListener = listener;
    }

    public void startSE(String eveText){
        if (mIse != null && mEvaluatorListener!=null && mParameters!=null){
            setParameter();
            mIse.startEvaluating(eveText,null,mEvaluatorListener);
        }
    }

    public void setParameter(Parameters parameters){
        this.mParameters = parameters;
    }

    private void setParameter(){

        mIse.setParameter(SpeechConstant.LANGUAGE, mParameters.getLanguage());
        mIse.setParameter(SpeechConstant.ISE_CATEGORY, mParameters.getCategory());
        mIse.setParameter(SpeechConstant.TEXT_ENCODING, mParameters.getText_coding());
        mIse.setParameter(SpeechConstant.VAD_BOS, mParameters.getVad_bos());
        mIse.setParameter(SpeechConstant.VAD_EOS, mParameters.getVad_eos());
        mIse.setParameter(SpeechConstant.KEY_SPEECH_TIMEOUT, mParameters.getSpeech_timeout());
        mIse.setParameter(SpeechConstant.RESULT_LEVEL, mParameters.getResult_level());

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIse.setParameter(SpeechConstant.AUDIO_FORMAT,mParameters.getAudio_format());
        mIse.setParameter(SpeechConstant.ISE_AUDIO_PATH, mParameters.getAudio_path());
    }

    public void stopSE(){
        if (mIse!=null)
            mIse.stopEvaluating();
    }

    public void cancelSE(){
        if (mIse!=null)
            mIse.cancel();
    }

    public Parameters getmParameters(){
        return this.mParameters;
    }

    public String getParametersOf(String tag){
        if (mIse ==null)
            return "";
        return mIse.getParameter(tag);
    }


    public boolean isEvaluating(){
        if (mIse ==null)
            return false;
        return mIse.isEvaluating();
    }

    public void destorySE(){
        if (mIse!=null){
            mIse.destroy();
            mIse = null;
        }
        mParameters = null;
        mEvaluatorListener = null;
    }
}

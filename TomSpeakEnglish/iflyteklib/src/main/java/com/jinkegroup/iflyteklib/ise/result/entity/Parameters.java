package com.jinkegroup.iflyteklib.ise.result.entity;

import android.os.Environment;

import java.io.Serializable;

/**
 * Author   :  Tomcat
 * Date     :  2017/10/19
 * CopyRight:  JinkeGroup
 */

public class Parameters implements Serializable {

    private String language ;

    private String category ;

    private String text_coding ;

    private String vad_bos ;

    private String vad_eos ;

    private String speech_timeout;

    private String result_level ;

    private String audio_format ;

    private String audio_path;

    public Parameters(){
        this.language = "en_us";
        this.category = "read_word";
        this.text_coding = "utf-8";
        this.vad_bos = "5000";
        this.vad_eos = "1800";
        this.speech_timeout = "-1";
        this.result_level = "complete";
        this.audio_format = "wav";
        this.audio_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/msc/ise."+this.audio_format;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getText_coding() {
        return this.text_coding;
    }

    public void setText_coding(String text_coding) {
        this.text_coding = text_coding;
    }

    public String getVad_bos() {
        return this.vad_bos;
    }

    public void setVad_bos(String vad_bos) {
        this.vad_bos = vad_bos;
    }

    public String getVad_eos() {
        return this.vad_eos;
    }

    public void setVad_eos(String vad_eos) {
        this.vad_eos = vad_eos;
    }

    public String getSpeech_timeout() {
        return this.speech_timeout;
    }

    public void setSpeech_timeout(String speech_timeout) {
        this.speech_timeout = speech_timeout;
    }

    public String getResult_level() {
        return this.result_level;
    }

    public void setResult_level(String result_level) {
        this.result_level = result_level;
    }

    public String getAudio_format() {
        return this.audio_format;
    }

    public void setAudio_format(String audio_format) {
        this.audio_format = audio_format;
    }

    public String getAudio_path() {
        return this.audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }
}

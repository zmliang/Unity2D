package com.jinkegroup.edu.se.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.airbnb.lottie.LottieAnimationView;
import com.jinkegroup.edu.se.R;
import com.jinkegroup.supportlib.util.Logger;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Author   :  Tomcat
 * Date     :  2017/11/1
 * CopyRight:  JinkeGroup
 */

public class SceneDialogueActivity extends BaseFragmentActivity {
    private final String TAG = SceneDialogueActivity.class.getSimpleName();

    @BindView(R.id.scene_animation)
    LottieAnimationView sceneView;

    @BindView(R.id.speak_english)
    ImageButton speakEnglish;

    @BindView(R.id.play_game)
    ImageButton playGame;

    @BindView(R.id.main_scene)
    ViewGroup sceneContainer;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Slide slideExitTransition = new Slide(Gravity.BOTTOM);
        slideExitTransition.excludeTarget(R.id.tom_custom_title,true);
        slideExitTransition.excludeTarget(android.R.id.navigationBarBackground, true);
        slideExitTransition.excludeTarget(android.R.id.statusBarBackground, true);
        getWindow().setExitTransition(slideExitTransition);

        Slide enter = new Slide(Gravity.TOP);
        enter.excludeTarget(R.id.tom_custom_title,true);
        enter.excludeTarget(android.R.id.navigationBarBackground, true);
        enter.excludeTarget(android.R.id.statusBarBackground, true);
        enter.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                getWindow().getEnterTransition().removeListener(this);
                sceneView.animate().setStartDelay(0)
                        .scaleY(1).scaleX(1);
                playGame.animate().setStartDelay(30)
                        .scaleY(1).scaleX(1);
                speakEnglish.animate().setStartDelay(60)
                        .scaleX(1).scaleY(1);
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
        return R.layout.main_scene;
    }


    @Override
    public void back(){
        sceneView.animate().setStartDelay(0)
                .scaleY(0).scaleX(0);
        playGame.animate().setStartDelay(30)
        .scaleY(0).scaleX(0);
        speakEnglish.animate().setStartDelay(60)
                .scaleX(0).scaleY(0)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        finishAfterTransition();
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        sceneContainer = null;
        speakEnglish = null;
        playGame = null;
        sceneView = null;
    }


}

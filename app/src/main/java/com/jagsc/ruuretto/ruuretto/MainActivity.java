package com.jagsc.ruuretto.ruuretto;

import android.animation.Animator;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
    boolean flags_[];
    int cnt_;
    boolean re_;
    CharSequence preview_;
    Button btn_;
    TextView tv_;
    TextView pv_;
    ImageView iv_;
    ImageView i2v_;
    Spring spring_;

    private void reset(){
        flags_ = new boolean[75];
        for( int i = 0; i < 75; ++i ){
            TextView cellv_ = (TextView) findViewById(getResources().getIdentifier("c"+(i+1), "id", getPackageName()));
            cellv_.animate().alpha(0.2f).setStartDelay(200).start();
        }
        cnt_ = 0;
        btn_.setText(R.string.zoi);
        tv_.setText("");
        iv_.setVisibility(View.INVISIBLE);
        i2v_.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_ = (Button)findViewById(getResources().getIdentifier("button","id",getPackageName()));
        tv_ = (TextView)findViewById(getResources().getIdentifier("ransuu","id",getPackageName()));
        pv_ = (TextView)findViewById(getResources().getIdentifier("preview","id",getPackageName()));
        iv_ = (ImageView) findViewById(getResources().getIdentifier("meu", "id", getPackageName()));
        i2v_ = (ImageView) findViewById(getResources().getIdentifier("zoi", "id", getPackageName()));

        SpringSystem springSystem = SpringSystem.create();
        spring_ = springSystem.createSpring();
        spring_.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringUpdate(Spring spring) {
                // You can observe the updates in the spring
                // state by asking its current value in onSpringUpdate.
                float value = (float) spring.getCurrentValue();
                float scale = 1f - (value * 0.3f);
                tv_.setScaleX(scale);
                tv_.setScaleY(scale);
            }
        });

        flags_ = new boolean[75];
        cnt_ = 0;
        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i2v_.setVisibility(View.INVISIBLE);
                if( !re_ ){
                    Random r_ = new java.util.Random();
                    int v_;
                    do
                    {
                        v_ = r_.nextInt(75);
                    }
                    while( flags_[v_] );
                    flags_[v_] = true;
                    cnt_ += 1;

                    TextView cv_ = (TextView) findViewById(getResources().getIdentifier("c"+(v_+1), "id", getPackageName()));
                    cv_.setX(cv_.getX() + 30);
                    cv_.setAlpha(0);
                    cv_.animate().alpha(1).setStartDelay(300).xBy(-30).start();

                    if( cnt_ != 75 ){
                        pv_.animate().alpha(0).x(40).setStartDelay(150).setListener(new Animator.AnimatorListener() {
                            @Override public void onAnimationStart(Animator animation) {}
                            @Override public void onAnimationEnd(Animator animation) {
                                TextView pv_ = (TextView)findViewById(getResources().getIdentifier("preview","id",getPackageName()));
                                pv_.setText(preview_);
                                pv_.setX(100);
                                animation.removeAllListeners();
                                pv_.animate().x(70).alpha(1).setListener(new Animator.AnimatorListener() {
                                    @Override public void onAnimationStart(Animator animation) {}
                                    @Override public void onAnimationEnd(Animator animation) {}
                                    @Override public void onAnimationCancel(Animator animation) {}
                                    @Override public void onAnimationRepeat(Animator animation) {}
                                }).setStartDelay(150).start();
                            }
                            @Override public void onAnimationCancel(Animator animation) {}
                            @Override public void onAnimationRepeat(Animator animation) {                        }
                        }).start();
                        preview_ = tv_.getText();
                        tv_.setText("" + (v_ + 1));
                        spring_.setEndValue(1);
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                spring_.setEndValue(0);
                            }
                        }, 100);
                    }
                    else{
                        btn_.setText(R.string.meu);
                        tv_.setText("");
                        pv_.animate().alpha(0).x(40).setStartDelay(150).setListener(new Animator.AnimatorListener() {
                            @Override public void onAnimationStart(Animator animation) {}
                            @Override public void onAnimationEnd(Animator animation) {
                                pv_.setText("");
                            }
                            @Override public void onAnimationCancel(Animator animation) {}
                            @Override public void onAnimationRepeat(Animator animation) {                        }
                        }).start();
                        ImageView iv_ = (ImageView) findViewById(getResources().getIdentifier("meu", "id", getPackageName()));
                        iv_.setVisibility(View.VISIBLE);
                        re_ = true;
                    }
                }
                else
                {
                    reset();
                    re_ = false;
                }
            }
        });
    }
}

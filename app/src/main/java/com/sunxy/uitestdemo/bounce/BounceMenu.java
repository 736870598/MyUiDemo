package com.sunxy.uitestdemo.bounce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.sunxy.uitestdemo.R;

/**
 * Created by sunxiaoyu on 2017/1/6.
 */

public class BounceMenu {


    /**
     * 布局高度
     */
    private int mMaxHeight = 500;
    /**
     * 辅助控制点最大高度
     */
    private int maxBounceHeight = 300;
    /**
     * 上升动画时间
     */
    private long showTime = 600;
    /**
     * 回弹动画时间
     */
    private long bounceTime = 300;


    private static BounceMenu bounceMenu;
    private BounceViewAdapter adapter;

    public static BounceMenu makeView(Context context, View childView, BounceViewAdapter adapter){
        bounceMenu = new BounceMenu(context, childView, adapter);
        return bounceMenu;
    }


    private Context context;
    private boolean isShowing;
    private ViewGroup doctView;
    private View rootView;
    private RecyclerView recyclerView;

    public BounceMenu(Context context, View childView, BounceViewAdapter adapter) {
        this.context = context;
        this.adapter = adapter;
        doctView = findDoctView(childView);
    }

    private ViewGroup findDoctView(View childView){
        if(childView != null){
            if (childView instanceof FrameLayout){
                if (childView.getId() == android.R.id.content){
                    return (ViewGroup)childView;
                }
            }
            ViewParent viewParent = childView.getParent();
            childView = (viewParent instanceof View ? (View)viewParent : null);
            return findDoctView(childView);

        }
        return null;
    }

    public boolean isShowing(){
        return isShowing;
    }

    public BounceMenu show(){
        dismiss(true);

        if(doctView != null){
            rootView = LayoutInflater.from(context).inflate(R.layout.bounce_menu_layout, null, false);
            doctView.addView(rootView);
            isShowing = true;
            recyclerView = (RecyclerView) rootView.findViewById(R.id.bounce_recycleview);
            if(adapter != null){
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
            }
            BounceView bounceView = (BounceView) rootView.findViewById(R.id.bounce_view);
            bounceView.setmMaxHeight(mMaxHeight);
            bounceView.setMaxBounceHeight(maxBounceHeight);
            bounceView.setShowTime(showTime);
            bounceView.setBounceTime(bounceTime);
            recyclerView.getLayoutParams().height = mMaxHeight;
            bounceView.show();
        }

        Animation tAnimation = AnimationUtils.loadAnimation(context, R.anim.show_anim);
        tAnimation.setDuration(showTime + bounceTime);
        recyclerView.startAnimation(tAnimation);

        return this;
    }

    public void dismiss(boolean isFast){
        if(rootView != null && doctView != null && rootView.getParent() != null){
            if(!isFast){
                ObjectAnimator animator = ObjectAnimator.ofFloat(rootView, "translationY", 0 , mMaxHeight);
                animator.setDuration(showTime);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        doctView.removeView(rootView);
                        isShowing = false;
                    }
                });
                animator.start();
            }else{
                doctView.removeView(rootView);
            }
        }
        rootView = null;
    }
}

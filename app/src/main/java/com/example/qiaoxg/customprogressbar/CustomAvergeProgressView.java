package com.example.qiaoxg.customprogressbar;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qiaoxg on 2018/9/10.
 */

public class CustomAvergeProgressView extends RelativeLayout {

    private RelativeLayout mScaleParentView;

    private int mScreenWidth;

    private View mRootView;

    private List<LinearLayout> linearLayoutList = new ArrayList<>();

    public CustomAvergeProgressView(Context context) {
        super(context);
        initView();
    }

    public CustomAvergeProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomAvergeProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_averge_progress, this);

        mScaleParentView = mRootView.findViewById(R.id.item_scale_view);

        LinearLayout l1 = mRootView.findViewById(R.id.parentView_ll1);
        LinearLayout l2 = mRootView.findViewById(R.id.parentView_ll2);
        LinearLayout l3 = mRootView.findViewById(R.id.parentView_ll3);
        LinearLayout l4 = mRootView.findViewById(R.id.parentView_ll4);
        LinearLayout l5 = mRootView.findViewById(R.id.parentView_ll5);
        LinearLayout l6 = mRootView.findViewById(R.id.parentView_ll6);

        linearLayoutList.add(l1);
        linearLayoutList.add(l2);
        linearLayoutList.add(l3);

        linearLayoutList.add(l4);
        linearLayoutList.add(l5);
        linearLayoutList.add(l6);

        mScreenWidth = getScreenWidth(getContext()) - dpTopx(getContext(), 30);
    }

    public void setData(List<Integer> list, int mMaxProgress, int mCurrProgress) {

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout parentView = new RelativeLayout(getContext());
        parentView.setBackgroundColor(getResources().getColor(R.color.white));
        parentView.setLayoutParams(layoutParams1);


        int kedu = mMaxProgress / (list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            View scaleView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_progress_scale, null, false);
            TextView tv = scaleView.findViewById(R.id.item_number);
            tv.setText(list.get(i) + "");

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int marginLeft = (kedu * i) * mScreenWidth / mMaxProgress;
            layoutParams.setMargins(marginLeft, 0, 0, 0);
            scaleView.setLayoutParams(layoutParams);
            parentView.addView(scaleView);

        }
        mScaleParentView.addView(parentView);


        for (int i = 0; i < linearLayoutList.size(); i++) {
            linearLayoutList.get(i).setVisibility(GONE);
            linearLayoutList.get(i).removeAllViews();
        }

        int size = list.size();
        List<ProgressBar> progressBarList = new ArrayList<>();
        for (int i = 1; i < size; i++) {
            View scaleView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_averge_progress_scale, null, false);

            TextView tv = scaleView.findViewById(R.id.scaleNumber_tv);
            tv.setText(list.get(i) + "");

            ProgressBar pb = scaleView.findViewById(R.id.progressBar);
            pb.setMax(list.get(i));
            if (i == 1) {
                pb.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.style_mine_progress_start));
            } else if (i == size - 1) {
                pb.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.style_mine_progress_end));
            } else {
                pb.setProgressDrawable(getContext().getResources().getDrawable(R.drawable.style_mine_progress_mid));
            }

            progressBarList.add(pb);
            linearLayoutList.get(i - 1).setVisibility(VISIBLE);
            linearLayoutList.get(i - 1).addView(scaleView);

        }


        int pbPosition = 0;
        if (mCurrProgress > list.get(size - 1)) {
            pbPosition = size;
        } else {
            for (int i = 0; i < list.size() - 1; i++) {

                if (mCurrProgress > list.get(i) && mCurrProgress <= list.get(i + 1)) {
                    pbPosition = i;
                    break;
                }
            }

        }

        for (int i = 0; i < size - 1; i++) {
            if (i < pbPosition) {
                progressBarList.get(i).setProgress(list.get(i + 1));
            } else if (pbPosition == i) {
                progressBarList.get(i).setProgress(mCurrProgress);
            } else {
                progressBarList.get(i).setProgress(0);
            }
        }
    }

    public int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public int dpTopx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

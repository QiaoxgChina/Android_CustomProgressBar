package com.example.qiaoxg.customprogressbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Qiaoxg on 2018/9/10.
 */

public class CustomProgressView extends RelativeLayout {

    private RelativeLayout mScaleParentView;

    private ProgressBar mProgressBar;

    private int mScreenWidth;

    private View mRootView;

    public CustomProgressView(Context context) {
        super(context);
        initView();
    }

    public CustomProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mRootView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_progress, this);
        mScaleParentView = mRootView.findViewById(R.id.item_scale_view);
        mProgressBar = mRootView.findViewById(R.id.item_progressBar);
        mScreenWidth = getScreenWidth(getContext()) - dpTopx(getContext(), 30);
//        mScreenWidth = ScreenUtils.getScreenWidth(getContext());
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

    public void setData(List<Integer> list, int maxProgress, int currProgress) {
        int keduPix = mScreenWidth / (list.size() - 1);
        mProgressBar.setMax(mScreenWidth);

        int numberSize = list.size();
        int pbPosition = 0;
        for (int i = 0; i < numberSize - 1; i++) {
            if (currProgress > list.get(i) && currProgress <= list.get(i + 1)) {
                pbPosition = i;
                break;
            }
        }

        int currFanWei = list.get(pbPosition + 1) - list.get(pbPosition);
        int duoYu = currProgress - list.get(pbPosition);
        int DuoYuPix = keduPix * duoYu / currFanWei;
        int fullPix = keduPix * pbPosition;
        mProgressBar.setProgress(fullPix + DuoYuPix);
        mScaleParentView.removeAllViews();

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout parentView = new RelativeLayout(getContext());
        parentView.setBackgroundColor(getResources().getColor(R.color.white));
        parentView.setLayoutParams(layoutParams1);

        int kedu = maxProgress / (list.size() - 1);
        for (int i = 0; i < list.size(); i++) {
            View scaleView = LayoutInflater.from(getContext()).inflate(R.layout.view_custom_progress_scale, null, false);
            TextView tv = scaleView.findViewById(R.id.item_number);
            tv.setText(list.get(i) + "");

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int marginLeft = (kedu * i) * mScreenWidth / maxProgress;
            layoutParams.setMargins(marginLeft, 0, 0, 0);
            scaleView.setLayoutParams(layoutParams);
            parentView.addView(scaleView);

        }
        mScaleParentView.addView(parentView);
    }

}

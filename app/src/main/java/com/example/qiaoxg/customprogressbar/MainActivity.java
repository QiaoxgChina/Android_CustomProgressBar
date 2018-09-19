package com.example.qiaoxg.customprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomAvergeProgressView mCustomProgressView;
    private CustomProgressView mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomProgressView = findViewById(R.id.customProgressBar);
        mProgressView = findViewById(R.id.CustomProgressView);


        List<Integer> list = new ArrayList<>();
        list.add(0);
        list.add(10);
        list.add(40);
        list.add(70);
        list.add(300);
        list.add(500);

        mCustomProgressView.setData(list, list.get(list.size() - 1), 80);
//        progressBar.setProgress(80);
        mProgressView.setData(list, list.get(list.size() - 1), 520);

        findViewById(R.id.showBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Integer> list1 = new ArrayList<>();
                list1.add(0);
                list1.add(40);
                list1.add(100);
//                progressBar.setProgress(40);
                mCustomProgressView.setData(list1, list1.get(list1.size() - 1),  40);
                mProgressView.setData(list1, list1.get(list1.size() - 1),  40);
            }
        });

        isFirst = true;


    }

    private boolean isFirst;

    @Override
    protected void onResume() {
        super.onResume();

        if (isFirst) {
            isFirst = false;
            return;
        }
        List<Integer> list1 = new ArrayList<>();
        list1.add(0);
        list1.add(30);
        list1.add(60);
        list1.add(100);
        mCustomProgressView.setData(list1, list1.get(list1.size() - 1),  50);
        mProgressView.setData(list1, list1.get(list1.size() - 1),  50);
    }
}

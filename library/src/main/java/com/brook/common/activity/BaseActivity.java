package com.brook.common.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Activity 基类
 * Created by zhangliang on 2017/7/25.
 */

public class BaseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

package com.chrischeng.router.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.annotation.RouterRule;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.demo.interceptor.FirstActivityInterceptor;
import com.chrischeng.router.demo.interceptor.SecondActivityInterceptor;

@RouterInterceptor({FirstActivityInterceptor.class, SecondActivityInterceptor.class})
@RouterRule("first")
public class FirstActivity extends TargetActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);

        String
    }
}

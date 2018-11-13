package com.chrischeng.router.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.chrischeng.router.annotation.RouterInterceptor;
import com.chrischeng.router.annotation.RouterProtocal;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.demo.interceptor.TargetActivityInterceptor;

@RouterInterceptor(TargetActivityInterceptor.class)
@RouterProtocal("target")
public class TargetActivity extends AppCompatActivity {

    public static final String KEY_YEAR = "year";
    public static final String KEY_MONTH = "month";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;

        Log.d("aaa", "year:" + bundle.get(KEY_YEAR));
        Log.d("aaa", "month:" + bundle.get(KEY_MONTH));
    }
}

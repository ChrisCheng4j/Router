package com.chrischeng.router.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chrischeng.router.Router;
import com.chrischeng.router.annotation.RouterRule;
import com.chrischeng.router.demo.R;

@RouterRule("main")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_first).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_first:
                Router.create("router://first?").route(this);
                break;
        }
    }
}

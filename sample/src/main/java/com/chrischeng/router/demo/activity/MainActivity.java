package com.chrischeng.router.demo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.chrischeng.router.Router;
import com.chrischeng.router.annotation.RouterProtocal;
import com.chrischeng.router.callback.RouterCallback;
import com.chrischeng.router.demo.R;
import com.chrischeng.router.rule.RouterRule;

import java.util.Calendar;
import java.util.Locale;

@RouterProtocal("main")
public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RouterCallback routerCallback = new RouterCallback() {
            @Override
            public void onSuccess(Uri uri, RouterRule rule) {
                Toast.makeText(MainActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(Uri uri, Throwable e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        findViewById(R.id.tv_target).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                String uri = String.format(Locale.getDefault(), "router://target?year=%1d&month=%2d&day=%3d", year, month, day);
                Router.create(uri)
                        .setCallback(routerCallback)
                        .requestCode(REQUEST_CODE)
                        .overridePendingTransition(0, 0)
                        .route(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
            Toast.makeText(MainActivity.this, String.valueOf(resultCode), Toast.LENGTH_SHORT).show();
    }
}

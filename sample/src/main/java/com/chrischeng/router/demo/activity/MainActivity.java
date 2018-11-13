package com.chrischeng.router.demo.activity;

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

                String uri = String.format(Locale.getDefault(), "router://target?year=%d&month=%s", year, String.valueOf(month));
                Router.create(uri)
                        .setCallback(routerCallback)
                        .route(MainActivity.this);
            }
        });
    }
}

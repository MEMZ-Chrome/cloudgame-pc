package com.memzchrome.cloudgame;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AboutActivity extends Activity {

    private int versionClickCount = 0;
    private boolean developerModeEnabled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        TextView appName = findViewById(R.id.app_name);
        TextView version = findViewById(R.id.version);
        TextView author = findViewById(R.id.author);

        appName.setText("云游戏PC");
        version.setText("v1.0.0");
        author.setText("作者: MEMZ-Chrome");

        // 点击版本号5次开启开发者模式
        version.setOnClickListener(v -> {
            versionClickCount++;
            if (versionClickCount >= 5 && !developerModeEnabled) {
                developerModeEnabled = true;
                Configuration.getConfiguration().setBooleanValue("developer_mode", true);
                // 可以显示一个Toast提示开发者模式已开启
            }
        });

        // 点击作者跳转B站
        author.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://space.bilibili.com/1353783215"));
            startActivity(intent);
        });
    }
}


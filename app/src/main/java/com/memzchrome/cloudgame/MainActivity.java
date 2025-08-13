package com.memzchrome.cloudgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.tencent.mmkv.MMKV;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // 初始化MMKV
        MMKV.initialize(this);
        Configuration.getConfiguration().preloadPageStartScripts(this);
        
        // 直接启动WebActivity
        String defaultUrl = Configuration.getConfiguration().getStringValue("default_game", "ys");
        String url;
        if ("sr".equals(defaultUrl)) {
            url = "https://sr.mihoyo.com/cloud/";
        } else {
            url = "https://ys.mihoyo.com/cloud/";
        }
        
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
        finish();
    }
}


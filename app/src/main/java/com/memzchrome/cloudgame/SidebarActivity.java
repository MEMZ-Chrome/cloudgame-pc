package com.memzchrome.cloudgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SidebarActivity extends Activity {
    
    private LinearLayout customUrlsLayout;
    private List<String> customUrls;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        mainLayout.setPadding(20, 20, 20, 20);
        
        // 默认游戏选项
        Button ysButton = new Button(this);
        ysButton.setText("云原神");
        ysButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", "https://ys.mihoyo.com/cloud/");
            startActivity(intent);
            finish();
        });
        
        Button srButton = new Button(this);
        srButton.setText("云星铁");
        srButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra("url", "https://sr.mihoyo.com/cloud/");
            startActivity(intent);
            finish();
        });
        
        // 自定义URL区域
        TextView customLabel = new TextView(this);
        customLabel.setText("自定义");
        customLabel.setTextSize(18);
        
        customUrlsLayout = new LinearLayout(this);
        customUrlsLayout.setOrientation(LinearLayout.VERTICAL);
        
        Button addCustomButton = new Button(this);
        addCustomButton.setText("添加自定义");
        addCustomButton.setOnClickListener(v -> {
            // TODO: 实现添加自定义URL对话框
        });
        
        // 底部按钮
        Button exitButton = new Button(this);
        exitButton.setText("退出应用");
        exitButton.setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
        
        Button aboutButton = new Button(this);
        aboutButton.setText("关于");
        aboutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.putExtra("page", "about");
            startActivity(intent);
        });
        
        Button settingsButton = new Button(this);
        settingsButton.setText("设置");
        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ContentActivity.class);
            intent.putExtra("page", "settings");
            startActivity(intent);
        });
        
        mainLayout.addView(ysButton);
        mainLayout.addView(srButton);
        mainLayout.addView(customLabel);
        mainLayout.addView(customUrlsLayout);
        mainLayout.addView(addCustomButton);
        mainLayout.addView(exitButton);
        mainLayout.addView(aboutButton);
        mainLayout.addView(settingsButton);
        
        setContentView(mainLayout);
    }
}


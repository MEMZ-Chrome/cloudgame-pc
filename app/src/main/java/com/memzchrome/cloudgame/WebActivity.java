package com.memzchrome.cloudgame;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Choreographer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;
import android.window.OnBackInvokedDispatcher;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Locale;

import com.memzchrome.cloudgame.webview.GameView;
import com.memzchrome.cloudgame.webview.IWebPageCallback;
import android.view.MenuItem;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity implements IWebPageCallback {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    public static final String START_SCRIPT = "page_start_scripts";
    public static final String LOADED_SCRIPT = "page_load_scripts";
    public static final String URL = "url";
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // 禁用返回键
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT,
                () -> {
                    // 不执行任何操作，禁用返回键
                }
        );

        setupActivity();
        loadWeb();

        // 侧边栏按钮点击事件
        findViewById(R.id.btn_ys).setOnClickListener(v -> {
            loadUrl("https://ys.mihoyo.com/cloud/");
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        findViewById(R.id.btn_sr).setOnClickListener(v -> {
            loadUrl("https://sr.mihoyo.com/cloud/");
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        findViewById(R.id.btn_add_custom).setOnClickListener(v -> {
            // TODO: 实现添加自定义URL功能
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        findViewById(R.id.btn_exit).setOnClickListener(v -> {
            finishAffinity();
            System.exit(0);
        });
        findViewById(R.id.btn_about).setOnClickListener(v -> {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });
        findViewById(R.id.btn_settings).setOnClickListener(v -> {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        // TODO: 实现自定义URL的添加和记忆功能
    }

    private void enableImmersiveMode() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    private void setupActivity() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        window.setAttributes(layoutParams);
        enableImmersiveMode();
    }

    private void loadWeb() {
        GameView view = findViewById(R.id.mouse_view);
        if (view == null) {
            return;
        }
        webView = view;
        view.setWebCallback(this);
        view.setOnClickListener(l -> {
            view.requestPointerCapture();
        });
        String url = getIntent().getStringExtra("url");
        if (TextUtils.isEmpty(url)) {
            url = Configuration.getConfiguration().getStringValue(Configuration.LAUNCH_URL, Configuration.DEFAULT_URL);
        }
        loadUrl(url);
    }

    private void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.destroy();
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // 禁用返回键，不执行任何操作
            // super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 拦截ESC键，传递给网页处理而不是退出应用
        if (keyCode == KeyEvent.KEYCODE_ESCAPE) {
            // 将ESC键事件传递给WebView
            webView.evaluateJavascript("document.dispatchEvent(new KeyboardEvent('keydown', {key: 'Escape', keyCode: 27, which: 27}));", null);
            return true; // 消费事件，不让系统处理
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onWebPageLoadEnd(WebView webView, String url) {
        evalScripts(START_SCRIPT);
    }

    @Override
    public void onWebPageLoadStart(WebView webView, String url) {
        evalScripts(LOADED_SCRIPT);
    }

    private void evalScripts(String lifeCycleKey) {
        String scripts = getIntent().getStringExtra(lifeCycleKey);
        if (!TextUtils.isEmpty(scripts)) {
            Gson gson = new Gson();
            try {
                List<String> scriptList = gson.fromJson(scripts, new TypeToken<List<String>>(){}.getType());
                for (String script : scriptList) {
                    String scriptData = Configuration.getConfiguration().getScript(script);
                    if (!TextUtils.isEmpty(scriptData)) {
                        webView.evaluateJavascript(scriptData, null);
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }
}
package com.memzchrome.cloudgame;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // 鼠标灵敏度设置
        SeekBar mouseSpeedSeekBar = findViewById(R.id.mouse_speed_seekbar);
        TextView mouseSpeedValue = findViewById(R.id.mouse_speed_value);
        
        int currentMouseSpeed = Configuration.getConfiguration().getIntValue("mouse_speed", 50);
        mouseSpeedSeekBar.setProgress(currentMouseSpeed);
        mouseSpeedValue.setText(String.valueOf(currentMouseSpeed));
        
        mouseSpeedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mouseSpeedValue.setText(String.valueOf(progress));
                Configuration.getConfiguration().setIntValue("mouse_speed", progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // 清除浏览器数据
        Button clearDataButton = findViewById(R.id.clear_data_button);
        clearDataButton.setOnClickListener(v -> {
            // TODO: 实现清除浏览器数据功能
        });

        // 无派蒙模式
        CheckBox noPaimonCheckBox = findViewById(R.id.no_paimon_checkbox);
        boolean noPaimonMode = Configuration.getConfiguration().getBooleanValue("no_paimon_mode", false);
        noPaimonCheckBox.setChecked(noPaimonMode);
        noPaimonCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Configuration.getConfiguration().setBooleanValue("no_paimon_mode", isChecked);
        });

        // 开发者模式选项
        boolean developerMode = Configuration.getConfiguration().getBooleanValue("developer_mode", false);
        if (developerMode) {
            findViewById(R.id.developer_section).setVisibility(android.view.View.VISIBLE);
            
            // UA设置
            Button uaButton = findViewById(R.id.ua_button);
            uaButton.setOnClickListener(v -> {
                // TODO: 实现UA设置功能
            });

            // 注入控制台
            CheckBox injectConsoleCheckBox = findViewById(R.id.inject_console_checkbox);
            boolean injectConsole = Configuration.getConfiguration().getBooleanValue("inject_console", false);
            injectConsoleCheckBox.setChecked(injectConsole);
            injectConsoleCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                Configuration.getConfiguration().setBooleanValue("inject_console", isChecked);
            });
        }
    }
}


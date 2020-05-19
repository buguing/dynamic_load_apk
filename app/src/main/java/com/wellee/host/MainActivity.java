package com.wellee.host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wellee.core.PluginManager;
import com.wellee.core.ProxyActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private File pluginFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance().init(this);
        pluginFile = getExternalFilesDir("plugin");
        if (!pluginFile.exists()) {
            pluginFile.mkdirs();
        }
    }

    public void loadApk(View view) {
        File filesDir = new File(pluginFile, "plugin.apk");
        String path = filesDir.getAbsolutePath();
        PluginManager.getInstance().loadApk(path);
    }

    public void jumpPlugin(View view) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", "com.wellee.plugin.PluginActivity");
        startActivity(intent);
    }
}

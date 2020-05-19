package com.wellee.core;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @author : liwei
 * 创建日期 : 2019/12/4 16:11
 * 邮   箱 : liwei@worken.cn
 * 功能描述 : 管理插件的生命周期
 */
public class ProxyActivity extends Activity {

    private String mClassName;
    private PluginApk mPluginApk;
    private IPluginLifecycle mIPluginLifecycle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getPluginApk();
        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null) {
            throw new RuntimeException("请先加载插件apk");
        }
        try {
            Class<?> clazz = mPluginApk.getDexClassLoader().loadClass(mClassName);
            Object obj = clazz.newInstance();
            if (obj instanceof IPluginLifecycle) {
                mIPluginLifecycle = (IPluginLifecycle) obj;
                mIPluginLifecycle.onAttach(this);
                Bundle bundle = new Bundle();
                bundle.putInt("FROM", IPluginLifecycle.FROM_EXTERNAL);
                mIPluginLifecycle.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.getResources() : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.getAssetManager() : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.getDexClassLoader() : super.getClassLoader();
    }
}

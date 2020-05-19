package com.wellee.core;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author : liwei
 * 创建日期 : 2019/12/4 14:30
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class PluginManager {

    private Context mContext;
    private PluginApk mPluginApk;

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        return PluginManagerHolder.INSTANCE;
    }

    private static class PluginManagerHolder {
        private static PluginManager INSTANCE = new PluginManager();
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
    }

    public PluginApk getPluginApk() {
        return mPluginApk;
    }

    public void loadApk(String apkPath) {
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            return;
        }
        // 创建ClassLoader
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        // 创建AssetManager
        AssetManager assetManager = createAssetManager(apkPath);
        // 创建Resources
        Resources resources = createResources(assetManager);
        mPluginApk = new PluginApk(packageInfo, resources, classLoader);
    }

    private DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    private AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.setAccessible(true);
            method.invoke(assetManager, apkPath);
            return assetManager;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Resources createResources(AssetManager assetManager) {
        Resources resources = mContext.getResources();
        return new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());
    }

}

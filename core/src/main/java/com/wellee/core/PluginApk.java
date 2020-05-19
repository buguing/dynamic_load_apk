package com.wellee.core;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author : liwei
 * 创建日期 : 2019/12/4 14:26
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public class PluginApk {

    private PackageInfo packageInfo;
    private Resources resources;
    private AssetManager assetManager;
    private DexClassLoader dexClassLoader;

    public PluginApk(PackageInfo packageInfo, Resources resources, DexClassLoader dexClassLoader) {
        this.packageInfo = packageInfo;
        this.resources = resources;
        this.assetManager = resources.getAssets();
        this.dexClassLoader = dexClassLoader;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public Resources getResources() {
        return resources;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }
}

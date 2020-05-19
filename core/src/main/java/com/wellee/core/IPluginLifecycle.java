package com.wellee.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author : liwei
 * 创建日期 : 2019/12/4 15:52
 * 邮   箱 : liwei@worken.cn
 * 功能描述 :
 */
public interface IPluginLifecycle {

    // 内部跳转
    int FROM_INTERNAL = 0;
    // 外部跳转
    int FROM_EXTERNAL = 1;

    void onAttach(Activity activity);

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onActivityResult(int requestCode, int resultCode, Intent data);
}

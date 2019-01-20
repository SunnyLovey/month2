package com.bawei.demo.zhangjing20190120.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ValiousUtils {

    //判断有无网络
    public static boolean hasNetWork(Context context){

        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = manager.getActiveNetworkInfo();

        return activeNetworkInfo.isAvailable()&&activeNetworkInfo!=null;
    }

}

package com.bawei.demo.zhangjing20190120.broad;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.bawei.demo.zhangjing20190120.activity.DetailActivity;

public class MyBroadCast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1=new Intent(context, DetailActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra("pid",1+"");
        context.startActivity(intent1);
    }
}

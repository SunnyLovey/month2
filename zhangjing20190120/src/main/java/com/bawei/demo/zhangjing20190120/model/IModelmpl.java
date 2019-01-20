package com.bawei.demo.zhangjing20190120.model;

import com.bawei.demo.zhangjing20190120.utils.MCallBack;
import com.bawei.demo.zhangjing20190120.utils.RetrofitUtils;
import com.google.gson.Gson;

import java.util.Map;

public class IModelmpl implements IModel {
    @Override
    public void getData(String url, final Map<String, String> map, final Class clazz, final MCallBack mCallBack) {
        RetrofitUtils.getInstance().post(url, map, new RetrofitUtils.OkHttpCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    Gson gson=new Gson();
                    Object o = gson.fromJson(data, clazz);
                    if(mCallBack!=null){
                        mCallBack.onSuccessed(o);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    if(mCallBack!=null){
                        mCallBack.onFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String error) {
              if(mCallBack!=null){
                  mCallBack.onFailed(error);
              }
            }
        });
    }
}

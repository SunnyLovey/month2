package com.bawei.demo.zhangjing20190120.model;

import com.bawei.demo.zhangjing20190120.utils.MCallBack;

import java.util.Map;

public interface IModel {
    void getData(String url, Map<String,String> map, Class clazz, MCallBack mCallBack);
}

package com.bawei.demo.zhangjing20190120.utils;

public interface MCallBack<T> {
    void onSuccessed(T data);
    void onFailed(String error);
}

package com.bawei.demo.zhangjing20190120.view;

public interface IView<T> {
    void successData(T data);
    void failData(String error);
}

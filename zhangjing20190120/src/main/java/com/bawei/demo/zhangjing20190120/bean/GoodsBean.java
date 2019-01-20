package com.bawei.demo.zhangjing20190120.bean;

import com.bawei.demo.zhangjing20190120.greendao.DataBean;

import java.util.List;

public class GoodsBean {
    private String code;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }
}

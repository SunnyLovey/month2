package com.bawei.demo.zhangjing20190120.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class DataBean {
    @Id
    @Unique
    private long id;
    private String images;
    private String title;
    private double price;
    private int pid;
    @Generated(hash = 1756859286)
    public DataBean(long id, String images, String title, double price, int pid) {
        this.id = id;
        this.images = images;
        this.title = title;
        this.price = price;
        this.pid = pid;
    }
    @Generated(hash = 908697775)
    public DataBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getImages() {
        return this.images;
    }
    public void setImages(String images) {
        this.images = images;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public double getPrice() {
        return this.price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getPid() {
        return this.pid;
    }
    public void setPid(int pid) {
        this.pid = pid;
    }



    
}

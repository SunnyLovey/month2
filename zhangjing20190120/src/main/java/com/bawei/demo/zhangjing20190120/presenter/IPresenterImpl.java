package com.bawei.demo.zhangjing20190120.presenter;

import com.bawei.demo.zhangjing20190120.model.IModelmpl;
import com.bawei.demo.zhangjing20190120.utils.MCallBack;
import com.bawei.demo.zhangjing20190120.view.IView;

import java.util.Map;

public class IPresenterImpl implements IPresenter {
    private IView iView;
    private IModelmpl iModelmpl;

    public IPresenterImpl(IView iView) {
        this.iView = iView;
        iModelmpl=new IModelmpl();
    }

    @Override
    public void startRequestPost(String url, Map<String, String> map, Class clazz) {
        iModelmpl.getData(url, map, clazz, new MCallBack() {
            @Override
            public void onSuccessed(Object data) {
                iView.successData(data);
            }

            @Override
            public void onFailed(String error) {
                iView.failData(error);
            }
        });
    }
    //解除绑定
    public void detachView(){
        if(iModelmpl!=null){
            iModelmpl=null;
        }
        if(iView!=null){
            iView=null;
        }
    }
}

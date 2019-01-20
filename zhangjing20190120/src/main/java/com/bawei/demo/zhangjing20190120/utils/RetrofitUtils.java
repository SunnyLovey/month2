package com.bawei.demo.zhangjing20190120.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RetrofitUtils {
    private static RetrofitUtils instance;
    private OkHttpClient client;
    private final  String BASE_URL="http://www.zhaoapi.cn/product/";
    private BaseApis baseApis;
    //创建单例
    public static RetrofitUtils getInstance(){
        if(instance==null){
            synchronized (RetrofitUtils.class){
                instance=new RetrofitUtils();
            }
        }
        return instance;
    }
    //构造方法
    private RetrofitUtils(){
        //日志拦截
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor interceptor1 = interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client=new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor1)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000,TimeUnit.SECONDS)
                .connectTimeout(5000,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
         baseApis=retrofit.create(BaseApis.class);
    }
    public void post(String url, Map<String,String> map,OkHttpCallBack okHttpCallBack){
        if(map==null){
            map=new HashMap<>();
        }
        baseApis.post(url, map)
                 .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(okHttpCallBack));
    }

    private Observer getObserver(final OkHttpCallBack okHttpCallBack) {
        Observer observer=new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
              if(okHttpCallBack!=null){
                  okHttpCallBack.onFail(e.getMessage());
              }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                  try {
                      String string = responseBody.string();
                      if(okHttpCallBack!=null){
                          okHttpCallBack.onSuccess(string);
                      }
                  }catch (Exception e){
                      e.printStackTrace();
                      if(okHttpCallBack!=null){
                          okHttpCallBack.onFail(e.getMessage());
                      }
                  }
            }
        };
        return observer;
    }

    public OkHttpCallBack okHttpCallBack;
    public void setOnOkHttpCallBack(OkHttpCallBack callBack){
        this.okHttpCallBack=callBack;
    }
    //接口回调
    public interface OkHttpCallBack{
        void onSuccess(String data);
        void onFail(String error);
    }

}

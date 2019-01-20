package com.bawei.demo.zhangjing20190120.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.demo.zhangjing20190120.MainActivity;
import com.bawei.demo.zhangjing20190120.R;
import com.bawei.demo.zhangjing20190120.adapter.ViewPagerAdapter;
import com.bawei.demo.zhangjing20190120.bean.DetailBean;
import com.bawei.demo.zhangjing20190120.bean.GoodsBean;
import com.bawei.demo.zhangjing20190120.presenter.IPresenterImpl;
import com.bawei.demo.zhangjing20190120.utils.Apis;
import com.bawei.demo.zhangjing20190120.view.IView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
 * 商品详情展示
 * 20190120
 * zhangjing
 *
 * */

public class DetailActivity extends AppCompatActivity implements IView{
   private IPresenterImpl iPresenter;
   @BindView(R.id.image_pic)
    SimpleDraweeView simpleDraweeView;
   @BindView(R.id.text_detail_price)
    TextView textView_price;
    @BindView(R.id.text_detail_title)
    TextView textView_title;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.button_add)
    Button button;
    private int i;
    private ViewPagerAdapter adapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i++;
            viewPager.setCurrentItem(i);
            sendEmptyMessageDelayed(0,1000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //实例化
        iPresenter=new IPresenterImpl(this);
        //绑定
        ButterKnife.bind(this);
        //得到pid
        Intent intent = getIntent();
        String pid = intent.getStringExtra("pid");

        UMShareConfig config=new UMShareConfig();
         config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(DetailActivity.this).setShareConfig(config);


        //请求
        Map<String,String> map=new HashMap<>();
        map.put("pid",pid);
        iPresenter.startRequestPost(Apis.URL_DETAIL,map, DetailBean.class);
    }
    @OnClick({R.id.button_add})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.button_add:
               qqLogin();
                break;
        }
    }

    private void qqLogin() {
        UMShareAPI umShareAPI=UMShareAPI.get(DetailActivity.this);
        umShareAPI.getPlatformInfo(DetailActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {

            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                String profile_image_url = map.get("profile_image_url");
                simpleDraweeView.setImageURI(profile_image_url);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void successData(Object data) {
            DetailBean bean= (DetailBean) data;
          DetailBean.Data data1 = bean.getData();
          textView_title.setText(data1.getTitle());
          textView_price.setText("￥"+data1.getPrice()+".00");
        String images = data1.getImages();
        String[] split = images.split("\\|");
        adapter=new ViewPagerAdapter(split,this);
        viewPager.setAdapter(adapter);
         handler.removeCallbacksAndMessages(null);
        i = viewPager.getCurrentItem();
        handler.sendEmptyMessageDelayed(i,1000);

    }

    @Override
    public void failData(String error) {
        Toast.makeText(DetailActivity.this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        iPresenter.detachView();
        handler.removeCallbacksAndMessages(null);
    }
}

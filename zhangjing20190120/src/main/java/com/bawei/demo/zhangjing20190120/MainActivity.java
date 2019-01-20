package com.bawei.demo.zhangjing20190120;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bawei.demo.zhangjing20190120.activity.MapActivity;
import com.bawei.demo.zhangjing20190120.adapter.RecyclerAdapter;
import com.bawei.demo.zhangjing20190120.bean.GoodsBean;
import com.bawei.demo.zhangjing20190120.greendao.DaoMaster;
import com.bawei.demo.zhangjing20190120.greendao.DaoSession;
import com.bawei.demo.zhangjing20190120.greendao.DataBean;
import com.bawei.demo.zhangjing20190120.greendao.DataBeanDao;
import com.bawei.demo.zhangjing20190120.presenter.IPresenterImpl;
import com.bawei.demo.zhangjing20190120.utils.Apis;
import com.bawei.demo.zhangjing20190120.utils.ValiousUtils;
import com.bawei.demo.zhangjing20190120.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 商品展示，子条目的展示
* 20190120
* zhangjing
*
* */
public class MainActivity extends AppCompatActivity implements IView{
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private IPresenterImpl iPresenter;
    private RecyclerAdapter adapter;
    private DataBeanDao dataBeanDao;
    private List<DataBean> data1;
    @BindView(R.id.button_position)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //实例化
        iPresenter=new IPresenterImpl(this);

        //布局管理器
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);

         adapter=new RecyclerAdapter(this);
         recyclerView.setAdapter(adapter);

         //删除
        adapter.setOnLongCallBack(new RecyclerAdapter.LongCallBack() {
            @Override
            public void getPosition(int position) {
                adapter.delete(position);
            }
        });
         //初始化GreenDao
        initGreenDao();

         if(!ValiousUtils.hasNetWork(this)){
             Toast.makeText(MainActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
           List<DataBean> list = dataBeanDao.queryBuilder().list();
            adapter.setList(list);
         }else {
             Map<String,String> map=new HashMap<>();
             map.put("keywords","笔记本");
             map.put("page",1+"");
             iPresenter.startRequestPost(Apis.URL_GOODS,map, GoodsBean.class);
         }


    }
    @OnClick({R.id.button_position})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.button_position:
                Intent intent=new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }

 private void initGreenDao() {
        DaoMaster.DevOpenHelper helper=new DaoMaster.DevOpenHelper(this,"goods.db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        dataBeanDao = daoSession.getDataBeanDao();
    }
    //添加
  public void add(){
        for (int i=0;i<data1.size();i++){
            String images = data1.get(i).getImages();
            double price = data1.get(i).getPrice();
            String title = data1.get(i).getTitle();
            int pid = data1.get(i).getPid();
            DataBean dataBean=new DataBean(i,images,title,price,pid);
            dataBeanDao.insert(dataBean);
        }
    }
    @Override
    public void successData(Object data) {
             GoodsBean bean= (GoodsBean) data;
        data1 = bean.getData();
        adapter.setList(data1);
      add();
    }

    @Override
    public void failData(String error) {
        Toast.makeText(MainActivity.this,error,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除绑定
        iPresenter.detachView();
    }
}

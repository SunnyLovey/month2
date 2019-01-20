package com.bawei.demo.zhangjing20190120.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.demo.zhangjing20190120.R;
import com.bawei.demo.zhangjing20190120.activity.DetailActivity;
import com.bawei.demo.zhangjing20190120.bean.GoodsBean;
import com.bawei.demo.zhangjing20190120.greendao.DataBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<DataBean> list;
    private Context context;
    private ObjectAnimator animator;

    public RecyclerAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.goods_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
           viewHolder.textView_title.setText(list.get(i).getTitle());
           viewHolder.textView_price.setText("￥"+list.get(i).getPrice()+".00");
        String images = list.get(i).getImages();
        String[] split = images.split("\\|");
        viewHolder.simpleDraweeView.setImageURI(split[0]);
        //调转到详情页
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("pid",list.get(i).getPid()+"");
                context.startActivity(intent);
            }
        });
        //删除
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                animator = ObjectAnimator.ofFloat(v,"alpha",1.0f,0f);
                animator.setDuration(3000);
                if(longCallBack!=null){
                    longCallBack.getPosition(i);
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.simple_image)
        SimpleDraweeView simpleDraweeView;
        @BindView(R.id.text_title)
        TextView textView_title;
        @BindView(R.id.text_price)
        TextView textView_price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //删除的方法
    public void delete(final int position){
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                list.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    //接口回调
    public LongCallBack longCallBack;
    public void setOnLongCallBack(LongCallBack callBack){
        this.longCallBack=callBack;
    }
    public interface LongCallBack{
        void getPosition(int position);
    }
}

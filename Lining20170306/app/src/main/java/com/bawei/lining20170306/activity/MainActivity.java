package com.bawei.lining20170306.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bawei.lining20170306.R;
import com.bawei.lining20170306.bean.Good;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/*
*  lining
*  20170306
*  主界面
*
* */
public class MainActivity extends AppCompatActivity {
 Handler handler=new Handler(){
     @Override
     public void handleMessage(Message msg) {
         super.handleMessage(msg);
         int item = vp.getCurrentItem();
         vp.setCurrentItem(++item);
         handler.sendEmptyMessageDelayed(0,2000);
     }
 };
    public  String [] imgarr=new String[]{
            "http://www.babybuy100.com/Storage/master/banner/201701091457553775150.png",
            "http://www.babybuy100.com/Storage/master/banner/201701091458115744260.png",
            "http://www.babybuy100.com/Storage/master/banner/201701091459160124470.png",
            "http://www.babybuy100.com/Storage/master/banner/201701101907498554190.png",
            "http://www.babybuy100.com/Storage/master/banner/201701101914391626910.png",
            "http://www.babybuy100.com/Storage/master/banner/201701101911379135490.png"

    };
    private ViewPager vp;
    private LinearLayout lin;
    private ArrayList<ImageView> list;
    private List<Good.ResultBean.IndexProductsBean> indexProducts;
    private GridView lv;
    private TextView tv_ping;
    private List<Good.ResultBean.BrandsBean.ProductsBean> productslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找控件
        vp = (ViewPager) findViewById(R.id.vp);
        lin = (LinearLayout) findViewById(R.id.lin);
        lv = (GridView) findViewById(R.id.lv_gg);
        tv_ping = (TextView) findViewById(R.id.tv_ping);
        initviewpic();//无限轮播
        xutilss();//xutil解析
        tvview();
    }

    private void tvview() {
        tv_ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);

            }
        });
    }

    private void xutilss() {

        String path="http://www.babybuy100.com/API/getShopOverview.ashx";
        x.http().get(new RequestParams(path), new Callback.CommonCallback<String>() {



            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Good good = gson.fromJson(result, Good.class);
                Good.ResultBean result1 = good.getResult();
                indexProducts = result1.getIndexProducts();
                List<Good.ResultBean.BrandsBean> brands = result1.getBrands();
                for (int i = 0; i < brands.size(); i++) {
                    productslist = brands.get(i).getProducts();


                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Madapter madapter = new Madapter();
                lv.setAdapter(madapter);
            }
        });
    }

   class  Madapter extends BaseAdapter{
       @Override
       public int getCount() {
           return indexProducts.size();
       }

       @Override
       public Object getItem(int position) {
           return null;
       }

       @Override
       public long getItemId(int position) {
           return 0;
       }

       @Override
       public View getView(int position, View convertView, ViewGroup parent) {
           Viewholder viewholder;

          if(convertView==null){
              convertView=  View.inflate(MainActivity.this,R.layout.index,null);
               viewholder = new Viewholder();
              viewholder.tv_pric= (TextView) convertView.findViewById(R.id.tv_pric);
              viewholder.tv_nam= (TextView) convertView.findViewById(R.id.tv_nam);
              convertView.setTag(viewholder);
          }else{
              viewholder= (Viewholder) convertView.getTag();
          }
           viewholder.tv_pric.setText(indexProducts.get(position).getPrice());
           viewholder.tv_nam.setText(indexProducts.get(position).getName());
           return convertView;
       }
       class  Viewholder{

           public TextView tv_pric;
           public TextView tv_nam;
       }
   }





//轮播
    private void initviewpic() {

        vp.setCurrentItem(1000);
        initadapter();
        inityuan();
        initpon();
        handler.sendEmptyMessage(0);
    }

    private void initpon() {
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for (int i = 0; i < imgarr.length; i++) {
                    ImageView imageView = list.get(i);
                    if(i==position%imgarr.length){
                        imageView.setImageResource(R.drawable.ss);
                    }else{
                        imageView.setImageResource(R.drawable.s2);
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void inityuan() {
        list = new ArrayList<>();
        for (int i = 0; i < imgarr.length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            if(i==0){
                imageView.setImageResource(R.drawable.ss);
            }else{
                imageView.setImageResource(R.drawable.s2);
            }
            imageView.setLayoutParams(new ViewGroup.LayoutParams(20,20));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            list.add(imageView);
            lin.addView(imageView);
        }

    }

    private void initadapter() {
        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                  int pp=position%imgarr.length;
                ImageLoader.getInstance().displayImage(imgarr[pp],imageView);
                container.addView(imageView);
                return imageView;
            }
        });
    }
}

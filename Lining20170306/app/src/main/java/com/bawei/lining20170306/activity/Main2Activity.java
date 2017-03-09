package com.bawei.lining20170306.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bawei.lining20170306.R;
import com.bawei.lining20170306.bean.Good;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;
/*
lining
20170306
展示页面
*
* */
public class Main2Activity extends AppCompatActivity {
    private List<Good.ResultBean.BrandsBean.ProductsBean> productslist;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        lv = (ListView) findViewById(R.id.lv);

        xutilss();//页面展示
    }
    private void xutilss() {

        String path="http://www.babybuy100.com/API/getShopOverview.ashx";
        x.http().get(new RequestParams(path), new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Good good = gson.fromJson(result, Good.class);
                Good.ResultBean result1 = good.getResult();

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
                    lv.setAdapter(new Madapter());
            }
        });
}

    class  Madapter extends BaseAdapter {
        @Override
        public int getCount() {
            return productslist.size();
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
                convertView=  View.inflate(Main2Activity.this,R.layout.llv,null);
                viewholder = new Viewholder();
                viewholder.tv2_pric= (TextView) convertView.findViewById(R.id.tv2_pric);
                viewholder.tv2_nam= (TextView) convertView.findViewById(R.id.tv2_nam);
                convertView.setTag(viewholder);
            }else{
                viewholder= (Viewholder) convertView.getTag();
            }
            viewholder.tv2_pric.setText(productslist.get(position).getPrice());
            viewholder.tv2_nam.setText(productslist.get(position).getName());
            return convertView;
        }
        class  Viewholder{

            public TextView tv2_pric;
            public TextView tv2_nam;
        }
}}

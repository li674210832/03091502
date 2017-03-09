package com.example.lenovo.test2;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mylibrary.XListView;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements XListView.IXListViewListener{
  Handler handler =new Handler(){
      @Override
      public void handleMessage(Message msg) {
          super.handleMessage(msg);

      }
  };
    private XListView xlv;
   private List<Bean.T1348647909107Bean>listt=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xlv = (XListView)   findViewById(R.id.xlv);
        xlv.setPullLoadEnable(true);
        xlv.setPullRefreshEnable(true);
        xlv.setXListViewListener(this);
        intview();
//*****************
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.color.colorAccent);

        // 设置滑动菜单视图的宽度
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        // 设置渐入渐出效果的值
        menu.setFadeDegree(0.35f);

        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //为侧滑菜单设置布局
        menu.setMenu(R.layout.left_menu);



    }
















    //**********************************************
    private void intview() {
        String path="http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
        x.http().get(new RequestParams(path), new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Bean bean = gson.fromJson(result, Bean.class);
                List<Bean.T1348647909107Bean> list = bean.getT1348647909107();
                listt.addAll(list);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
             xlv.setAdapter(new Myadapte());
            }
        });

    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                     listt.clear();
                intview();
                 xlv.stopLoadMore();
                xlv.stopRefresh();
                xlv.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    @Override
    public void onLoadMore() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intview();
                xlv.stopLoadMore();
                xlv.stopRefresh();
                xlv.setRefreshTime("刚刚");
            }
        }, 2000);
    }

    //适配器
    class Myadapte extends BaseAdapter {
        final  int TYPE1=0;
        final  int TYPE2=1;
        @Override
        public int getCount() {
            return listt.size();
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

            ViewHolder viewHolder;
            int type = getItemViewType(position);

            if (convertView == null) {
                viewHolder = new ViewHolder();
                switch (type){
                    case TYPE1:
                        convertView = View.inflate(MainActivity.this, R.layout.lv, null);

                        viewHolder.tt = (TextView) convertView.findViewById(R.id.tv);
                        break;

                    case  TYPE2:
                        convertView = View.inflate(MainActivity.this, R.layout.lvvv, null);

                        viewHolder.ttvv = (TextView) convertView.findViewById(R.id.tv2);
                        viewHolder.imgg= (ImageView) convertView.findViewById(R.id.imgg);
                        break;


                }
                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            switch (type){
                case TYPE1:
                    viewHolder.tt.setText(listt.get(position).getTitle());
                    break;

                case  TYPE2:
                    viewHolder.ttvv.setText(listt.get(position).getSource());
                   // Picasso.with(MainActivity.this).load(listtt.get(position).getImgsrc()).into(viewHolder.imgg);
                    break;


            }


            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            if(position%2==0){
                return TYPE1;
            }else{
                return TYPE2;
            }


        }
    }

    class ViewHolder {

        public TextView tt;
        public TextView ttvv;
        public ImageView imgg;
    }
}

package rv.bawe.com.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Lenovo on 2017/3/17.
 * author ：李宁
 * 类注释：
 */

public class MyreaycleviewAdapter  extends RecyclerView.Adapter<MyreaycleviewAdapter.ViewHolderr>{
    private final Context context;
    private ArrayList<String> datas;

    public MyreaycleviewAdapter(Context context, ArrayList<String> datas) {
        this.context = context;
        this.datas = datas;
    }

    /**
     * 相当于于getView方法中创建View和ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolderr onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = View.inflate(context, R.layout.item_recyclerview, null);
        return new ViewHolderr(inflate);

    }
    /**相当于于getView绑定数据部分的代码
     * 数据和View绑定
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolderr holder, int position) {
        //根据位置得到对应的数据
        String ss = datas.get(position);
          holder.tv_title.setText(ss);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    //添加条目
    public  void addDate(int pisition,String date){
        datas.add(pisition,date);
        //刷新适配器
        notifyItemInserted(pisition);
    }
    //删除条目
    public  void removeDate(int position){
        datas.remove(position);
        //刷新适配器
        notifyItemRemoved(position);
    }


    class ViewHolderr extends RecyclerView.ViewHolder{

        private  ImageView iv_icon;
        private  TextView tv_title;

        public ViewHolderr(View itemView) {
            super(itemView);
            iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            //条目监听
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Toast.makeText(context, "itemView"+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    if(onitmeClickLinner!=null){
                       // onitmeClickLinner.onitmeClick(v,datas.get(getLayoutPosition()));
                        onitmeClickLinner.onitmeClick(v,getLayoutPosition());
                    }
                }
            });
            //图片监听
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "我是图片=="+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    /**
     * 点击RecyclerView某条的监听
     */
    public interface onitmeClickLinner{
        /**
         * 当RecyclerView某个被点击的时候回调
         * @param view 点击item的视图
         * @param data 点击得到的数据
         */
      //  public void onitmeClick(View view,String data);
        public void onitmeClick(View view,int data);
    }
    public onitmeClickLinner onitmeClickLinner;
    /**
     * 设置RecyclerView某个的监听
     *
     */
    public void setOnitmeClickLinner(MyreaycleviewAdapter.onitmeClickLinner onitmeClickLinner) {
        this.onitmeClickLinner = onitmeClickLinner;
    }
}

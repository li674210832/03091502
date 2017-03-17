package rv.bawe.com.recycleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btn_add;
    private Button btn_delete;
    private Button btn_list;
    private Button btn_grid;
    private Button btn_flow;
    private RecyclerView recyclerview;
    private TextView tv_title;
    private ArrayList<String> datas;
    private MyreaycleviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        initData();

        //设置RecyclerView的适配器
        adapter = new MyreaycleviewAdapter(MainActivity.this, datas);
        recyclerview.setAdapter(adapter);
        //添加RecyclerView的分割线
               //第一种
      // recyclerview.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));
        recyclerview.addItemDecoration(new DividerListItemDecoration(MainActivity.this,DividerListItemDecoration.VERTICAL_LIST));

        //LayoutManager
        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));

        //设置点击某条的监听
            adapter.setOnitmeClickLinner(new MyreaycleviewAdapter.onitmeClickLinner() {
                @Override
                public void onitmeClick(View view, int data) {
                    Toast.makeText(MainActivity.this, "data=="+data, Toast.LENGTH_SHORT).show();
                    datas.remove(data);
                    adapter.notifyItemRemoved(data);
                }
            });

        recyclerview.setItemAnimator(new DefaultItemAnimator());

    }

    private void initData() {

        datas = new ArrayList<>();
        //准备数据集合
        for (int i=0;i<20;i++){
            datas.add("Content_"+i);
        }
    }

    private void initView() {
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
       // tv_title = (TextView) findViewById(R.id.tv_title);
       // tv_title.setText("RecyclerView");

        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_flow.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
               switch (v.getId()){
                   case R.id.btn_add:
                     //  adapter.addData(0,"New_Content");
                       //recyclerview.scrollToPosition(0);
                       adapter.addDate(0,"yi");
                       recyclerview.scrollToPosition(0);
                       break;
                   case R.id.btn_delete:
                       adapter.removeDate(0);
                       break;
                   case R.id.btn_list:
                       //设置List类型效果
                      // recyclerview.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));
                        recyclerview.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));


                       break;
                   case R.id.btn_grid:
                       //设置Grid类型效果
                      // recyclerview.setLayoutManager(new GridLayoutManager(RecyclerViewActivity.this, 2, GridLayoutManager.VERTICAL, false));
//                recyclerview.scrollToPosition(99);
                       recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this,2,GridLayoutManager.VERTICAL,false));

                       break;
                   case R.id.btn_flow:
                       //设置瀑布流类型效果
                      // recyclerview.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
                       recyclerview.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));
                       break;
               }
    }
}

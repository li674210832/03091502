package lokpp.bawe.com.okhttp;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int GET = 1;
    private static final int POST = 2;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = MainActivity.class.getSimpleName();
    private Button btn_get_post;
    private TextView tv_result;
    private Button btn_get_okhttputils;
    private Button btn_downloadfile;
    private ProgressBar mProgressBar;
    private Button btn_uploadfile;
    private Button btn_image;
    private Button btn_image_list;
    private ImageView iv_icon;
    private String result;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case GET:
                    //获取数据
                    tv_result.setText(result);
                    break;
                case POST:
                    //获取数据
                    tv_result.setText(porest);
                    break;
            }
        }
    };
    public OkHttpClient client = new OkHttpClient();
    private String porest;
    private TextView tv_result22;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_get_post = (Button) findViewById(R.id.btn_get_post);
        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result22 = (TextView) findViewById(R.id.tv_result22);
        btn_get_okhttputils = (Button) findViewById(R.id.btn_get_okhttputils);
        btn_downloadfile = (Button) findViewById(R.id.btn_downloadfile);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        btn_uploadfile = (Button) findViewById(R.id.btn_uploadfile);
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        btn_image = (Button) findViewById(R.id.btn_image);
        btn_image_list = (Button) findViewById(R.id.btn_image_list);
        //设置点击事件
        btn_get_post.setOnClickListener(this);
        btn_get_okhttputils.setOnClickListener(this);
        btn_downloadfile.setOnClickListener(this);
        btn_uploadfile.setOnClickListener(this);
        btn_image.setOnClickListener(this);
        btn_image_list.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_post://使用原生的okhttp请求网络数据，get和post
                getDataFromPost();//点击事件
                // tv_result.setText("");
                break;
            case R.id.btn_get_okhttputils:
                // getDataGetByOkhttpUtils();
                getDataPostByOkhttpUtils();
                break;
            case R.id.btn_downloadfile://下载文件
                downloadFile();
                break;
        }


    }

    /**
     * 使用okhttp-utils的get请求网络文本数据
     */
    public void getDataGetByOkhttpUtils() {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .get()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {
        @Override
        public void onBefore(Request request, int id) {
            setTitle("loading...");
        }

        @Override
        public void onAfter(int id) {
            setTitle("Sample-okHttp");
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            e.printStackTrace();
            tv_result.setText("onError:" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {
            Log.e(TAG, "onResponse：complete");
            tv_result.setText("onResponse:" + response);

            switch (id) {
                case 100:
                    Toast.makeText(MainActivity.this, "http", Toast.LENGTH_SHORT).show();
                    break;
                case 101:
                    Toast.makeText(MainActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        public void inProgress(float progress, long total, int id) {
            Log.e(TAG, "inProgress:" + progress);
            mProgressBar.setProgress((int) (100 * progress));
        }
    }



    //okhttputilpost
    private void getDataPostByOkhttpUtils() {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
//        url="http://www.391k.com/api/xapi.ashx/info.json?key=bd_hyrzjjfb4modhj&size=10&page=1";
        url = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";
        OkHttpUtils
                .post()
                .url(url)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    private void getDataFromPost() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    // result = get("http://api.m.mtime.cn/PageSubArea/TrailerList.api");
                    porest = post("http://api.m.mtime.cn/PageSubArea/TrailerList.api", "");
                    handler.sendEmptyMessage(POST);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }


    /**
     * okhttp3的post请求
     */
    private String post(String url, String json) throws IOException {
        RequestBody requestBody = RequestBody.create(JSON, json);
        Request build = new Request.Builder().url(url)
                .post(requestBody).build();
        Response execute = client.newCall(build).execute();
        return execute.body().string();
    }

    private String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url).build();
        Response execute = client.newCall(request).execute();
        return execute.body().string();
    }
    //////////////////

    /**
     * 使用okhttp-utils下载大文件
     */
    public void downloadFile() {
        String url = "http://vfx.mtime.cn/Video/2016/07/24/mp4/160724055620533327_480.mp4";
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new FileCallBack(Environment.getExternalStorageDirectory().getAbsolutePath(), "okhttp-utils-test.mp4")//
                {

                    @Override
                    public void onBefore(Request request, int id) {
                    }

                    @Override
                    public void inProgress(float progress, long total, int id) {
                        mProgressBar.setProgress((int) (100 * progress));
                        tv_result22.setText((int) (100 * progress)+"%");
                        if((int) (100 * progress)==100){
                            Toast.makeText(MainActivity.this, "下载完成", Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                        }
                        Log.e(TAG, "inProgress :" + (int) (100 * progress));
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e(TAG, "onError :" + e.getMessage());
                    }

                    @Override
                    public void onResponse(File file, int id) {
                        Log.e(TAG, "onResponse :" + file.getAbsolutePath());
                    }
                });
    }


}

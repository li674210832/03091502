package com.example.zhiyuan.clearcache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private TextView tv_cache_size;
    private long totalSize;
    private File cacheDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_cache_size = (TextView) findViewById(R.id.tv_cache_size);


        //人为的写入缓存信息
        cacheDir = getCacheDir();


        writeCache(cacheDir);
        //显示当前缓存的大小
        try {
            String size = DataClearManager.getCacheSize(cacheDir);
            tv_cache_size.setText(size);
        } catch (Exception e) {
            e.printStackTrace();
        }
//         String size=  getCacheSize(cacheDir);


        //缓存的大小-Cache Files
        //SD 当前放置缓存信息的目录
        //所有的 都删除
        //除了sharedPreference不删除

        //获取当前文件夹下边所有文件的总大小

    }

    /**
     * 获取缓存目录下的文件大小
     *
     * @param cacheDir
     * @return
     */
    private String getCacheSize(File cacheDir) {
        //先置位
        totalSize = 0;

        getCacheSizeMethod(cacheDir);
        //字节大小转成字符串  222222     12KB
        return formartSize(totalSize);
    }

    private String formartSize(long totalSize) {
        //小于1K
        if (totalSize < 1024) {
            return totalSize + "字节";
        } else {
            if ((totalSize / 1024) < 1024) {
                //kb范围以内
                return totalSize / 1024 + "kb";
            }
        }
        return null;
    }

    /**
     * 递归判断
     *
     * @param cacheDir
     */
    private void getCacheSizeMethod(File cacheDir) {
        //获取当前所有的大小
        File[] files = cacheDir.listFiles();
        if (files != null) {
            for (File file : files) {
                //是文件夹
                if (file.isDirectory()) {
                    //继续获取
                    getCacheSizeMethod(file);
                } else {
                    //是文件---获取当前文件的大小
                    long length = file.length();
                    totalSize = totalSize + length;
                }
            }

        }
    }

    /**
     * 模拟存储缓存内容
     *
     * @param cacheDir
     */
    private void writeCache(File cacheDir) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(cacheDir, "aaaa.txt"));
            fileOutputStream.write("dhoadhlfajdlsjddddddddddddddddddakfsavoannvkawodqqqqqfnawdnfawnvvnkanvaovnjaonvalvnakdjfnajdvjjdjjdjdjdjdjdjdjdjdjdjdjakdnfaeo;aeo".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File file = new File(cacheDir, "uuuu");
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(file, "bbbb.txt"));
            fileOutputStream.write("dhoadhlfajdlsjddddddddddddddddddakfsavoannvkawodfnawdnfawnvvnkanvaovnjaonvalvnakdjfnajdvjjdjjdjdjdjdjdjdjdjdjdjdjakdnfaeo;aeo".getBytes());
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * 清除缓存
     * @param view
     */
    public void clearCache(View view) {
//        clearAllCache(cacheDir);
        //判断是否删光
//        String cacheSize = getCacheSize(cacheDir);
        //删除某个指定文件夹下的所有信息
        DataClearManager.deleteFolderFile(cacheDir.getAbsolutePath(), true);
        String size = null;
        try {
            size = DataClearManager.getCacheSize(cacheDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        tv_cache_size.setText(size);
    }
    private void clearAllCache(File cacheDir) {
        File[] files = cacheDir.listFiles();
        //uuuu列出所有  bbb.txt
        for (File file : files) {
            if (file.isDirectory()) {
                //如果是文件夹，还是得判断
                if (file.listFiles().length == 0) {
                    //空文件夹
                    file.delete();
                } else {
                    //继续递归删除文件
                    clearAllCache(file);
                }
            } else {
                file.delete();
            }
        }
        //删除uuu
        cacheDir.delete();
    }


}

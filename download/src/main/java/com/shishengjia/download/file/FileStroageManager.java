package com.shishengjia.download.file;

import android.content.Context;
import android.os.Environment;

import com.shishengjia.download.utils.Md5Utils;

import java.io.File;
import java.io.IOException;

/**
 * 文件管理类
 */

public class FileStroageManager {
    //使用单例模式
    private static final FileStroageManager fsManager = new FileStroageManager();
    private Context mContext;

    public static FileStroageManager getInstance(){
        return fsManager;
    }

    public FileStroageManager(){
    }

    public void init(Context context){
        mContext = context;
    }

    public File getFileByName(String url){
        File parent;
        //判断是否有外置存储卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            //有的话采用外置的缓存目录
            parent = mContext.getExternalCacheDir();
        }else{
            //否则采用系统默认的缓存目录
            parent = mContext.getCacheDir();
        }
        //对url进行加密
        String fileName = Md5Utils.generateCode(url);
        File file = new File(parent,fileName);
        //判断赶文件是否存在，否的话则新创建
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

}

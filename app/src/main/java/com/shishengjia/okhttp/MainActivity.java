package com.shishengjia.okhttp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.shishengjia.download.file.FileStroageManager;
import com.shishengjia.download.http.DownloadCallback;
import com.shishengjia.download.http.HttpManager;
import com.shishengjia.download.utils.Logger;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);

        HttpManager.getInstance().asyncRequest("http://img1.mm131.com/pic/2762/4.jpg",
                new DownloadCallback() {
                    @Override
                    public void success(File file) {
                        final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(bitmap);
                            }
                        });
                        Logger.debug("shi","success"+file.getAbsolutePath());
                    }

                    @Override
                    public void fail(int errorCode, String errorMessage) {
                        Logger.error("shi","fail"+errorCode+" "+errorMessage);
                    }

                    @Override
                    public void progress(int progress) {

                    }
                });

    }
}

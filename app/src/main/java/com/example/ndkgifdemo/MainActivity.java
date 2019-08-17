package com.example.ndkgifdemo;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ndkgifdemo.utils.GifHelper;
import com.example.ndkgifdemo.utils.PermissionUtility;

import java.io.File;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "demo.gif";
    private GifHelper gifHelper;
    private Bitmap bitmap;
    private int currentIndex = 0;
    private int maxIndex = 0;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            currentIndex ++;
            if (currentIndex>=maxIndex){
                currentIndex = 0;
            }
            long nextFrameTime = gifHelper.renderFrame(bitmap, currentIndex);
            imageView.setImageBitmap(bitmap);
            handler.sendEmptyMessageDelayed(1,nextFrameTime);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.iv_gif);
        getPermissions();
    }

    private void getPermissions() {
        PermissionUtility.getRxPermission(MainActivity.this)
                .request(Manifest.permission.READ_EXTERNAL_STORAGE) //申请所需权限
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {
                            gifHelper = new GifHelper(path);
                            int width = gifHelper.getWidth();
                            int height = gifHelper.getHeight();
                            maxIndex = gifHelper.getImageCount();
                            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                            long nextFrameTime = gifHelper.renderFrame(bitmap, currentIndex);
                            imageView.setImageBitmap(bitmap);

                            if (handler != null){
                                handler.sendEmptyMessageDelayed(1,nextFrameTime);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "请开启读写权限", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


}

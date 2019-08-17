package com.example.ndkgifdemo.utils;

import android.graphics.Bitmap;


public class GifHelper {
    static {
        System.loadLibrary("native-lib");
    }

    private volatile long gifInfo;

    public GifHelper(String path){
        // 加载gif文件
        gifInfo = openFile(path);
    }

    public synchronized int getWidth(){
        return getWidth_native(gifInfo);
    }

    public synchronized int getHeight(){
        return getHeight_native(gifInfo);
    }

    public synchronized int getImageCount(){
        return getImageCountNative(gifInfo);
    }

    public synchronized long renderFrame(Bitmap bitmap,int index){
        return renderFrameNative(gifInfo,bitmap,index);
    }

    public native long openFile(String path);
    public native int getWidth_native(long gifInfo);
    public native int getHeight_native(long gifInfo);
    public native int getImageCountNative(long gifInfo);
    public native long renderFrameNative(long gifInfo,Bitmap bitmap,int index);
}

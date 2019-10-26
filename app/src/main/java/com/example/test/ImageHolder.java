package com.example.test;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageHolder {
    public static ImageHolder holder = new ImageHolder();
    private Bitmap _bitmap = null;

    public void setBitmap(Bitmap bitmap){
        _bitmap = bitmap;
    }

    public Bitmap getBitmap(){
        if(_bitmap == null){
            Log.e("e", "no bitmap");
            return null;
        }
        return _bitmap;
    }
}

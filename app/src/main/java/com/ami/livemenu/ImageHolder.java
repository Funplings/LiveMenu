package com.ami.livemenu;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ImageHolder {
    public static ImageHolder holder = new ImageHolder();
    private List<Bitmap> _bitmaps = new ArrayList<Bitmap>();

    public void addBitmap(Bitmap bitmap){
        _bitmaps.add(bitmap);
    }

    public void clean(){
        for(int i = 0; i < _bitmaps.size(); i++){
            _bitmaps.get(i).recycle();
        }
        _bitmaps.clear();
    }
    public List<Bitmap> getBitmaps(){
        if(_bitmaps == null){
            Log.e("e", "no bitmap");
            return null;
        }
        return _bitmaps;
    }
}

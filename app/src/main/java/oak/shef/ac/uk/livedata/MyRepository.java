/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oak.shef.ac.uk.livedata.database.MyDAO;
import oak.shef.ac.uk.livedata.database.MyRoomDatabase;
import oak.shef.ac.uk.livedata.database.PicinfoData;


class MyRepository extends ViewModel {
    private final MyDAO mDBDao;


    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDBDao = db.myDao();
    }

    /**
     * it gets the data when changed in the db and returns it to the ViewModel
     * @return
     */
    public LiveData<PicinfoData> getNumberData() {
        return mDBDao.retrieveOnePicinfo();
    }

    /**
     * called by the UI to request the generation of a new random number
     */
    public void generateNewNumber() {
        Random r = new Random();
        int i1 = r.nextInt(10000 - 1) + 1;
        String title = "new title";
        String description = "new dec";

//        new insertAsyncTask(mDBDao).execute(new PicinfoData(i1, title, description, a));
    }

    private byte[] getPicture(Drawable drawable) {
        if(drawable == null) {
            return null;
        }
        BitmapDrawable bd = (BitmapDrawable) drawable;
        Bitmap bitmap = bd.getBitmap();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    public void onPhotosReturned(List<File> files){
        for (File file:files
             ) {
            String filePath =file.getPath();
            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            Random r = new Random();
            int i1 = r.nextInt(10000 - 1) + 1;
            String title = "new title";
            String description = "new dec";

            new insertAsyncTask(mDBDao).execute(new PicinfoData(i1, title, description, byteArray));
        }
    }



    private static class insertAsyncTask extends AsyncTask<PicinfoData, Void, Void> {
        private MyDAO mAsyncTaskDao;
        private LiveData<PicinfoData> numberData;

        insertAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PicinfoData... params) {
            mAsyncTaskDao.insert(params[0]);
            Log.i("MyRepository", "number generated: "+params[0].getNumber()+""+ params[0].getTitle() + " " +params[0].getDescription()+ " "+ params[0].getImage());
            // you may want to uncomment this to check if numbers have been inserted
            //            int ix=mAsyncTaskDao.howManyElements();
            //            Log.i("TAG", ix+"");
            return null;
        }
    }

//    public byte[] bitmabToBytes(Context context){
//        //将图片转化为位图
//
//        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.joe1);
//        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
//        //创建一个字节数组输出流,流的大小为size
//        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
//        try {
//            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            //将字节数组输出流转化为字节数组byte[]
//            byte[] imagedata = baos.toByteArray();
//            return imagedata;
//        }catch (Exception e){
//        }finally {
//            try {
//                bitmap.recycle();
//                baos.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return new byte[0];
//    }
}

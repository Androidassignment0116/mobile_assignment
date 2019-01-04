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
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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



    public LiveData<List<PicinfoData>> getall(){
        return mDBDao.getall();
    }



    public void onNewTitleReturned(String title, String datetime){

        new updateTitleAsyncTask(mDBDao).execute(title,datetime);
    }

    public void onNewDescriptionReturned(String description, String datetime){

        new updateDescriptionAsyncTask(mDBDao).execute(description,datetime);
    }

    public void getallimagesfromphone(List<PicinfoData> files){
        for (PicinfoData p:files){
            p.getPath();
        }

    }

    /**
     * MainActivity
     * 1. 拿到系统相册里的所有图片的地址
     * 2. for循环地址list
     * 每次创建一个picinfodata
     * 3. 每个地址，查询相应title信息
     * 4. 展示图片，有title显示title， 没有显示文件名
     * 5. 点击图片，传入相应title， description, latitude...
     *

     */

    public void updateorinsert(String p){
        String title= "default";
        String description= "default";
        String path= p;
        String datetime= "a";
        Float latitude= 40f;
        Float longitude= 20f;
        String latitude_Ref= "N";
        String longitude_Ref = "W";
        String Latitude = "N";
        String Longitude = "W";


        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                latitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                Longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                longitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);

            if((Latitude !=null)
                    && (latitude_Ref !=null)
                    && (Longitude != null)
                    && (longitude_Ref !=null)) {

                if (latitude_Ref.equals("N")) {
                    latitude = convertToDegree(Latitude);
                } else {
                    latitude = 0 - convertToDegree(Latitude);
                }

                if (longitude_Ref.equals("E")) {
                    longitude = convertToDegree(Longitude);
                } else {
                    longitude = 0 - convertToDegree(Longitude);
                }
            }
            if (datetime == null){
                datetime = "" + System.currentTimeMillis() / 1000;
            }
            new updateTitleAsyncTask(mDBDao).execute(title,datetime);
            new updateDescriptionAsyncTask(mDBDao).execute(description,datetime);

            new insertAsyncTask(mDBDao).execute(new PicinfoData(title, description, path, datetime, latitude, longitude)); }


    private Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    };



    private static class insertAsyncTask extends AsyncTask<PicinfoData, Void, Void> {
        private MyDAO mAsyncTaskDao;
        private LiveData<PicinfoData> numberData;

        insertAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PicinfoData... params) {
                if (!mAsyncTaskDao.checkexits(params[0].getDatetime())) {
                    mAsyncTaskDao.insert(params[0]);
                    Log.i("MyRepository", "number generated: " + "" + params[0].getTitle() + " " + params[0].getDescription() + " " + params[0].getDatetime()+params[0].getPath()+ " "+params[0].getLatitude()+" "+ params[0].getLongitude());
                }

            return null;
        }
    }

    private static class updateTitleAsyncTask extends AsyncTask<String,Void,Void>{
        private MyDAO mAsyncTaskDao;
        updateTitleAsyncTask(MyDAO dao){mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(String... strings) {
            if (mAsyncTaskDao.checkexits(strings[1]))
            {mAsyncTaskDao.updatetitle(strings[0],strings[1]);}
            return null;
        }
    }

    private static class updateDescriptionAsyncTask extends AsyncTask<String, Void, Void>{
        private MyDAO mAsyncTaskDao;
        updateDescriptionAsyncTask(MyDAO dao){mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(String... strings) {
            if (mAsyncTaskDao.checkexits(strings[1])){
            mAsyncTaskDao.updatedescription(strings[0],strings[1]);}
            return null;
        }
    }

}
//        for (File file:files
//                ) {
//                String filePath =file.getPath();
//                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                bitmap.recycle();
//                String time = "default";
//                String Latitude = "0";
//                String Longitude = "0";
//                String title = "default title";
//                String description = "default description";
//                String latitude_Ref = "N";
//                String longitude_Ref = "E";
//                Float latitude =0.0f, longitude=0.0f;
//                try {
//                ExifInterface exifInterface = new ExifInterface(filePath);
//                Latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
//                latitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
//                Longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
//                longitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
//                time = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
//                } catch (IOException e) {
//                e.printStackTrace();
//                }
//                if((Latitude !=null)
//                && (latitude_Ref !=null)
//                && (Longitude != null)
//                && (longitude_Ref !=null))
//                {
//
//                if(latitude_Ref.equals("N")){
//                latitude = convertToDegree(Latitude);
//                }
//                else{
//                latitude = 0 - convertToDegree(Latitude);
//                }
//
//                if(longitude_Ref.equals("E")){
//                longitude = convertToDegree(Longitude);
//                }
//                else{
//                longitude = 0 - convertToDegree(Longitude);
//                }
//
//                }
////            new insertAsyncTask(mDBDao).execute(new PicinfoData(title, description, byteArray, time, latitude, longitude));
//
//                }
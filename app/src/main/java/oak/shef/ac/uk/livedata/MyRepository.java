/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import oak.shef.ac.uk.livedata.database.MyDAO;
import oak.shef.ac.uk.livedata.database.MyRoomDatabase;
import oak.shef.ac.uk.livedata.database.PicinfoData;

class MyRepository extends ViewModel {
    private final MyDAO mDBDao;
    private List<ImageElement> myPictureList = new ArrayList<>();

    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDBDao = db.myDao();
        initData();
    }

    /**
     * it gets the data when changed in the db and returns it to the ViewModel
     * @return
     */
    public LiveData<PicinfoData> getPicinfoData() {
        return mDBDao.retrieveOnePicinfo();
    }

    /**
     * called by the UI to request the generation of a new random number
     */
//    public void generateNewNumber() {
//        Random r = new Random();
//        int i1 = r.nextInt(10000 - 1) + 1;
//        new insertAsyncTask(mDBDao).execute(new PicinfoData(i1));
//    }

    public void generateNewTitle(){
        String title = "new ttt";
        String description = "new description";
        new insertAsyncTask(mDBDao).execute(new PicinfoData(title,description));
    }

    public List<ImageElement> getMyPictureList() {
        return myPictureList;
    }

    public void initData() {
        myPictureList.add(new ImageElement(R.drawable.joe1));
        myPictureList.add(new ImageElement(R.drawable.joe2));
        myPictureList.add(new ImageElement(R.drawable.joe3));
        myPictureList.add(new ImageElement(R.drawable.joe1));
        myPictureList.add(new ImageElement(R.drawable.joe2));
        myPictureList.add(new ImageElement(R.drawable.joe3));
        myPictureList.add(new ImageElement(R.drawable.joe1));
        myPictureList.add(new ImageElement(R.drawable.joe2));
        myPictureList.add(new ImageElement(R.drawable.joe3));
        myPictureList.add(new ImageElement(R.drawable.joe1));
        myPictureList.add(new ImageElement(R.drawable.joe2));
        myPictureList.add(new ImageElement(R.drawable.joe3));
        myPictureList.add(new ImageElement(R.drawable.joe1));
        myPictureList.add(new ImageElement(R.drawable.joe2));
        myPictureList.add(new ImageElement(R.drawable.joe3));
    }

    private static class insertAsyncTask extends AsyncTask<PicinfoData, Void, Void> {
        private MyDAO mAsyncTaskDao;


        insertAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PicinfoData... params) {
            mAsyncTaskDao.insert(params[0]);
            Log.i("MyRepository", "number generated: "+params[0].getTitle()+"");
            // you may want to uncomment this to check if numbers have been inserted
            //            int ix=mAsyncTaskDao.howManyElements();
            //            Log.i("TAG", ix+"");
            return null;
        }
    }
}

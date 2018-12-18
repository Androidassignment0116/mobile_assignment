/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Random;

import oak.shef.ac.uk.livedata.database.MyDAO;
import oak.shef.ac.uk.livedata.database.MyRoomDatabase;
import oak.shef.ac.uk.livedata.database.NumberData;

class MyRepository extends ViewModel {

    private final MyDAO mDBDao;

    MutableLiveData<String> newString;
    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDBDao = db.myDao();
        newString= new MutableLiveData();
    }

    /**
     * it returns the value of the live data
     * @return
     */
    public LiveData<String> getStringToDisplay() {
        if (newString.getValue()=="")
            generateNewNumber();
        return newString;
    }




    /**
     * it gets the data when changed in the db and returns it to the ViewModel
     *@return
     */
    public LiveData<NumberData> getNumberData() {
        return mDBDao.retrieveOneNumber();
    }


    /**
     * called by the UI to request the generation of a new random number
     */
    public void generateNewNumber() {
        Random r = new Random();
        int i1 = r.nextInt(10000 - 1) + 1;
        String nv= i1+"";
        newString.setValue(nv);
    }
}

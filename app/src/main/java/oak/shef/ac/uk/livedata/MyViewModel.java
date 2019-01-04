/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import oak.shef.ac.uk.livedata.database.PicinfoData;

public class MyViewModel extends AndroidViewModel {
    private final MyRepository mRepository;



    LiveData<List<PicinfoData>> allData;



    public MyViewModel (Application application) {
        super(application);
        mRepository = new MyRepository(application);
        allData = mRepository.getall();

    }




    LiveData<List<PicinfoData>> getallData(){
        if(allData == null){
            allData = new MutableLiveData<List<PicinfoData>>();
        }
        return  allData;
    }


    /**
     * request by the UI to generate a new random number
     */
    public void updateorinsert(String p){
        mRepository.updateorinsert(p);
    }
    public void deletePic(PicinfoData picinfoData){
        mRepository.deletePicData(picinfoData);
    }

    public void showallpic(List<PicinfoData> files){
        mRepository.getallimagesfromphone(files);
    }
    public void updatenewTitle(String title, String datetime)
    {
        mRepository.onNewTitleReturned(title,datetime);
    }

    public void updatenewDescription(String description, String datetime){
        mRepository.onNewDescriptionReturned(description,datetime);
    }


}

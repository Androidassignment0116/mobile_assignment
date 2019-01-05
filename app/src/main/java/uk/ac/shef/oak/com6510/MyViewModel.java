/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package uk.ac.shef.oak.com6510;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.ac.shef.oak.com6510.database.PicinfoData;

public class MyViewModel extends AndroidViewModel {
    private final MyRepository mRepository;



    LiveData<List<PicinfoData>> allData;
    List<PicinfoData> searchResults;



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

    public LiveData<PicinfoData> getthePic(String path){
        return mRepository.getthepic(path);
    }

    public List<PicinfoData> searchtheimage(String maytitle, String maydescription, String maydatetime){
        List<PicinfoData> p = new ArrayList<>();

        try {
            p = mRepository.searchtheimage(maytitle, maydescription, maydatetime);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return p;
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

    public void insertwithcoordinate(String path, Location currentlocation){
        mRepository.insertwithcoordinate(path, currentlocation);
    }


}

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

    LiveData<PicinfoData> numberDataToDisplay;
    LiveData<List<byte[]>> picDataToDisplay;
    List<ImageElement> temp = new ArrayList<>();


    public MyViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MyRepository(application);
        numberDataToDisplay = mRepository.getNumberData();
        picDataToDisplay = mRepository.getallimage();

    }


    /**
     * getter for the live data
     * @return
     */
    LiveData<PicinfoData> getNumberDataToDisplay() {
        if (numberDataToDisplay == null) {
            numberDataToDisplay = new MutableLiveData<PicinfoData>();
        }
        return numberDataToDisplay;
    }

    LiveData<List<byte[]>> getPicDataToDisplay(){
        if (picDataToDisplay == null){
            picDataToDisplay = new MutableLiveData<List<byte[]>>();
        }
        return picDataToDisplay;
    }
//    List<ImageElement> getPicDataToDisplay(){
//        if(picDataToDisplay != null) {
//            for (byte[] b:picDataToDisplay
//                 ) {
//                Bitmap bitmap = BitmapFactory.decodeByteArray(b , 0, b .length);
//                int x = bitmap.getWidth();
//                int y = bitmap.getHeight();
//                int[] intArray = new int[x * y];
//                bitmap.getPixels(intArray, 0, x, 0, 0, x, y);
//                temp.add(bitmap);
//            }
//        }
//        else
//            return null;
//    }

    /**
     * request by the UI to generate a new random number
     */

    public void sortpic(List<File> files){
        mRepository.onPhotosReturned(files);
    }
}

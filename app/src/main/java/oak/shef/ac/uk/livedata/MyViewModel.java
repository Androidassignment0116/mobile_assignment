/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;

import oak.shef.ac.uk.livedata.database.PicinfoData;

public class MyViewModel extends AndroidViewModel {
    private final MyRepository mRepository;

    LiveData<PicinfoData> numberDataToDisplay;

    List<ImageElement> initPicinfo;

    public MyViewModel (Application application) {
        super(application);
        // creation and connection to the Repository
        mRepository = new MyRepository(application);
        numberDataToDisplay = mRepository.getPicinfoData();
        initPicinfo = mRepository.getMyPictureList();
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

    /**
     * request by the UI to generate a new random number
     */
    public void generateNewNumber() {
        mRepository.generateNewTitle();
    }
}

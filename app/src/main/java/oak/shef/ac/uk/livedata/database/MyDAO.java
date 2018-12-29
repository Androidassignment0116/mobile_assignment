/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface MyDAO {
    @Insert
    void insertAll(PicinfoData... picinfoData);

    @Insert
    void insert(PicinfoData picinfoData);

    @Delete
    void delete(PicinfoData picinfoData);

    // it selects a random element
    @Query("SELECT * FROM picinfo_database ORDER BY RANDOM() LIMIT 1")
    LiveData<PicinfoData> retrieveOnePicinfo();

    @Query("SELECT image FROM picinfo_database")
    LiveData<List<byte[]>> getallimage();

    @Delete
    void deleteAll(PicinfoData... picinfoData);

    @Query("SELECT COUNT(*) FROM picinfo_database")
    int howManyElements();
}

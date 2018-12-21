/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "picinfo_database")
public class PicinfoData {
    @PrimaryKey(autoGenerate = true)
    @android.support.annotation.NonNull
    private int id=0;
    private int number;
    private String title;
    private String description;


//    public PicinfoData(int number) {
//        this.number= number;
//    }
    public PicinfoData(String title, String description){
        this.description = description;
        this.title = title;
    }

    @android.support.annotation.NonNull
    public int getId() {
        return id;
    }
    public void setId(@android.support.annotation.NonNull int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

}

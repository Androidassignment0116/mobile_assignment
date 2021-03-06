

package uk.ac.shef.oak.com6510.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface MyDAO {


    @Insert
    void insert(PicinfoData picinfoData);

    @Delete
    void delete(PicinfoData picinfoData);



    @Query("SELECT * FROM picinfo_database")
    LiveData<List<PicinfoData>> getall();


    @Query("SELECT * FROM picinfo_database WHERE path = :path")
    LiveData<PicinfoData> getthepic(String path);


    @Query("SELECT * FROM picinfo_database WHERE title LIKE '%' || :maytitle || '%' AND description LIKE '%' || :maydescription || '%' AND datetime LIKE '%' || :maydatetime || '%'")
    List<PicinfoData> searchanimage(String maytitle, String maydescription, String maydatetime);


    @Query("SELECT * FROM picinfo_database WHERE datetime = :datetime")
    boolean checkexits(String datetime);


    @Query("UPDATE picinfo_database SET title = :newtitle WHERE datetime = :datetime")
    void updatetitle(String newtitle,String datetime);


    @Query("UPDATE picinfo_database SET description = :newdescription WHERE datetime = :datetime")
    void updatedescription(String newdescription,String datetime);


}



package uk.ac.shef.oak.com6510.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import java.util.List;

@Dao
public interface MyDAO {
/**
 * insert method
 * @param picinfoData
  * @return void
 * @author Gang Chen
 */

    @Insert
    void insert(PicinfoData picinfoData);
/**
 * delete method
 * @param picinfoData
  * @return void
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 13:32
 */
    @Delete
    void delete(PicinfoData picinfoData);


    /**
     * get all information from database
     * @return LiveData<List<PicinfoData>>
     */
    @Query("SELECT * FROM picinfo_database")
    LiveData<List<PicinfoData>> getall();

    /**
     * get a specific picture from database.
     * @param path
     * @author Gang Chen
     * @return LiveData<PicinfoData>
     */
    @Query("SELECT * FROM picinfo_database WHERE path = :path")
    LiveData<PicinfoData> getthepic(String path);

    /**
     * fuzz search for some picture contain below information
     * @param maytitle
	* @param maydescription
     *
     * @author Gang Chen
	* @param maydatetime
      * @return java.util.List<uk.ac.shef.oak.com6510.database.PicinfoData>
     */
    @Query("SELECT * FROM picinfo_database WHERE title LIKE '%' || :maytitle || '%' AND description LIKE '%' || :maydescription || '%' AND datetime LIKE '%' || :maydatetime || '%'")
    List<PicinfoData> searchanimage(String maytitle, String maydescription, String maydatetime);

    /**
     * check whether a specific picture exists by the picture datetime
     * @param datetime
      * @return boolean
     * @author Gang Chen
     */
    @Query("SELECT * FROM picinfo_database WHERE datetime = :datetime")
    boolean checkexits(String datetime);

    /**
     * update title for a picture
     * @param newtitle
	* @param datetime
      * @return void
     */
    @Query("UPDATE picinfo_database SET title = :newtitle WHERE datetime = :datetime")
    void updatetitle(String newtitle,String datetime);

   /**
    * update description for a  picture
    * @param newdescription
	* @param datetime
     * @return void
    * @author Gang Chen
    */
    @Query("UPDATE picinfo_database SET description = :newdescription WHERE datetime = :datetime")
    void updatedescription(String newdescription,String datetime);


}

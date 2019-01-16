

package uk.ac.shef.oak.com6510;


import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.location.Location;
import android.media.ExifInterface;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import uk.ac.shef.oak.com6510.database.MyDAO;
import uk.ac.shef.oak.com6510.database.MyRoomDatabase;
import uk.ac.shef.oak.com6510.database.PicinfoData;

/**
 * insert and update methods in repository.
 * @param
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:36
 * @return
 */
class MyRepository extends ViewModel {
    private final MyDAO mDBDao;


    public MyRepository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        mDBDao = db.myDao();
    }



    public LiveData<List<PicinfoData>> getall(){
        return mDBDao.getall();
    }



    public void onNewTitleReturned(String title, String datetime){

        new updateTitleAsyncTask(mDBDao).execute(title,datetime);
    }

    public void onNewDescriptionReturned(String description, String datetime){

        new updateDescriptionAsyncTask(mDBDao).execute(description,datetime);
    }



    public void deletePicData(PicinfoData picinfoData){
        new  deleteAsyncTask(mDBDao).execute(picinfoData);

    }
    public LiveData<PicinfoData> getthepic(String path){
       return mDBDao.getthepic(path);
    }
/**
 * if the picture was took by the camera than insert to database with current location.
 * @param p the path passed from Myview activity
	* @param currentLocation  passed from Myview activity

 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:37
 * @return void
 */
    public void insertwithcoordinate(String p, Location currentLocation){
        String title= "default";
        String description= "default";
        String path= p;
        String datetime= "a";
        Float latitude = Float.valueOf((float) currentLocation.getLatitude());
        Float longitude = Float.valueOf((float) currentLocation.getLongitude());
        long time = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date resultdate = new Date(time);
        datetime = sdf.format(resultdate);

        new insertAsyncTask(mDBDao).execute(new PicinfoData(title,description,path,datetime,latitude,longitude));
    }
/**
 * if the picture was picked from gallery, then check if it already exits in database. If not exits, insert it. If it exits, do update.
 * Get information of pictures by using ExifInterface.
 * @param p
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:43
 * @return void
 */
    public void updateorinsert(String p){
        String title= "default";
        String description= "default";
        String path= p;
        String datetime= "a";
        Float latitude= 40f;
        Float longitude= 20f;
        String latitude_Ref= "N";
        String longitude_Ref = "W";
        String Latitude = "N";
        String Longitude = "W";


        ExifInterface exifInterface = null;
        try {
            exifInterface = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Latitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                latitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                Longitude = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                longitude_Ref = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);



            if((Latitude !=null)
                    && (latitude_Ref !=null)
                    && (Longitude != null)
                    && (longitude_Ref !=null)) {

                if (latitude_Ref.equals("N")) {
                    latitude = convertToDegree(Latitude);
                } else {
                    latitude = 0 - convertToDegree(Latitude);
                }

                if (longitude_Ref.equals("E")) {
                    longitude = convertToDegree(Longitude);
                } else {
                    longitude = 0 - convertToDegree(Longitude);
                }
            }

//            datetime = MediaStore.Files.FileColumns.DATE_MODIFIED;
            Log.i("time","media time: " + datetime);
            if (datetime == null){
                long time = System.currentTimeMillis();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
                Date resultdate = new Date(time);
                datetime = sdf.format(resultdate);
            }
            new updateTitleAsyncTask(mDBDao).execute(title,datetime);
            new updateDescriptionAsyncTask(mDBDao).execute(description,datetime);

            new insertAsyncTask(mDBDao).execute(new PicinfoData(title, description, path, datetime, latitude, longitude)); }

            public List<PicinfoData> searchtheimage(String maytitle, String maydescription, String maydate) throws ExecutionException, InterruptedException {
               return new searchAsyncTask(mDBDao).execute(maytitle,maydescription,maydate).get();
            }


    private Float convertToDegree(String stringDMS){
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0/D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0/M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0/S1;

        result = new Float(FloatD + (FloatM/60) + (FloatS/3600));

        return result;


    };

/**
 * The insert Async Task. If the picture already exits do not insert.
 * @param
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:48
 * @return
 */

    private static class insertAsyncTask extends AsyncTask<PicinfoData, Void, Void> {
        private MyDAO mAsyncTaskDao;
        private LiveData<PicinfoData> numberData;

        insertAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final PicinfoData... params) {
                if (!mAsyncTaskDao.checkexits(params[0].getDatetime())) {
                    mAsyncTaskDao.insert(params[0]);
                    Log.i("MyRepository", "number generated: " + "" + params[0].getTitle() + " " + params[0].getDescription() + " " + params[0].getDatetime()+params[0].getPath()+ " "+params[0].getLatitude()+" "+ params[0].getLongitude());
                }

            return null;
        }
    }
/**
 * Update title async task. Do update only the picture exits.

 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:49
 * @return
 */
    private static class updateTitleAsyncTask extends AsyncTask<String,Void,Void>{
        private MyDAO mAsyncTaskDao;
        updateTitleAsyncTask(MyDAO dao){mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(String... strings) {
            if (mAsyncTaskDao.checkexits(strings[1]))
            {mAsyncTaskDao.updatetitle(strings[0],strings[1]);}
            return null;
        }
    }
    /**
     * Update description async task. Do update only the picture exits.

     * @author Gang Chen
     * @creed: assignment
     * @date 2019/1/16 14:49
     * @return
     */
    private static class updateDescriptionAsyncTask extends AsyncTask<String, Void, Void>{
        private MyDAO mAsyncTaskDao;
        updateDescriptionAsyncTask(MyDAO dao){mAsyncTaskDao = dao;}
        @Override
        protected Void doInBackground(String... strings) {
            if (mAsyncTaskDao.checkexits(strings[1])){
            mAsyncTaskDao.updatedescription(strings[0],strings[1]);}
            return null;
        }
    }
/**
 *
 * Delete async task

 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:50
 * @return
 */
    private static class deleteAsyncTask extends AsyncTask<PicinfoData, Void, Void>{

        private MyDAO mAsyncTaskDao;
        deleteAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(PicinfoData... picinfoData) {
            mAsyncTaskDao.delete(picinfoData[0]);
            return null;
        }
    }
/**
 *
 * search async task

 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:51
 * @return List<Picinfodata>
 */
    private static class searchAsyncTask extends AsyncTask<String, Void, List<PicinfoData>>{
        private MyDAO mAsyncTaskDao;
        searchAsyncTask(MyDAO dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected List<PicinfoData> doInBackground(String... strings) {
            List<PicinfoData> res = new ArrayList<>();
            res = mAsyncTaskDao.searchanimage(strings[0],strings[1],strings[2]);
            return res;
        }
    }
}
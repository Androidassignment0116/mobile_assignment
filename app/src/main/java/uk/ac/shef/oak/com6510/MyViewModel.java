

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
 * invoke updateorinsert method in repository.
 * @param p the path of the picture file.
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:53
 * @return void
 */
    public void updateorinsert(String p){
        mRepository.updateorinsert(p);
    }
    /**
     * Invoke deletPicData method in repository.
     * @param picinfoData the target picture
     * @author Gang Chen
     * @creed: assignment
     * @date 2019/1/16 14:55
     * @return void
     */
    public void deletePic(PicinfoData picinfoData){
        mRepository.deletePicData(picinfoData);
    }
/**
 * Invoke getthepic method in repository
 * @param path passed from every Show Detail activity
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:56
 * @return android.arch.lifecycle.LiveData<uk.ac.shef.oak.com6510.database.PicinfoData>
 */
    public LiveData<PicinfoData> getthePic(String path){
        return mRepository.getthepic(path);
    }
/**
 * Invoke searchtheimage method in repository
 * @param maytitle fuzz title
	* @param maydescription fuzz description
	* @param maydatetime fuzz datetime

 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:57
 * @return java.util.List<uk.ac.shef.oak.com6510.database.PicinfoData>
 */
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
/**
 * Invoke onNewtitleReturned method in repository.
 * @param title passed from edit activity
	* @param datetime passed from edit activity
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 14:58
 * @return void
 */
    public void updatenewTitle(String title, String datetime)
    {
        mRepository.onNewTitleReturned(title,datetime);
    }
/**
 * Invoke onNewDescriptionReturned in repository
 * @param description passed form edit activity
	* @param datetime passed from edit activity
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 15:00
 * @return void
 */
    public void updatenewDescription(String description, String datetime){
        mRepository.onNewDescriptionReturned(description,datetime);
    }

    /**
     * Invoke inserwithcoordinate method in repository
     * @param path passed from MyView activity
	* @param currentlocation passed from MyView activity

     * @author Gang Chen
     * @creed: assignment
     * @date 2019/1/16 15:01
     * @return void
     */
    public void insertwithcoordinate(String path, Location currentlocation){
        mRepository.insertwithcoordinate(path, currentlocation);
    }


}

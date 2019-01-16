package uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
/**
 * check if the mobile phone has camera.
 * @param
 * @author Gang Chen
 * @creed: assignment
 * @date 2019/1/16 13:51
 * @return
 */
public class checkCamera {
    public static boolean checkCamera(Context context){
        PackageManager pm = context.getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                || Camera.getNumberOfCameras() > 0;
        return hasACamera;
    }


}


package oak.shef.ac.uk.cgxassignment;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import oak.shef.ac.uk.cgxassignment.database.PicinfoData;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.aprilapps.easyphotopicker.EasyImageConfiguration;

public class MyView extends AppCompatActivity {
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2987;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 7829;
    private MyViewModel myViewModel;
    private Activity activity;
    private PicAdapter PicAdapterview;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        setIconsVisible(menu,true);
        getMenuInflater().inflate(R.menu.menu_camera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.app_bar_search) {
            Intent intent = new Intent(this,Search.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.app_bar_camera) {
            EasyImage.openCamera(getActivity(), 0);
            return true;
        }

        if (id == R.id.app_bar_gallery) {
            EasyImage.openGallery(getActivity(), 0);
            return true;
        }

        if (id == R.id.app_bar_map) {
            Intent intent = new Intent(MyView.this,MapsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setIconsVisible(Menu menu, boolean flag) {

        if(menu != null) {
            try {

                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);

                method.setAccessible(true);

                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        RecyclerView recyclerView = findViewById(R.id.grid_recycler_view);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        PicAdapterview = new PicAdapter();
        recyclerView.setAdapter(PicAdapterview);



        activity= this;

        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        myViewModel.getallData().observe(this, new Observer<List<PicinfoData>>() {
            @Override
            public void onChanged(@Nullable List<PicinfoData> picinfoData) {


                PicAdapterview.setBitmaps(picinfoData);
            }
        });





        checkPermissions(getApplicationContext());

        initEasyImage();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openCamera(getActivity(), 0);

            }
        });
//
//        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
//        fabGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EasyImage.openGallery(getActivity(), 0);
//            }
//        });
//
//        FloatingActionButton fabmap = (FloatingActionButton) findViewById(R.id.fab_map);
//        fabmap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MyView.this,MapsActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

//        PackageManager.Per
    }

    private void initEasyImage() {
        EasyImageConfiguration c = EasyImage.configuration(this)
                .setImagesFolderName("EasyImageSample")
                .setCopyTakenPhotosToPublicGalleryAppFolder(true)
                .setCopyPickedImagesToPublicGalleryAppFolder(true)
                .setAllowMultiplePickInGallery(true);

        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

//        System.out.println(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES,
    }

    private void checkPermissions(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
                }

            }
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("Writing external storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                        }
                    });
                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
                }

            }


        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {

            @Override
            public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                //Some error handling
                e.printStackTrace();
            }

            @Override
            public void onImagesPicked(List<File> imageFiles, EasyImage.ImageSource source, int type) {
//                if (source == EasyImage.ImageSource.CAMERA)
                    for (File f : imageFiles) {

                        String p = f.getPath();
                        myViewModel.updateorinsert(p);
                    }



            }

            @Override
            public void onCanceled(EasyImage.ImageSource source, int type) {
                //Cancel handling, you might wanna remove taken photo if it was canceled
                if (source == EasyImage.ImageSource.CAMERA) {
                    File photoFile = EasyImage.lastlyTakenButCanceledPhoto(getActivity());
                    if (photoFile != null) photoFile.delete();
                }
            }
        });
    }

    public Activity getActivity() {
        return activity;
    }



    public  void setExif (String filepath,String longitude,String latitude,String time){

         ExifInterface exif = null;
        try{
            exif = new ExifInterface(filepath);
        }catch (IOException ex){
            Log.e("Mine","cannot read exif",ex);
        }
        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,longitude);
        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude);
        exif.setAttribute(ExifInterface.TAG_DATETIME,time);
        exif.setAttribute(ExifInterface.TAG_MAKE,longitude);
        exif.setAttribute(ExifInterface.TAG_MODEL,latitude);
        try{
            exif.saveAttributes();         //最后保存起来
        }catch (IOException e){
            Log.e("Mine","cannot save exif",e);
        }
    }



}


/*
 * Copyright (c) 2018. This code has been developed by Fabio Ciravegna, The University of Sheffield. All rights reserved. No part of this code can be used without the explicit written permission by the author
 */

package oak.shef.ac.uk.livedata;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import oak.shef.ac.uk.livedata.database.PicinfoData;
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
//                List<PicinfoData> newList = getallimagesFromPhone();
//                newList.addAll(picinfoData);
//                if (picinfoData.size()==0)
//                {
//                    PicAdapterview.setBitmaps(getallimagesFromPhone());
//                }
//                else

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

        FloatingActionButton fabGallery = (FloatingActionButton) findViewById(R.id.fab_gallery);
        fabGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EasyImage.openGallery(getActivity(), 0);
            }
        });

        FloatingActionButton fabmap = (FloatingActionButton) findViewById(R.id.fab_map);
        fabmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyView.this,MapsActivity.class);
                startActivity(intent);
            }
        });

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

//                myViewModel.sortpic(imageFiles);
                for(File f: imageFiles){
                    String p = f.getPath();
//                    String title = f.getName();
//                    String description = f.getName();
//                    String time = "" + System.currentTimeMillis() / 1000;
//                    Float latitude = 40f;
//                    Float longitude = 3f;
//                    List info = new ArrayList();
//                    info.add(title);
//                    info.add(description);
//                    info.add(p);
//                    info.add(time);
//                    info.add(latitude);
//                    info.add(longitude);
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

//private ArrayList<PicinfoData> getallimagesFromPhone() {
//    ArrayList<PicinfoData> files = new ArrayList<>();
//    Uri uri = MediaStore.Images.Media.getContentUri("external");
//    if (uri != null) {
//        String[] projection = new String[]{MediaStore.Files.FileColumns._ID, // id
//                MediaStore.Files.FileColumns.DATA, // 文件路径
//                MediaStore.Files.FileColumns.SIZE, // 文件大小
//                MediaStore.Files.FileColumns.DATE_MODIFIED}; // 修改日期
//        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//        if (cursor != null) {
//            try {
//                if (cursor.moveToFirst()) {
//                    final int pathIdx = cursor
//                            .getColumnIndex(MediaStore.Files.FileColumns.DATA);
//                    do {
//                        String path = cursor.getString(pathIdx);
//
//                        System.out.println(path);
//                        File f = new File("");
//                        PicinfoData file = new PicinfoData(MediaStore.Files.FileColumns.TITLE,"default description", path, "0",40.0f,3.0f);
//
//                        files.add(file);
//                    } while (cursor.moveToNext());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                cursor.close();
//            }
//        }
//    }
//    return files;
//}

    public  void setExif (String filepath,String longitude,String latitude,String time){

         ExifInterface exif = null;
        try{
            exif = new ExifInterface(filepath);     //根据图片的路径获取图片的Exif
        }catch (IOException ex){
            Log.e("Mine","cannot read exif",ex);
        }
        exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE,longitude);    //把经度写进exif
        exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latitude);     //把纬度写进exif
        exif.setAttribute(ExifInterface.TAG_DATETIME,time);              //把时间写进exif
        exif.setAttribute(ExifInterface.TAG_MAKE,longitude);             //把经度写进MAKE 设备的制造商，当然这样也是可以的，大家都是Stirng类型
        exif.setAttribute(ExifInterface.TAG_MODEL,latitude);             //把纬度写进MODEL
        try{
            exif.saveAttributes();         //最后保存起来
        }catch (IOException e){
            Log.e("Mine","cannot save exif",e);
        }
    }



}


package uk.ac.shef.oak.com6510;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import uk.ac.shef.oak.com6510.database.PicinfoData;

public class MapMarkerDetail extends AppCompatActivity {
    private MyViewModel myViewModeldetail;
    private byte[] byteArray;
    TextView textTittle;
    TextView textDescription;
    TextView textDate;
    MyImageView imageViewmap;
    ImageView imageView;
    Button mButtonEdit;
    String Latitude = "0";
    String Longitude = "0";
    String latitude_Ref = "N";
    String longitude_Ref = "E";
    Float latitude =0.0f, longitude=0.0f;
    String time ;
    Button mButtonDelete;
    FloatingActionButton mButtonMenu;
    private LinearLayout menu;
    private LinearLayout main;

    private Animation outAnimation;
    private Animation leftInAnimation;
    private Animation menuInAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myViewModeldetail = ViewModelProviders.of(this).get(MyViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_with_tool_bar);

        Bundle bundle = getIntent().getExtras();
        int pos = -1;
        if (bundle != null) {
            pos = bundle.getInt("position");
            if (pos != -1) {
                imageView = findViewById(R.id.image);
                textTittle = findViewById(R.id.title);
                imageViewmap = findViewById(R.id.map);
                textDescription = findViewById(R.id.description);
                textDate = findViewById(R.id.date);
                mButtonEdit = findViewById(R.id.edit);
                mButtonDelete = findViewById(R.id.delete);
                mButtonMenu = findViewById(R.id.fab_menu);
                menu = findViewById(R.id.menu);
                main = findViewById(R.id.main);
                outAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_and_left_out);
                leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
                menuInAnimation = AnimationUtils.loadAnimation(this, R.anim.menu_in);


                final String temp = PicAdapter.getItems().get(pos).getPath();
                Bitmap b = BitmapFactory.decodeFile(temp);
                if (b != null) {
                    imageView.setImageBitmap(b);
                    textTittle.setText(PicAdapter.getItems().get(pos).getTitle());
                    textDescription.setText(PicAdapter.getItems().get(pos).getDescription());
                    time = PicAdapter.getItems().get(pos).getDatetime();
                    longitude = PicAdapter.getItems().get(pos).getLongitude();
                    latitude = PicAdapter.getItems().get(pos).getLatitude();

                    textDate.setText(time);
                }

                myViewModeldetail.getthePic(temp).observe(this, new Observer<PicinfoData>() {

                    @Override
                    public void onChanged(@Nullable PicinfoData picinfoData) {
                        if (picinfoData != null){
                            textTittle.setText(picinfoData.getTitle());
                            textDescription.setText(picinfoData.getDescription());
                            textDate.setText(picinfoData.getDatetime());}
                    }
                });




                mButtonMenu.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (menu.getVisibility() == View.VISIBLE) {
                            main.startAnimation(leftInAnimation);
                            menu.startAnimation(menuInAnimation);
                            menu.setVisibility(View.GONE);
                        } else {
                            main.startAnimation(outAnimation);
                            menu.startAnimation(outAnimation);
                            menu.setVisibility(View.VISIBLE);
                        }
                    }
                });
                mButtonMenu.performClick();

                mButtonEdit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapMarkerDetail.this, Edit.class);
                        intent.putExtra("Title", textTittle.getText().toString());
                        intent.putExtra("Description", textDescription.getText().toString());
                        intent.putExtra("datetime", time);
                        intent.putExtra("picpath", temp);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }
                });
                final int finalPos = pos;
                mButtonDelete.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MapMarkerDetail.this, MyView.class);
                        myViewModeldetail.deletePic(PicAdapter.getItems().get(finalPos));
                        startActivity(intent);
                    }
                });


                String path = "https://maps.googleapis.com/maps/api/staticmap?markers=" + latitude + "," + longitude + "&zoom=17&size=400x250&key=AIzaSyDyTz8n8hZG9xTLw6Ffgve6faqfdwZVDwQ";
                imageViewmap.setImageURL(path);
                Log.i("url", "URL: " + path);


            }
        }

    }




}


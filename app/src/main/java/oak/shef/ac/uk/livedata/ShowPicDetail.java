package oak.shef.ac.uk.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import oak.shef.ac.uk.livedata.database.PicinfoData;

public class ShowPicDetail extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myViewModeldetail = ViewModelProviders.of(this).get(MyViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pic);


        Bundle bundle = getIntent().getExtras();
        int pos = -1;
        if (bundle !=null){
            pos = bundle.getInt("position");
            if (pos!= -1){
                imageView = findViewById(R.id.image);
                textTittle = findViewById(R.id.title);
                imageViewmap = findViewById(R.id.map);
                textDescription = findViewById(R.id.description);
                textDate = findViewById(R.id.date);
                mButtonEdit = findViewById(R.id.edit);
                mButtonDelete = findViewById(R.id.delete);


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


                    mButtonEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShowPicDetail.this,Edit.class);
                            intent.putExtra("Title",textTittle.getText().toString());
                            intent.putExtra("Description",textDescription.getText().toString());
                            intent.putExtra("datetime", time);
                            intent.putExtra("picpath",temp);
                            intent.putExtra("latitude", latitude);
                            intent.putExtra("longitude",longitude);
                            startActivity(intent);
                        }
                    });
                    final int finalPos = pos;
                    mButtonDelete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ShowPicDetail.this, MyView.class);
                            myViewModeldetail.deletePic(PicAdapter.getItems().get(finalPos));
                            startActivity(intent);
                        }
                    });
                }

                String path = "https://maps.googleapis.com/maps/api/staticmap?markers="+latitude+","+longitude+"&zoom=17&size=400x250&key=AIzaSyDyTz8n8hZG9xTLw6Ffgve6faqfdwZVDwQ";
                imageViewmap.setImageURL(path);
                Log.i("url","URL: "+path);


            }
        }



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
}

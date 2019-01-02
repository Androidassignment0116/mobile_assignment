package oak.shef.ac.uk.livedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

import oak.shef.ac.uk.livedata.database.PicinfoData;

public class ShowPicDetail extends AppCompatActivity {
    private MyViewModel myViewModeldetail;
    private byte[] byteArray;
    TextView textTittle;
    TextView textDescription;
    TextView textDate;
    MyImageView imageViewmap;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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


                byte[] temp = PicAdapter.getItems().get(pos).getImage();
                Bitmap b = BitmapFactory.decodeByteArray(temp , 0, temp.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                if (b != null) {
                    imageView.setImageBitmap(b);
                    textTittle.setText("Title:" + PicAdapter.getItems().get(pos).getTitle());
                    textDescription.setText("Description: "+ PicAdapter.getItems().get(pos).getDescription());
                    textDate.setText("Date: " + PicAdapter.getItems().get(pos).getDatetime());
                }

                String path = "https://maps.googleapis.com/maps/api/staticmap?markers="+PicAdapter.getItems().get(pos).getLatitude()+","+PicAdapter.getItems().get(pos).getLongitude()+"&zoom=17&size=400x250&key=AIzaSyDyTz8n8hZG9xTLw6Ffgve6faqfdwZVDwQ";
                imageViewmap.setImageURL(path);
                Log.i("url","URL: "+path);


            }
        }



    }
}

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
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_pic);


        Bundle bundle = getIntent().getExtras();
        int pos = -1;
        if (bundle !=null){
            pos = bundle.getInt("position");
            if (pos!= -1){
                ImageView imageView = findViewById(R.id.image);
                TextView textView = findViewById(R.id.title);

                byte[] temp = PicAdapter.getItems().get(pos).getImage();
                Bitmap b = BitmapFactory.decodeByteArray(temp , 0, temp.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                if (b != null)
                    imageView.setImageBitmap(b);

                textView.setText("Title: "+ PicAdapter.getItems().get(pos).getTitle()+" Description: "+ PicAdapter.getItems().get(pos).getDescription()+ " latitude: "+ PicAdapter.getItems().get(pos).getLatitude() + " Longitude:"+ PicAdapter.getItems().get(pos).getLongitude());
            }
        }



    }
}

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
    TextView textLocation;


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
                TextView textTittle = findViewById(R.id.title);
                TextView textDescription = findViewById(R.id.description);
                TextView textLocation = findViewById(R.id.location);

                byte[] temp = PicAdapter.getItems().get(pos).getImage();
                Bitmap b = BitmapFactory.decodeByteArray(temp , 0, temp.length);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byteArray = stream.toByteArray();
                if (b != null)
                    imageView.setImageBitmap(b);

                textTittle.setText( PicAdapter.getItems().get(pos).getTitle());

                textDescription.setText( PicAdapter.getItems().get(pos).getDescription());
                textLocation.setText(" latitude: "+ PicAdapter.getItems().get(pos).getLatitude()
                        + " Longitude:"+ PicAdapter.getItems().get(pos).getLongitude());

            }
        }



    }
}

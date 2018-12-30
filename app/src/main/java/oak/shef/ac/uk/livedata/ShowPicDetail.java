package oak.shef.ac.uk.livedata;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ShowPicDetail extends AppCompatActivity {
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
                Bitmap b = PicAdapter.getItems().get(pos);
                if (b != null)
                    imageView.setImageBitmap(b);
            }
        }



    }
}

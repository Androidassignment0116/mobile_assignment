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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        byte[] b = bundle.getByteArray("Picture");
        Bitmap bitmap = BitmapFactory.decodeByteArray(b , 0, b.length);
        int x = bitmap.getWidth();
        int y = bitmap.getHeight();
        int[] intArray = new int[x * y];
        bitmap.getPixels(intArray, 0, x, 0, 0, x, y);

        ImageView imageView = findViewById(R.id.image);
        imageView.setImageBitmap(bitmap);

    }
}

package oak.shef.ac.uk.livedata;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {
    private List<Bitmap> bitmaps = new ArrayList<>();


    @NonNull
    @Override
    public PicAdapter.PicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        return new PicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PicAdapter.PicHolder picHolder, int i) {
        Bitmap currentPic = bitmaps.get(i);
        picHolder.imageViewpic.setImageBitmap(currentPic);
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }
    public void setBitmaps(List<byte[]> pic)
    {
        for (byte[] b:pic
             ) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(b , 0, b.length);
            int x = bitmap.getWidth();
            int y = bitmap.getHeight();
            int[] intArray = new int[x * y];
            bitmap.getPixels(intArray, 0, x, 0, 0, x, y);
            bitmaps.add(bitmap);
        }
        notifyDataSetChanged();
    }

    class PicHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewpic;


        public PicHolder(@NonNull View itemView) {
            super(itemView);
            imageViewpic = itemView.findViewById(R.id.image_item);
        }
    }
}

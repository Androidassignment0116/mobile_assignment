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

import oak.shef.ac.uk.livedata.database.PicinfoData;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {
    private List<Bitmap> bitmaps = new ArrayList<>();
    private OnItemClickListener listener;

    public PicAdapter(){};

    public PicAdapter(List<Bitmap> b){  this.bitmaps = b;}

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
        List<Bitmap> bit = new ArrayList<>();
        for (byte[] b:pic
             ) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(b , 0, b.length);
            int x = bitmap.getWidth();
            int y = bitmap.getHeight();
            int[] intArray = new int[x * y];
            bitmap.getPixels(intArray, 0, x, 0, 0, x, y);
            bit.add(bitmap);
        }
        this.bitmaps = bit;
        notifyDataSetChanged();
    }

    class PicHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewpic;


        public PicHolder(@NonNull View itemView) {
            super(itemView);
            imageViewpic = itemView.findViewById(R.id.image_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(bitmaps.get(position));
                    }
                }
            });
        }
    }

    public  List<Bitmap> getItems() {
        return bitmaps;
    }
    public interface OnItemClickListener {
        void onItemClick(Bitmap node);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

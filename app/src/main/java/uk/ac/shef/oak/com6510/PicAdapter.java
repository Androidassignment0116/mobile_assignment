package uk.ac.shef.oak.com6510;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.database.PicinfoData;

public class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {
    private static List<Bitmap> bitmaps = new ArrayList<>();
    private static Context context;
    private static List<PicinfoData> allinfo = new ArrayList<>();


    public PicAdapter(){};

/**
 *
 * @param viewGroup
	* @param i the position of the picture in adapter.
 * @author Yiwei Xu
 * @creed: assignment
 * @date 2019/1/16 15:03
 * @return uk.ac.shef.oak.com6510.PicAdapter.PicHolder
 */
    @NonNull
    @Override
    public PicAdapter.PicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        context = viewGroup.getContext();
        return new PicHolder(v);
    }
/**
 * set item click listener on every picture and pass the position to showPicDetail activity.
 * @param picHolder
	* @param i the position of the picture in adapter
 * @author Yiwei Xu
 * @creed: assignment
 * @date 2019/1/16 15:04
 * @return void
 */
    @Override
    public void onBindViewHolder(@NonNull PicAdapter.PicHolder picHolder, final int i) {
        Bitmap currentPic = bitmaps.get(i);
        picHolder.imageViewpic.setImageBitmap(currentPic);
        picHolder.imageViewpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPicDetail.class);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

    /**
     * set picture in adapter
     * @param pic passed from MyView activity
     * @author Yiwei Xu
     * @creed: assignment
     * @date 2019/1/16 15:07
     * @return void
     */
    public void setBitmaps(List<PicinfoData> pic)
    {

        List<Bitmap> bit = new ArrayList<>();
        for (PicinfoData b:pic
                ) {
            Bitmap bitmap = BitmapFactory.decodeFile(b.getPath());

            bit.add(bitmap);
        }
        this.bitmaps = bit;
        this.allinfo = pic;
        notifyDataSetChanged();
    }

    class PicHolder extends RecyclerView.ViewHolder{
        private ImageView imageViewpic;


        public PicHolder(@NonNull View itemView) {
            super(itemView);
            imageViewpic = itemView.findViewById(R.id.image_item);

        }
    }

    /**
     *Return all picture information to every detail activity
     * @param
     * @author Yiwei Xu
     * @creed: assignment
     * @date 2019/1/16 15:08
     * @return java.util.List<uk.ac.shef.oak.com6510.database.PicinfoData>
     */
    public static List<PicinfoData> getItems() {
        return allinfo;
    }

}

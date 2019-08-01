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


public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.PicHolder> {
    private static List<Bitmap> bitmaps = new ArrayList<>();
    private static Context context;
    private static List<PicinfoData> allinfo = new ArrayList<>();


    public ResultAdapter(){};


    public ResultAdapter(List<Bitmap> b){  this.bitmaps = b;}

    public ResultAdapter(Context cont, List<Bitmap> b){
        this.context = cont;
        this.bitmaps = b;
    }

    @NonNull
    @Override
    public ResultAdapter.PicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_image, viewGroup, false);
        context = viewGroup.getContext();
        return new PicHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.PicHolder picHolder, final int i) {
        Bitmap currentPic = bitmaps.get(i);
        picHolder.imageViewpic.setImageBitmap(currentPic);
//
        picHolder.imageViewpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPicDetailSearch.class);
                intent.putExtra("position", i);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bitmaps.size();
    }

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

    public static List<PicinfoData> getItems() {
        return allinfo;
    }

}

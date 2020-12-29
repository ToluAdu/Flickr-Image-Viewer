package uk.ac.kent.ta480.imageviewer;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    public ArrayList<ImageInfo> imageList = new ArrayList<ImageInfo>();


public class ViewHolder extends RecyclerView.ViewHolder {

   public TextView imageTitle;

   //used to be mainImage
   public NetworkImageView mainImage2;


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(),DetailsActivity.class);


            int position = ViewHolder.this.getLayoutPosition();
            intent.putExtra("PHOTO_POSITION",position);
            context.startActivity(intent);

        }
    };

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageTitle = (TextView) itemView.findViewById(R.id.imageTitleDt);

         mainImage2 = (NetworkImageView) itemView.findViewById(R.id.mainImage);

        mainImage2.setOnClickListener(onClick);
    }
}

    private Context context;

    public ImageListAdapter(Context context) {
        super();
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // Return the design for a cell in your list
        View cellLayout = LayoutInflater.from(context)
                .inflate(R.layout.cell_image_card, viewGroup, false);
        // Create ViewHolder for that cell
        ViewHolder vh = new ViewHolder(cellLayout);
        return vh;




    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

   ImageInfo imgObject = imageList.get(i);
   viewHolder.imageTitle.setText(imgObject.title);


//****


if(imgObject.url_m != null)
{
    viewHolder.mainImage2.setImageUrl(imgObject.url_m, NetworkMgr.getInstance(context).imageLoader);

}

        else if(imgObject.url_l != null)
        {
            viewHolder.mainImage2.setImageUrl(imgObject.url_l, NetworkMgr.getInstance(context).imageLoader);

        }

        else if(imgObject.url_o != null)
        {
            viewHolder.mainImage2.setImageUrl(imgObject.url_o, NetworkMgr.getInstance(context).imageLoader);

        }








    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }
}

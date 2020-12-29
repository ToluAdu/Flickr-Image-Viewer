package uk.ac.kent.ta480.imageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class DetailsActivity extends AppCompatActivity {

    private ImageInfo photo;


    private ImageView image;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        // Get the intent
        Intent intent = getIntent();
        int photoPosition = intent.getIntExtra("PHOTO_POSITION", 0);
        // Retrieve the data object
        photo = NetworkMgr.getInstance(this).imageList.get(photoPosition);



        // text view object must be in onCreate
        TextView tv = new TextView(this);
        ImageView iv = new ImageView(this);


        //****
        TextView dtv = new TextView(this);






        tv = (TextView) findViewById(R.id.imageTitleDt);

        //***
        dtv = (TextView) findViewById(R.id.descriptionText);



        image = (ImageView)findViewById(R.id.mainImage);





        tv.setText(photo.title);

        //***
        dtv.setText(photo.description);







        NetworkMgr netMgr = NetworkMgr.getInstance(this);

        //problem rn is that the photo.url_l is empty

        if(photo.url_m != null)
        {
            netMgr.imageLoader.get(photo.url_m, imageListener);
        }
        //***
       else if (photo.url_l != null)
        {

            netMgr.imageLoader.get(photo.url_l, imageListener);

        }
        //***
        else if (photo.url_o != null)
        {

           netMgr.imageLoader.get(photo.url_o, imageListener);

        }



    }


    private ImageLoader.ImageListener imageListener = new ImageLoader.ImageListener() {
        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

            if(response.getBitmap() != null)
            {
                // pictures are not displaying , the photo url_l is empty as I was getting errors before making the if statement
                // which means imageLoader was getting empty urls which resulted in the program not working


             image.setImageBitmap(response.getBitmap());
            }

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };



















}

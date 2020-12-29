package uk.ac.kent.ta480.imageviewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NetworkMgr {

    //9858f2a9ba39ad9e8ae124356f21e9b8
// add more content, change display and favourites button

    //https://developer.android.com/training/swipe/add-swipe-interface

    private Context context;
    private static NetworkMgr instance;
    public RequestQueue requestQueue;
    ArrayList<ImageInfo> imageList;
    private RecyclerView.Adapter adapter;

    private NotifyResult notifyResult;

    public ImageLoader imageLoader;

    private ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache()
    {
        private final LruCache<String, Bitmap> cache = new LruCache<String, Bitmap>(20);

        @Override
        public Bitmap getBitmap(String url)
        {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap)
        {
           cache.put(url, bitmap);
        }



    };




    public NetworkMgr(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
        imageList = new ArrayList<ImageInfo>();

        imageLoader = new ImageLoader(requestQueue,imageCache);

    }

    public static NetworkMgr getInstance(Context context) {
        if (instance == null)
            instance = new NetworkMgr(context.getApplicationContext());
        return instance;
    }



    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            //exp1
            notifyResult.onNetworkResult(true);

        }
    };


    public void loadPhotos (NotifyResult notifier)
    {

        this.notifyResult = notifier;

        String url = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=9858f2a9ba39ad9e8ae124356f21e9b8&format=json&nojsoncallback=?&extras=description,owner_name,url_m,url_l,url_o,date_taken&per_page=50";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, responseListener, errorListener);
        requestQueue.add(request);
    }


 private Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
        @Override
     public void onResponse(JSONObject response)
        {

            try {

                JSONObject photos = response.getJSONObject("photos");
                JSONArray photolist = photos.getJSONArray("photo");

                for (int i=0; i < photolist.length(); i++)
                {
                 ImageInfo newImage = new ImageInfo();

                 JSONObject photo = photolist.getJSONObject(i);

                 newImage.id = photo.getString("id");
                    newImage.title = photo.getString("title");
                    newImage.owner = photo.getString("owner");



                          newImage.url_m = photo.getString("url_m");



                          newImage.url_l = photo.getString("url_l");




                   if(newImage.url_o != null)
                    {
                        newImage.url_o = photo.getString("url_o");
                    }













                    JSONObject descrObj = photo.getJSONObject("description");
                    newImage.description= descrObj.getString("_content");

                    imageList.add(newImage);
                }

            }
            catch (JSONException ex)
            {
                ex.printStackTrace();
                //exp1
                notifyResult.onNetworkResult(true);
            }




            // Notify we successfully got the photo list
            if(notifyResult != null)
                notifyResult.onNetworkResult(true);

        }







 };




    // Interface definition
    public interface NotifyResult {
        public void onNetworkResult(boolean success);
    }





}

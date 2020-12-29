package uk.ac.kent.ta480.imageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkMgr.NotifyResult{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageListAdapter adapter;
    private ProgressBar progressBar;

    @Override
    public void onNetworkResult(boolean success) {
        if (success == true) {
            adapter.notifyDataSetChanged();
        }


        else {

            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show();
        }

        // When the long task is finished, hide it again
        progressBar.setVisibility(View.GONE);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


     recyclerView = (RecyclerView) findViewById(R.id.photoListView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        // Attach adapter
        adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);

        adapter.imageList = NetworkMgr.getInstance(this).imageList;


        progressBar = (ProgressBar) findViewById(R.id.progressBar6);
        // Just before starting a long task, make it visible
        progressBar.setVisibility(View.VISIBLE);




        NetworkMgr netMgr = NetworkMgr.getInstance(this);
        netMgr.loadPhotos(this);









    }




}

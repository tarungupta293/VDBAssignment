package com.example.tarun.vdbassignment.ui.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.tarun.vdbassignment.R;
import com.example.tarun.vdbassignment.appUtils.AppDialogs;
import com.example.tarun.vdbassignment.appUtils.AppListeners;
import com.example.tarun.vdbassignment.appUtils.AppPref;
import com.example.tarun.vdbassignment.beans.Data;
import com.example.tarun.vdbassignment.beans.ErrorResponse;
import com.example.tarun.vdbassignment.restapi.ApiClient;
import com.example.tarun.vdbassignment.restapi.ApiInterface;
import com.example.tarun.vdbassignment.ui.adapters.RecyclerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // https://api.github.com/users/JakeWharton/repos?page=1&per_page=15

    private int pagination = 0;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private boolean isMaximumDataDownloaded = false;
    private List<Data> dataList = new ArrayList<>();
    private AppPref appPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appPref = new AppPref(this);
        initialiseViews();
        getData(true,false);
    }

    private void initialiseViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        final LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPos = linearLayoutManager.findLastVisibleItemPosition();
                Log.e("last_position",""+lastPos);
                if(!isMaximumDataDownloaded && lastPos>dataList.size()-3) {
                    if (lastPos == dataList.size())
                        getData(false, true);
                    if (lastPos >= dataList.size() - 2) {
                        getData(false, false);
                    }
                }
            }
        });

        recyclerAdapter = new RecyclerAdapter(this,dataList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void getData(final boolean isFirstTime, final boolean isLastPositionVisible) {
        Log.e("getData",""+pagination+" ");
        pagination = pagination+1;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Data>> call = apiService.getResults(String.valueOf(pagination),"15");
        if (isFirstTime)
            progressDialog.show();
        else if(isLastPositionVisible)
            progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (isFirstTime)
                    progressDialog.dismiss();
                else if(isLastPositionVisible)
                    progressBar.setVisibility(View.VISIBLE);
                Gson gson = new Gson();
                try {
                    if(response.body().size() == 0){
                        Toast.makeText(MainActivity.this,getString(R.string.no_more_data_to_download),Toast.LENGTH_SHORT).show();
                        isMaximumDataDownloaded = true;
                    }else {
                        List<Data> list = response.body();
                        dataList.addAll(list);
                        recyclerAdapter.updateAdapter(dataList);
                        if (isFirstTime)
                            appPref.saveData(new Gson().toJson(list));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        ErrorResponse errorResponse = gson.fromJson(response.errorBody().string(),ErrorResponse.class);
                        Toast.makeText(MainActivity.this,errorResponse.getMessage(),Toast.LENGTH_SHORT).show();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                if (isFirstTime)
                    progressDialog.dismiss();
                else if(isLastPositionVisible)
                    progressBar.setVisibility(View.VISIBLE);
                try {
                    pagination--;
                    if (dataList.size() == 0) {
                        AppDialogs.showAlertDialog(MainActivity.this, getString(R.string.oops), getString(R.string.no_internet_message), false, new AppListeners.DialogCallback() {
                            @Override
                            public void onClickPositiveButton() {
                                List<Data> list = new Gson().fromJson(appPref.getData(), new TypeToken<List<Data>>() {}.getType());
                                if (list != null && list.size() > 0) {
                                    pagination = 1;
                                    dataList.addAll(list);
                                    recyclerAdapter.updateAdapter(dataList);
                                }
                            }

                            @Override
                            public void onClickNegativeButton() {

                            }
                        });
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}

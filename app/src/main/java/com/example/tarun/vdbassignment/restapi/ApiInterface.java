package com.example.tarun.vdbassignment.restapi;

import com.example.tarun.vdbassignment.beans.Data;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by Navdeep on 5/12/2018.
 */

public interface ApiInterface {
    @GET("repos")
    Call<List<Data>> getResults(@Query("page") String page, @Query("per_page") String per_page);
}

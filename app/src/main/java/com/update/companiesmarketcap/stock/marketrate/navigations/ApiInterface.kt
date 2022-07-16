package com.update.companiesmarketcap.stock.marketrate.navigations

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @Headers("Content-Type: application/json")
    @GET("/IPO/upcoming")
    fun Payment(): Call<String?>?
}
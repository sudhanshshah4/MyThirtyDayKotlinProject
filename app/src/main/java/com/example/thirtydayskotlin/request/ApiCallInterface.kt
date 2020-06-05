package com.example.thirtydayskotlin.request

import com.example.thirtydayskotlin.utils.Urls
import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiCallInterface {

    @GET(Urls.ADDRESS_REQ)
    fun search(@Query("queryString") queryString: String, @Query("city") city: String): Observable<JsonElement?>?

}

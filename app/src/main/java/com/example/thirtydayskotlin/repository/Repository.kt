package com.example.thirtydayskotlin.repository

import com.example.thirtydayskotlin.request.ApiCallInterface
import com.google.gson.JsonElement
import io.reactivex.Observable

class Repository(private val apiCallInterface: ApiCallInterface) {

    fun fetchAddress(queryString: String, city: String): Observable<JsonElement?>? {
        return apiCallInterface.search(queryString,city)
    }

}

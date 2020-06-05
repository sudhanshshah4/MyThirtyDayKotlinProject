package com.example.thirtydayskotlin.response

import android.util.Log
import com.example.thirtydayskotlin.utils.Status
import com.google.gson.JsonElement
import java.util.*

class ApiResponse private constructor(val status: Status, val data: JsonElement?, val error: Throwable?) {

    companion object {
        fun loading(): ApiResponse {
            return ApiResponse(Status.LOADING, null, null)
        }

        fun success(data: JsonElement): ApiResponse {
            return ApiResponse(Status.SUCCESS, data, null)
        }

        fun error(error: Throwable): ApiResponse {
            Log.i("Airtel", Objects.requireNonNull(error.message))
            return ApiResponse(Status.ERROR, null, error)
        }
    }

}
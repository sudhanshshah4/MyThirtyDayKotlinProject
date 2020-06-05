package com.example.thirtydayskotlin.base.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thirtydayskotlin.repository.Repository
import com.example.thirtydayskotlin.response.ApiResponse
import com.google.gson.JsonElement
import java.util.concurrent.TimeUnit


import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class SearchListModel(private val repository: Repository) : ViewModel()
{
    private val disposables = CompositeDisposable()
    private val responseLiveData = MutableLiveData<ApiResponse>()
    private val mSearchResultsSubject: PublishSubject<String>

    init {
        mSearchResultsSubject = PublishSubject.create<String>()
        mSearchResultsSubject.debounce(400, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .map(Function<String, Unit> { o ->
                Log.i("Airtel", "Search Keyword : $o")
                getListByKeywords(o.toString())
            })
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit> {
                override fun onError(e: Throwable) {}
                override fun onComplete() {}
                override fun onSubscribe(d: Disposable) {}
                override fun onNext(unit: Unit) {}
            })
    }

    private fun getListByKeywords(city: String){

        val queryString:String = "airtel"
        disposables.add(repository.fetchAddress(queryString,city)
        !!.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { d: Disposable? -> responseLiveData.postValue(ApiResponse.loading()) }
            .subscribe(
                { result: JsonElement? -> responseLiveData.postValue(ApiResponse.success(result!!)) }
            ) { throwable: Throwable? -> responseLiveData.postValue(ApiResponse.error(throwable!!)) })


    }

    fun searchResponse(): MutableLiveData<ApiResponse> {
        return responseLiveData
    }

    fun setSearchData(search_txt: String) {
        mSearchResultsSubject.onNext(search_txt!!)

    }

}
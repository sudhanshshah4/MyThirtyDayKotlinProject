package com.example.thirtydayskotlin.base.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thirtydayskotlin.MyApplication
import com.example.thirtydayskotlin.R
import com.example.thirtydayskotlin.response.ApiResponse
import com.example.thirtydayskotlin.utils.Loader.getProgressDialog
import com.example.thirtydayskotlin.utils.Status
import com.example.thirtydayskotlin.utils.ViewModelFactory
import com.example.thirtydayskotlin.base.adapter.SearchListAdapter
import com.example.thirtydayskotlin.base.model.AddressListModel
import com.example.thirtydayskotlin.base.viewmodel.SearchListModel
import com.example.thirtydayskotlin.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity()
{
    private var binding: ActivityMainBinding? = null
    @Inject
    lateinit var factory: ViewModelFactory
    private var model: SearchListModel? = null
    private var progressDialog: ProgressBar? = null
    private var searchResultlist: RecyclerView? = null
    private var converter: Gson? = null
    private var type: Type? = null
    private var addresslistdata: MutableList<AddressListModel>? = null
    private lateinit var searchlistadapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this,R.layout.activity_main) // layout binding

        (application as MyApplication).appComponent?.doMainActivityInjection(this) // injection

        model = ViewModelProvider(this, factory).get(SearchListModel::class.java)
        converter = Gson()
        type = object : TypeToken<List<AddressListModel?>?>() {}.type
        addresslistdata = ArrayList()
        progressDialog = getProgressDialog(this)
        searchResultlist = setLayout(binding?.searchlist)

        binding!!.searchtext.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                model!!.setSearchData(s.toString())
            }
            override fun afterTextChanged(s: Editable) {}
        })


        model!!.searchResponse().observe(this, Observer { apiResponse: ApiResponse -> consumeResponse(apiResponse) })
    }

    private fun consumeResponse(apiResponse: ApiResponse) {
        when (apiResponse.status) {
            Status.LOADING -> progressDialog!!.visibility = View.VISIBLE
            Status.SUCCESS -> {
                progressDialog!!.visibility = View.GONE
                renderSuccessResponse(apiResponse.data)
            }
            Status.ERROR -> {
                progressDialog!!.visibility = View.GONE
                Toast.makeText(this@MainActivity, resources.getString(R.string.errorString), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun renderSuccessResponse(data: JsonElement?) {
        Log.d("Airtel", data.toString())
        val response = JSONObject(data.toString())
        val addressList = response.getJSONObject("data").getJSONArray("addressList")
        // add data to adapter
        addresslistdata = converter!!.fromJson<MutableList<AddressListModel>>(addressList.toString(), type)
        searchlistadapter = SearchListAdapter(addresslistdata)
        searchResultlist!!.adapter = searchlistadapter

    }

    // set relative layout
    private fun setLayout( view:RecyclerView?): RecyclerView?{
        view?.setHasFixedSize(true)
        view?.isNestedScrollingEnabled = false
        view?.itemAnimator = DefaultItemAnimator()
        val  manager: LinearLayoutManager? = LinearLayoutManager(this)
        manager?.orientation = RecyclerView.VERTICAL
        view?.layoutManager = manager
        return view
    }

}
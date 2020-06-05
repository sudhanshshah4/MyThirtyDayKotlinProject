package com.example.thirtydayskotlin.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.thirtydayskotlin.R
import com.example.thirtydayskotlin.base.model.AddressListModel
import com.example.thirtydayskotlin.base.viewholder.SearchItem
import com.example.thirtydayskotlin.databinding.MSearchitemBinding

class SearchListAdapter(
    Listdata: MutableList<AddressListModel>?
) : RecyclerView.Adapter<SearchItem>() {
    private var layoutInflater: LayoutInflater? = null
    private var data: MutableList<AddressListModel>? = Listdata
    private lateinit var binding:MSearchitemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItem {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        binding = DataBindingUtil.inflate(layoutInflater!!, R.layout.m_searchitem, parent, false)
        return SearchItem(binding)
    }

    override fun getItemCount(): Int {
        return data!!.size
    }

    override fun onBindViewHolder(holder: SearchItem, position: Int) {
        holder.binding.item=data!![position]
    }

}

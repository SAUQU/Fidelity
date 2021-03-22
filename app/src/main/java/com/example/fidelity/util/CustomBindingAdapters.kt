package com.example.fidelity.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
//Singleton class

object CustomBindingAdapters {

    @JvmStatic
    @BindingAdapter("app:adapter", "app:data")
    fun <T : RecyclerViewAdapter<*, *>> bind(
        recyclerView: RecyclerView,
        adapter: T,
        data: List<Nothing>
    ) {
        recyclerView.adapter = adapter
        adapter.updateData(data)
    }
}
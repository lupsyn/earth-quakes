package com.challange.hilt.util.ext

import androidx.recyclerview.widget.RecyclerView

fun <H : RecyclerView.ViewHolder,
        T : RecyclerView.Adapter<H>,
        L : RecyclerView.LayoutManager>
        RecyclerView.init(
        newLayoutManager: () -> L,
        newAdapter: () -> T,
        otherOptions: (RecyclerView.(T) -> Unit)? = null) {
    this.apply {
        val newInstanceOfAdapter = newAdapter()
        val llManager = newLayoutManager()

        if (this.adapter == null) {
            this.adapter = newInstanceOfAdapter
        }

        if (this.layoutManager == null) {
            this.layoutManager = llManager
        }
        otherOptions?.let { this.it(newInstanceOfAdapter) }
    }
}
package com.challange.hilt.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.challange.hilt.R
import com.challange.hilt.ui.models.EarthQuakesUiModel


class MainAdapter(private var listener: EarthQuakesUiModelListener) :
    ListAdapter<EarthQuakesUiModel, EarthQuakesUiModelViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EarthQuakesUiModelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EarthQuakesUiModelViewHolder(inflater, parent, listener)
    }

    override fun onBindViewHolder(holder: EarthQuakesUiModelViewHolder, position: Int) {
        val uiModels: EarthQuakesUiModel = getItem(position)

        holder.bind(uiModels)
    }
}

class EarthQuakesUiModelViewHolder(
    inflater: LayoutInflater, parent: ViewGroup,
    private val listener: EarthQuakesUiModelListener
) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item, parent, false)) {

    private var imageView: ImageView? = null
    private var date: TextView? = null
    private var lng: TextView? = null
    private var lat: TextView? = null
    private var depth: TextView? = null
    private var magnitude: TextView? = null
    private var quakesUiModel: EarthQuakesUiModel? = null
    private var itemContainer: ConstraintLayout

    init {
        imageView = itemView.findViewById(R.id.imageView)
        date = itemView.findViewById(R.id.date)
        lng = itemView.findViewById(R.id.lng)
        lat = itemView.findViewById(R.id.lat)
        depth = itemView.findViewById(R.id.depth)
        magnitude = itemView.findViewById(R.id.magnitude)
        itemContainer = itemView.findViewById(R.id.itemContainer)
    }

    @SuppressLint("SetTextI18n")
    fun bind(uiModel: EarthQuakesUiModel) {
        quakesUiModel = uiModel
        date?.text = uiModel.datetime
        lat?.text = lat?.context?.getString(R.string.latitude) + " " + uiModel.lat
        lng?.text = lng?.context?.getString(R.string.longitude) + " " + uiModel.lng
        depth?.text = depth?.context?.getString(R.string.depth) + " " + uiModel.depth
        magnitude?.text =
            magnitude?.context?.getString(R.string.magnitude) + " " + uiModel.magnitude
        imageView?.setImageResource(if (uiModel.magnitude >= 8) R.drawable.ic_noun_earthquake_big_magnitudo else R.drawable.ic_noun_earthquake)

        itemContainer.setOnClickListener {
            this.quakesUiModel.apply { listener.onClick(quakesUiModel!!) }
        }
    }
}

interface EarthQuakesUiModelListener {
    fun onClick(item: EarthQuakesUiModel)
}

private class DiffCallback : DiffUtil.ItemCallback<EarthQuakesUiModel>() {
    override fun areItemsTheSame(
        oldItem: EarthQuakesUiModel,
        newItem: EarthQuakesUiModel
    ): Boolean {
        return oldItem.eqid == newItem.eqid &&
                oldItem.datetime == newItem.datetime
    }

    override fun areContentsTheSame(
        oldItem: EarthQuakesUiModel,
        newItem: EarthQuakesUiModel
    ): Boolean {
        return oldItem.eqid == newItem.eqid &&
                oldItem.datetime == newItem.datetime &&
                oldItem.magnitude == newItem.magnitude
    }
}

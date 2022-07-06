package com.poney.gpuimage.adapter

import android.content.Context
import com.poney.gpuimage.filter.base.GPUImageFilterType
import androidx.recyclerview.widget.RecyclerView
import com.poney.gpuimage.adapter.FilterAdapter.FilterHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.poney.gpuimage.R
import android.widget.TextView
import android.widget.FrameLayout
import android.widget.ImageView
import com.poney.gpuimage.utils.FilterTypeHelper
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * Created by why8222 on 2016/3/17.
 */
class FilterAdapter(private val context: Context, private val filters: Array<GPUImageFilterType>?) :
    RecyclerView.Adapter<FilterHolder>() {
    private var selected = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.filter_item_layout,
            parent, false
        )
        val viewHolder = FilterHolder(view)
        viewHolder.thumbImage = view
            .findViewById<View>(R.id.filter_thumb_image) as ImageView
        viewHolder.filterName = view
            .findViewById<View>(R.id.filter_thumb_name) as TextView
        viewHolder.filterRoot = view
            .findViewById<View>(R.id.filter_root) as FrameLayout
        viewHolder.thumbSelected = view
            .findViewById<View>(R.id.filter_thumb_selected) as FrameLayout
        viewHolder.thumbSelectedBg = view.findViewById(R.id.filter_thumb_selected_bg)
        return viewHolder
    }

    override fun onBindViewHolder(holder: FilterHolder, position: Int) {
        holder.thumbImage!!.setImageResource(FilterTypeHelper.filterType2Thumb(filters!![position]))
        holder.filterName!!.setText(FilterTypeHelper.filterType2Name(filters[position]))
        holder.filterName!!.setBackgroundColor(
            context.resources.getColor(
                FilterTypeHelper.filterType2Color(filters[position])
            )
        )
        if (position == selected) {
            holder.thumbSelected!!.visibility = View.VISIBLE
            holder.thumbSelectedBg!!.setBackgroundColor(
                context.resources.getColor(
                    FilterTypeHelper.filterType2Color(filters[position])
                )
            )
            holder.thumbSelectedBg!!.alpha = 0.7f
        } else {
            holder.thumbSelected!!.visibility = View.GONE
        }
        holder.filterRoot!!.setOnClickListener(View.OnClickListener {
            if (selected == position) return@OnClickListener
            val lastSelected = selected
            selected = position
            notifyItemChanged(lastSelected)
            notifyItemChanged(position)
            onFilterChangeListener?.onFilterChanged(filters[position])
        })
    }

    override fun getItemCount(): Int {
        return filters?.size ?: 0
    }

    inner class FilterHolder(itemView: View?) : ViewHolder(itemView!!) {
        var thumbImage: ImageView? = null
        var filterName: TextView? = null
        var thumbSelected: FrameLayout? = null
        var filterRoot: FrameLayout? = null
        var thumbSelectedBg: View? = null
    }

    interface OnFilterChangeListener {
        fun onFilterChanged(filterType: GPUImageFilterType?)
    }

    private var onFilterChangeListener: OnFilterChangeListener ? = null

    fun setOnFilterChangeListener(onFilterChangeListener: OnFilterChangeListener?) {
        this.onFilterChangeListener = onFilterChangeListener
    }
}
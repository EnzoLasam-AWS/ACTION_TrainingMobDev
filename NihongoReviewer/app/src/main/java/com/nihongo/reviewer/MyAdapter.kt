package com.nihongo.reviewer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class MyAdapter(private val particleList: ArrayList<NihongoParticles>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: OnItemClickListener){

        mListener = listener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent, false)
        return MyViewHolder(itemView, mListener)


    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = particleList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
    }

    override fun getItemCount(): Int {


        return particleList.size
    }
    class MyViewHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView){

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading : TextView = itemView.findViewById(R.id.tvHeading)

        init {
            itemView.setOnClickListener{

                listener.onItemClick(absoluteAdapterPosition)
            }
        }
    }

}
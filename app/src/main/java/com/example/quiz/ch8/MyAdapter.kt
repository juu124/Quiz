package com.example.quiz.ch8

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.quiz.databinding.ItemCatListBinding

class MyViewHolder(val binding: ItemCatListBinding) : RecyclerView.ViewHolder(binding.root)

class MyAdapter(val context: Context, val datas: List<Cat>?) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        Log.d("jay", "datas: $datas")
        Log.d("jay", "datas size: ${datas?.size}")
        return MyViewHolder(
            ItemCatListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val cat = datas?.get(position)

        cat?.url?.let {
            Glide.with(context)
                .load(it)
                .centerCrop()
                .into(holder.binding.imageView)
        }
    }

    override fun getItemCount() = datas?.size ?: 0
}
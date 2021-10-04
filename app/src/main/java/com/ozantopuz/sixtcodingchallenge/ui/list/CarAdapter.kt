package com.ozantopuz.sixtcodingchallenge.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ozantopuz.sixtcodingchallenge.data.entity.Car
import com.ozantopuz.sixtcodingchallenge.databinding.ItemCarBinding
import com.ozantopuz.sixtcodingchallenge.util.extension.loadImage

class CarAdapter(
    private var list: ArrayList<Car> = arrayListOf()
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemBinding =
            ItemCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item: Car = list[position]
        with(holder.itemBinding) {
            imageView.loadImage(item.carImageUrl)
            textViewTitle.text = item.modelName?.uppercase()
            textViewName.text = item.name?.uppercase()
            textViewMake.text = item.make?.uppercase()
        }
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<Car>) {
        this.list = list
        notifyDataSetChanged()
    }

    class CarViewHolder(val itemBinding: ItemCarBinding) : RecyclerView.ViewHolder(itemBinding.root)
}
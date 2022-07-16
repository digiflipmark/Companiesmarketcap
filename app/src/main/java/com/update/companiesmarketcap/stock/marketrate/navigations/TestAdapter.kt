package com.update.companiesmarketcap.stock.marketrate.navigations

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.update.companiesmarketcap.R
import de.hdodenhof.circleimageview.CircleImageView

class TestAdapter(val empty: MutableList<TestModel>) : RecyclerView.Adapter<TestAdapter.Holder>() {


    class Holder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {

        val imageView: CircleImageView = itemView.findViewById(R.id.iv_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestAdapter.Holder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: TestAdapter.Holder, position: Int) {


        holder.imageView.load(empty[position].image)
    }

    override fun getItemCount(): Int {
        return empty.size
    }


}
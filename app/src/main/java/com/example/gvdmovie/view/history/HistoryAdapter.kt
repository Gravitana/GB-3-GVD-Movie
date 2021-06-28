package com.example.gvdmovie.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.gvdmovie.R
import com.example.gvdmovie.model.Movie
import kotlinx.android.synthetic.main.history_recycler_item.view.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_recycler_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Movie) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.recyclerViewItem.text =
                    String.format("%s %s", data.id, data.title)
                itemView.setOnClickListener {
                    Toast.makeText(
                        itemView.context,
                        "on click: ${data.title}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}

package br.com.fulltime.myapplicationpaging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var list: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val data = list[position]
        holder.textView.text = data.toString()
    }

    override fun getItemCount(): Int = list.size


    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView

        /**
         * aqui é so para  iniciar a variavel antes da instancia
         */

        init {
            textView = itemView.findViewById(R.id.text_number)
        }
    }


}
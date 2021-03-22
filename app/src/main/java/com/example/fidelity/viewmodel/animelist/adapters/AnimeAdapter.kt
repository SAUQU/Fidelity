package com.example.fidelity.viewmodel.animelist.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fidelity.R
import com.example.fidelity.model.AnimeModel
import com.example.fidelity.util.*
import com.example.fidelity.util.Utils.dateFormatYYYYMMDD
import kotlinx.android.synthetic.main.item_anime_list.view.*
import java.util.*

class AnimeAdapter(
    private val mContext: Context,
    private val anime_List: ArrayList<AnimeModel>
) : RecyclerViewAdapter<AnimeModel, AnimeAdapter.MyViewHolder>() {

    private var selectedPosition = -1
    var listener: IAnimeAdapter? = null

    override fun updateData(data: List<AnimeModel>) {
        if (data.isEmpty()) {
            this.anime_List.clear()
        } else {
            this.anime_List.addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(parent.inflate(R.layout.item_anime_list))

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.loadData(position)

    override fun getItemCount(): Int = anime_List.size


    inner class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun loadData(position: Int) {
            val animeModel = anime_List[position]
            view.apply {
                setBackgroundColor(getBackgroundColor(position))

                con_layoutMain.tag = position
                con_layoutMain.setOnClickListener {
                    listener?.itemSelected(animeModel)
                    selectedPosition = position
                    notifyDataSetChanged()
                }

                tv_anime_title.text = animeModel.title
                tv_is_airing.text = if(animeModel.airing==true)"On Air" else "Off Air"
                tv_no_episodes.text = animeModel.episodes.toString()
                tv_anime_start_date.text =
                    animeModel.start_date?.toDate()?.formatTo(dateFormatYYYYMMDD)

                img_pathImage.loadImg(animeModel.image_url)
                tv_rated.text = animeModel.rated
            }

        }

        private fun getBackgroundColor(position: Int): Int =
            if (selectedPosition == position)
                ContextCompat.getColor(mContext, R.color.colorAccent)
            else ContextCompat.getColor(mContext, android.R.color.white)

    }

    interface IAnimeAdapter {
        fun itemSelected(animeModel: AnimeModel)
    }
}
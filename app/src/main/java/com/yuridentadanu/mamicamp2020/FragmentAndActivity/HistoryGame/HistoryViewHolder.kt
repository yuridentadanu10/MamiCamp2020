package com.yuridentadanu.mamicamp2020.FragmentAndActivity.HistoryGame

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    fun bindItem(history: HistoryGame) {
        view.apply {
            //get data users
            val date = ": ${history.date}"
            val score = ": ${history.score}"
            //set view
            tv_date.text = date
            tv_score.text = score
        }
    }
}
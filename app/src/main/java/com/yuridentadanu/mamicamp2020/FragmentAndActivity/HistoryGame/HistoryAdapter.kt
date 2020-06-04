package com.yuridentadanu.mamicamp2020.FragmentAndActivity.HistoryGame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryAdapter(options: FirestoreRecyclerOptions<HistoryGame> ): FirestoreRecyclerAdapter<HistoryGame, HistoryAdapter.HistoryViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false))
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int, model: HistoryGame) {
        holder.bindItem(model)
        holder.itemView.setOnClickListener {
        }
    }
    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_date = view.tv_date
        private val tv_score = view.tv_score

        fun bindItem(history: HistoryGame) {
            val date = ": ${history.date}"
            val score = ": ${history.score}"
            //set view
            tv_date.text = date
            tv_score.text = score
        }
    }


}
package com.yuridentadanu.mamicamp2020.FragmentAndActivity.Leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import com.yuridentadanu.mamicamp2020.model.Leaderboard
import kotlinx.android.synthetic.main.item_history.view.*
import kotlinx.android.synthetic.main.item_history.view.tv_score

class LeaderboardAdapter(options: FirestoreRecyclerOptions<Leaderboard>): FirestoreRecyclerAdapter<Leaderboard, LeaderboardAdapter.LeaderboardViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderboardViewHolder {
        return LeaderboardViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_leaderboard2, parent, false))
    }

    override fun onBindViewHolder(holder: LeaderboardViewHolder, position: Int, model: Leaderboard) {
        holder.bindItem(model)
        holder.itemView.setOnClickListener {
        }
    }
    class LeaderboardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tv_name = view.tv_name
        private val tv_score = view.tv_score
        private val tv_position = view.tv_position
        private val tv_firstLetter = view.tv_firstLetter
        private val img_badge = view.badge

        fun bindItem(leaderboard: Leaderboard) {
            val name = "${leaderboard.name}"
            val score = "${leaderboard.score}"
            val number = adapterPosition+1
            val position = "#$number"
            val firstchar = name.get(0).toString()
            //set view
            tv_name.text = name
            tv_score.text = "Score: $score"
            tv_position.text = position
            tv_firstLetter.text = firstchar

            val item_id = number
            when (item_id) {
                1 -> img_badge.setImageResource(R.drawable.ic_medal)
                2 ->img_badge.setImageResource(R.drawable.ic_second)
                3 ->img_badge.setImageResource(R.drawable.ic_third)

            }


        }
    }


}
package com.yuridentadanu.mamicamp2020.FragmentAndActivity.Leaderboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuridentadanu.mamicamp2020.Const
import com.yuridentadanu.mamicamp2020.FragmentAndActivity.HistoryGame.HistoryAdapter
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import com.yuridentadanu.mamicamp2020.model.Leaderboard
import kotlinx.android.synthetic.main.activity_history.*
import kotlinx.android.synthetic.main.activity_leaderboard.*

class LeaderboardActivity : AppCompatActivity() {
    private lateinit var mAdapter: LeaderboardAdapter
    private var db: FirebaseFirestore = Firebase.firestore
    private val mLeaderboardCollection = db.collection(Const.DB_LEADERBOARD)
    private val mQuery = mLeaderboardCollection.orderBy(Const.DB_SCORE, Query.Direction.DESCENDING).limit(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)
        val actionBar = supportActionBar
        actionBar!!.title = "Leaderboard"
        setupAdapter()
    }

    private fun setupAdapter(){
        val options = FirestoreRecyclerOptions.Builder<Leaderboard>()
            .setQuery(mQuery, Leaderboard::class.java)
            .build()

        mAdapter = LeaderboardAdapter(options)
        mAdapter.notifyDataSetChanged()
        rv_leaderboard.adapter = mAdapter

        rv_leaderboard.apply {
            layoutManager = LinearLayoutManager(this@LeaderboardActivity)
            adapter = mAdapter
        }


    }

    override fun onStart() {
        super.onStart()
        mAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mAdapter.stopListening()
    }

}

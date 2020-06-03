package com.yuridentadanu.mamicamp2020.FragmentAndActivity.HistoryGame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.yuridentadanu.mamicamp2020.Const.DB_HISTORY
import com.yuridentadanu.mamicamp2020.Const.DB_SCORE
import com.yuridentadanu.mamicamp2020.Const.DB_USERS
import com.yuridentadanu.mamicamp2020.Const.getUidUser
import com.yuridentadanu.mamicamp2020.R
import com.yuridentadanu.mamicamp2020.model.HistoryGame
import kotlinx.android.synthetic.main.activity_history.*

class HistoryActivity : AppCompatActivity() {
    private lateinit var mAdapter: HistoryAdapter
    private var db: FirebaseFirestore =Firebase.firestore
    private val mUsersCollection = db.collection(DB_USERS).document(getUidUser()).collection(
        DB_HISTORY)
    private val mQuery = mUsersCollection.orderBy(DB_SCORE, Query.Direction.DESCENDING)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        val actionBar = supportActionBar
        actionBar!!.title = "Game History"

        setupAdapter()

    }

    private fun setupAdapter(){
        val options = FirestoreRecyclerOptions.Builder<HistoryGame>()
            .setQuery(mQuery, HistoryGame::class.java)
            .build()

        mAdapter = HistoryAdapter(options)
        mAdapter.notifyDataSetChanged()
        rv_firedb.adapter = mAdapter

        rv_firedb.apply {
            layoutManager = LinearLayoutManager(this@HistoryActivity)
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



package com.yuridentadanu.mamicamp2020

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Const {


    val DB_USERS = "users"
    val DB_HISTORY = "history"

    val DB_DATE = "date"
    val DB_SCORE = "score"

    fun getUidUser(): String {
        val mAuth: FirebaseAuth = Firebase.auth
        val uid = mAuth.uid.toString()
        return uid
    }


}
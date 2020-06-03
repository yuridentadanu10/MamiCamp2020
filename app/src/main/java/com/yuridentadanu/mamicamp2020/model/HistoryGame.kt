package com.yuridentadanu.mamicamp2020.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class HistoryGame(
    var date: String? = null,
    var score: Long? = null
) : Parcelable
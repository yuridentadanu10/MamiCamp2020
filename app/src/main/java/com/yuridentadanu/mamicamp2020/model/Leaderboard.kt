package com.yuridentadanu.mamicamp2020.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Leaderboard(
    var name: String? = null,
    var score: Long? = null
) : Parcelable
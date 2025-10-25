package com.ilesha.newstestapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey val queryId: String,
    val prevKey: Int?,
    val nextKey: Int?
)
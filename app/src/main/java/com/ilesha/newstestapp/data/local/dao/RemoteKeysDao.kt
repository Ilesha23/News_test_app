package com.ilesha.newstestapp.data.local.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ilesha.newstestapp.data.local.entity.RemoteKeysEntity

interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(remoteKey: RemoteKeysEntity)

    @Query("SELECT * FROM remote_keys WHERE queryId = :queryId")
    suspend fun getRemoteKeysByQuery(queryId: String): RemoteKeysEntity?

    @Query("DELETE FROM remote_keys WHERE queryId = :queryId")
    suspend fun deleteRemoteKeysByQuery(queryId: String)

}
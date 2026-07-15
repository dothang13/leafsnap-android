package fxc.dev.core.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import fxc.dev.core.domain.model.SampleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SampleDao {
    @Query("SELECT * FROM sample")
    fun getAll(): Flow<List<SampleEntity>>
}
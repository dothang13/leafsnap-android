package fxc.dev.core.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sample")
data class SampleEntity(
    @PrimaryKey val id: Long,
    val name: String
)
package fxc.dev.core.data.source.local

import androidx.room.*
import fxc.dev.core.data.source.local.converter.Converters
import fxc.dev.core.data.source.local.dao.SampleDao
import fxc.dev.core.domain.model.SampleEntity

@Database(
    entities = [
        SampleEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters::class
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sampleDao(): SampleDao
}
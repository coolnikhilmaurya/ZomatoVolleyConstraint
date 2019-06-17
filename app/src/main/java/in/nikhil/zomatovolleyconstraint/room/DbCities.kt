package `in`.nikhil.zomatovolleyconstraint.room

import `in`.nikhil.zomatovolleyconstraint.modal.LocationSuggestion
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = arrayOf(LocationSuggestion::class),
    version = 1
)
abstract class DbCities :RoomDatabase() {
    abstract fun CityDao(): DaoCities

    companion object {
        private var INSTANCE: DbCities? = null

        fun getInstance(context: Context): DbCities? {
            if (INSTANCE == null) {
                synchronized(DbCities::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        DbCities::class.java, "weather.db"
                    )
                        .build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}


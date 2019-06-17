package `in`.nikhil.zomatovolleyconstraint.room

import `in`.nikhil.zomatovolleyconstraint.modal.LocationSuggestion
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaoCities {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: LocationSuggestion);

    @Query("select * from LocationSuggestion order by name")
    fun getCitiesSearched(): List<LocationSuggestion>



}
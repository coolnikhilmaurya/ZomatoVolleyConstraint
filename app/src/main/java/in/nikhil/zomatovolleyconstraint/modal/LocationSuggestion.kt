package `in`.nikhil.zomatovolleyconstraint.modal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocationSuggestion(
    @PrimaryKey
    var id: Int = 0,
    var name: String = "",
    var country_id: Int = 0,
    var country_name: String = "",
    var country_flag_url: String = "",
    var should_experiment_with: Int = 0,
    var discovery_enabled: Int = 0,
    var has_new_ad_format: Int = 0,
    var is_state: Int = 0,
    var state_id: Int = 0,
    var state_name: String = "",
    var state_code: String = ""
)
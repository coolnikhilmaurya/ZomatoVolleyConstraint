package `in`.nikhil.zomatovolleyconstraint.modal

data class citySearchResponse(
    var location_suggestions: MutableList<LocationSuggestion> = mutableListOf(),
    var status: String = "",
    var has_more: Int = 0,
    var has_total: Int = 0
)
package `in`.nikhil.zomatovolleyconstraint.modal

data class ResturentInCitySearchResponse(
    var results_found: Int = 0,
    var results_start: Int = 0,
    var results_shown: Int = 0,
    var restaurants: List<Restaurant> = listOf()
)
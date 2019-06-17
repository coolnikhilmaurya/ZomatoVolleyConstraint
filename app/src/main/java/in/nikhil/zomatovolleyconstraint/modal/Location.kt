package `in`.nikhil.zomatovolleyconstraint.modal

data class Location(
    var address: String = "",
    var locality: String = "",
    var city: String = "",
    var city_id: Int = 0,
    var latitude: String = "",
    var longitude: String = "",
    var zipcode: String = "",
    var country_id: Int = 0,
    var locality_verbose: String = ""
)
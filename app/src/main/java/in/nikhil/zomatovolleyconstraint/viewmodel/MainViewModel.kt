package `in`.nikhil.zomatovolleyconstraint.viewmodel


import `in`.nikhil.zomatovolleyconstraint.modal.LocationSuggestion
import `in`.nikhil.zomatovolleyconstraint.modal.Restaurant
import `in`.nikhil.zomatovolleyconstraint.modal.ResturentInCitySearchResponse
import `in`.nikhil.zomatovolleyconstraint.modal.citySearchResponse
import `in`.nikhil.zomatovolleyconstraint.room.DbCities
import `in`.nikhil.zomatovolleyconstraint.util.logit
import `in`.nikhil.zomatovolleyconstraint.util.myutil
import `in`.nikhil.zomatovolleyconstraint.volley.MySingleton
import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import java.util.logging.Handler

class MainViewModel(application: Application) : AndroidViewModel(application) {

    // planned to use for preserving the RecyclerView Scroll State
    var activeItemRV: Int = 0

    var boolHasItems = false

    val queue = MySingleton.getInstance(application).requestQueue
    private val mySingleTon = MySingleton.getInstance(application)
    private val mDb = DbCities.getInstance(application)

    // val baseUrl="https://incoherent-passbook.000webhostapp.com/"
    private val baseUrl = "https://developers.zomato.com/api/v2.1/"

    val LvSearchResult: MutableLiveData<List<LocationSuggestion>> = MutableLiveData()

    val LvListResturentResult = MutableLiveData<List<Restaurant>>()
/*
    val LvListResturentResult= DbRestuarant.myDbRestuarantDao().getAllRestaurents()
*/


    private var resCode: Int? = 0
    var isInitialLoad: Boolean = true

    fun searchCities(city: String) {
        val url = baseUrl + "cities?q=" + city

        val myStryResquest = object : StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {
                myutil.hideLoading()

                val res = Gson().fromJson(it, citySearchResponse::class.java)
                LvSearchResult.postValue(res.location_suggestions)
                isInitialLoad = false
                // view.context.toastlogit("Parsed response with code -$resCode -"+res.toString())
            },
            Response.ErrorListener {
                myutil.hideLoading()
            }
        ) {
            //  Not invoked in case of get only in put and post
            /*override fun getParams(): MutableMap<String, String> {
                val m :MutableMap<String,String> = mutableMapOf("lname" to "myCustomlname")
                m["fname"] = "this is my fname"
                return m
            }*/


            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf("user-key" to "0925b2ae59e2f2efd8c22ff6e3ce7d9f")
                return headers
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                resCode = response?.statusCode
                return super.parseNetworkResponse(response)

            }

            /*override fun getBody(): ByteArray {
                return mRequestBody.getBytes("utf-8")
            }*/
        }

        queue.add(myStryResquest)

// Add a request (in this example, called stringRequest) to your RequestQueue.
        mySingleTon.addToRequestQueue(req = myStryResquest)
    }

    fun populate_searchCities_history() {

        AsyncTask.execute {
            // run db interaction on a background thread
            LvSearchResult.postValue(mDb?.CityDao()?.getCitiesSearched())
        }
    }

    fun searchResturentInCity(city_id: Int) {
        val url = baseUrl + "search?entity_type=city&entity_id=$city_id"

        val myStryResquest = object : StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {
                val res = Gson().fromJson(it, ResturentInCitySearchResponse::class.java)
                /*DbRestuarant.myDbRestuarantDao().insertListRestuarent(res.restaurants)*/
                LvListResturentResult.postValue(res.restaurants)
                isInitialLoad = false
                "Parsed response with code -$resCode. Res is - $res ".logit()
            },
            Response.ErrorListener {
                "Got error code -$resCode ${it}".logit()
                myutil.hideLoading()
            }
        ) {
            //  Not invoked in case of get only in put and post
            /*override fun getParams(): MutableMap<String, String> {
                val m :MutableMap<String,String> = mutableMapOf("lname" to "myCustomlname")
                m["fname"] = "this is my fname"
                return m
            }*/


            override fun getHeaders(): MutableMap<String, String> {
                val headers = mutableMapOf<String, String>("user-key" to "0925b2ae59e2f2efd8c22ff6e3ce7d9f")
                return headers
            }

            override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
                resCode = response?.statusCode
                return super.parseNetworkResponse(response)

            }

            /*override fun getBody(): ByteArray {
                return mRequestBody.getBytes("utf-8")
            }*/
        }

        queue.add(myStryResquest)

// Add a request (in this example, called stringRequest) to your RequestQueue.
        mySingleTon.addToRequestQueue(req = myStryResquest)
    }

}

package `in`.nikhil.zomatovolleyconstraint.adapter

import `in`.nikhil.zomatovolleyconstraint.R
import `in`.nikhil.zomatovolleyconstraint.fragment.SearchCityFragmentDirections
import `in`.nikhil.zomatovolleyconstraint.modal.LocationSuggestion
import `in`.nikhil.zomatovolleyconstraint.modal.citySearchResponse
import `in`.nikhil.zomatovolleyconstraint.room.DbCities
import `in`.nikhil.zomatovolleyconstraint.util.toastlogit
import android.app.PendingIntent.getActivity
import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.city_search_item.view.*
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.os.IBinder
import androidx.core.content.ContextCompat.getSystemService
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.FragmentNavigator


class CitySearchResultsAdapter(val list: List<LocationSuggestion>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<CitySearchResultsAdapter.CitySearchResultsViewHolder>() {

    private lateinit var ctx: Context
    private lateinit var view: View
    private lateinit var mDb: DbCities


    override fun onCreateViewHolder(vg: ViewGroup, pos: Int): CitySearchResultsViewHolder {
        view = LayoutInflater.from(vg.context).inflate(R.layout.city_search_item, vg, false)
        ctx = vg.context
        mDb= DbCities.getInstance(ctx)!!
        return CitySearchResultsViewHolder(view)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(vh: CitySearchResultsViewHolder, pos: Int) {
        vh.tvCityId.text = list[pos].id.toString()
        vh.tvCityName.text = list[pos].name

        Glide.with(ctx).load(list[pos].country_flag_url).into(vh.imgFlag)

        vh.itemView.setOnClickListener {
            val action = SearchCityFragmentDirections.actionSearchCityFragmentToListResturentsInCityFragment()
            action.cityId = list[pos].id
            action.cityName = list[pos].name
            removePhoneKeypad()
            AsyncTask.execute {
                // run db o/p in background thread
                mDb.CityDao().insertCity(list[pos])
            }
            Navigation.findNavController(vh.itemView).navigate(action)
        }

    }

    fun removePhoneKeypad() {
        try {
            val inputManager = ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val binder = view.getWindowToken()
            inputManager.hideSoftInputFromWindow(
                binder,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    inner class CitySearchResultsViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        val imgFlag = view.imgFlag
        val tvCityId = view.tvCityId
        val tvCityName = view.tvCityName
    }
}
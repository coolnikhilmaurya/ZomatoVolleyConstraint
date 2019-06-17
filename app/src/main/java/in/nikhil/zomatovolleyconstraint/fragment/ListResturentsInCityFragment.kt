package `in`.nikhil.zomatovolleyconstraint.fragment

import `in`.nikhil.zomatovolleyconstraint.R
import `in`.nikhil.zomatovolleyconstraint.adapter.CitySearchResultsAdapter
import `in`.nikhil.zomatovolleyconstraint.adapter.ListResturentsInCityAdapter
import `in`.nikhil.zomatovolleyconstraint.util.myutil
import `in`.nikhil.zomatovolleyconstraint.util.toastlogit
import `in`.nikhil.zomatovolleyconstraint.viewmodel.MainViewModel
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_resturents_in_city_fragment.*
import kotlinx.android.synthetic.main.search_city_fragment.*
import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.TransitionInflater
import kotlinx.android.synthetic.main.list_resturents_in_city_fragment.view.*


class ListResturentsInCityFragment : androidx.fragment.app.Fragment() {

    private lateinit var viewModel: MainViewModel
    lateinit var adapter: ListResturentsInCityAdapter
    lateinit var rvRestuarantInCityResults: androidx.recyclerview.widget.RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val v = inflater.inflate(R.layout.list_resturents_in_city_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        rvRestuarantInCityResults=v.rvRestuarantInCityResults

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // if app is running on post lolipop then transitions are supported
            sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
        }


        arguments?.let {
            val args = ListResturentsInCityFragmentArgs.fromBundle(it)

            val city_id = args.cityId

            myutil.showLoadind(v?.context!!)

            viewModel.LvListResturentResult.observe(this, Observer {
                it?.let {
                    if(viewInLandScape==null) {
                        // phone is in portrait mode
                        rvRestuarantInCityResults.layoutManager=LinearLayoutManager(v.context)
                    }
                    else{
                        // phone is in landscape mode
                        rvRestuarantInCityResults.layoutManager=GridLayoutManager(v.context,2)
                    }
                    adapter = ListResturentsInCityAdapter(it)
                    rvRestuarantInCityResults.adapter = adapter
                    myutil.hideLoading()
                }
            })

            viewModel.searchResturentInCity(city_id = city_id)
        }


        return v
    }

}

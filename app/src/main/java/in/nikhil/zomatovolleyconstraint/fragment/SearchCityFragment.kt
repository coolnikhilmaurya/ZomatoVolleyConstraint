package `in`.nikhil.zomatovolleyconstraint.fragment

import `in`.nikhil.zomatovolleyconstraint.viewmodel.MainViewModel
import `in`.nikhil.zomatovolleyconstraint.R
import `in`.nikhil.zomatovolleyconstraint.adapter.CitySearchResultsAdapter
import `in`.nikhil.zomatovolleyconstraint.room.DbCities
import `in`.nikhil.zomatovolleyconstraint.util.myutil
import `in`.nikhil.zomatovolleyconstraint.util.toastlogit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.search_city_fragment.*


class SearchCityFragment : androidx.fragment.app.Fragment() {


    private lateinit var viewModel: MainViewModel
    private lateinit var SearchResultsAdapter:CitySearchResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_city_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)



        viewModel.LvSearchResult.observe(this, Observer {
            it?.let {
                SearchResultsAdapter= CitySearchResultsAdapter(it)
                rvCitySearchResults.layoutManager= androidx.recyclerview.widget.LinearLayoutManager(view.context)
                rvCitySearchResults.adapter=SearchResultsAdapter

                if(it.count()==0){
                    imgNoCityFound.visibility=View.VISIBLE
                    tvrvCitySearchResultsLegend.visibility=View.GONE
                }
                else{
                    tvrvCitySearchResultsLegend.visibility=View.VISIBLE
                    if (viewModel.isInitialLoad)
                         tvrvCitySearchResultsLegend.text=getString(R.string.search_history)
                    else
                        tvrvCitySearchResultsLegend.text=getString(R.string.search_results)
                    imgNoCityFound.visibility=View.GONE
                }


                myutil.hideLoading()
            }
        })

        if (viewModel.isInitialLoad) {
            // populate the list with the previous searched items
            viewModel.populate_searchCities_history()
        }

        btnCitySubmit.setOnClickListener {
            val city=etCity.text.toString().trim()

            if (city.isEmpty()){
                view.context.toastlogit("Enter city first")
                return@setOnClickListener
            }

            myutil.showLoadind(view.context)

            viewModel.searchCities(city)


        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt("key",2)
    }

}


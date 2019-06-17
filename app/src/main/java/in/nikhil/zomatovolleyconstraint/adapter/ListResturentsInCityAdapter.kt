package `in`.nikhil.zomatovolleyconstraint.adapter

import `in`.nikhil.zomatovolleyconstraint.R
import `in`.nikhil.zomatovolleyconstraint.modal.Restaurant
import `in`.nikhil.zomatovolleyconstraint.util.toastlogit
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.android.synthetic.main.resturent_details_item.view.*

class ListResturentsInCityAdapter(var list:List<Restaurant>): androidx.recyclerview.widget.RecyclerView.Adapter<ListResturentsInCityAdapter.myListResturentsInCityViewHolder>() {
    private lateinit var ctx: Context
    private lateinit var glide: RequestManager
    private var clickIntent:Intent = Intent(Intent.ACTION_VIEW)

    override fun onCreateViewHolder(vg: ViewGroup, pos: Int): ListResturentsInCityAdapter.myListResturentsInCityViewHolder {
        val view= LayoutInflater.from(vg.context).inflate(R.layout.resturent_details_item,vg,false)
        ctx=vg.context
        glide=Glide.with(ctx)
        return myListResturentsInCityViewHolder(view)
    }

    override fun getItemCount() = list.count()

    override fun onBindViewHolder(vh: ListResturentsInCityAdapter.myListResturentsInCityViewHolder, pos: Int) {
        vh.tvResturentName.text = list[pos].restaurant.name
        vh.tvRating.text=list[pos].restaurant.user_rating.aggregate_rating
        vh.tvAddress.text=list[pos].restaurant.location.address
        vh.tvCurrency.text="Currency - ${list[pos].restaurant.currency}"

        val myColor = Color.parseColor("#"+list[pos].restaurant.user_rating.rating_color)
        vh.tvRating.setBackgroundColor(myColor)

        glide.load(list[pos].restaurant.featured_image).transition(DrawableTransitionOptions.withCrossFade())
            .into(vh.imgBanner)

        vh.itemView.setOnClickListener {
           /* val action = SearchCityFragmentDirections.actionSearchCityFragmentToListResturentsInCityFragment()
            action.cityId = pos

            Navigation.findNavController(vh.itemView).navigate(action)
            vh.itemView.context.toastlogit("Clicked Item - $pos")
            //      Navigation.createNavigateOnClickListener(R.id.action_searchCityFragment_to_listResturentsInCityFragment,action.arguments)
        */
        // vh.itemView.context.toastlogit("Clicked item at $pos")

            clickIntent.setData(Uri.parse(list[pos].restaurant.url))

            if(clickIntent.resolveActivity(ctx.packageManager)!=null){
                ctx.startActivity(clickIntent)
            }
            else{
                ctx.toastlogit("No URL browser present")
            }
        }
    }

    inner class myListResturentsInCityViewHolder(view: View): androidx.recyclerview.widget.RecyclerView.ViewHolder(view){
        val tvResturentName=view.tvResturentName
        val tvRating=view.tvRating
        val tvAddress=view.tvAddress
        val tvCurrency=view.tvCurrency

        val imgBanner=view.imgResturentBanner

    }
}
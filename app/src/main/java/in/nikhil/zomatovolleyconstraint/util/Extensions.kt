package `in`.nikhil.zomatovolleyconstraint.util

import android.content.Context
import android.util.Log
import android.widget.Toast


    fun String.logit(){
        Log.d("myCustomTag",this);
    }

    fun Context.toastlogit(str:String){
        Toast.makeText(this,str,Toast.LENGTH_LONG).show()
        Log.d("myCustomTag",str);
    }
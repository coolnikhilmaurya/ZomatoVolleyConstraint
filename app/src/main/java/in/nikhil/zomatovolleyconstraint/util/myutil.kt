package `in`.nikhil.zomatovolleyconstraint.util

import `in`.nikhil.zomatovolleyconstraint.R
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Window
import android.view.inputmethod.InputMethodManager


object myutil{

    lateinit var dialogBuilder:AlertDialog.Builder
    lateinit var alertDialog: AlertDialog

    private const val isLogAllowed=true


    fun showLoadind(ctx:Context){

        dialogBuilder = AlertDialog.Builder(ctx)
        val layoutInflator=ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView=layoutInflator.inflate(R.layout.progress_dialog,null)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setView(dialogView)
        alertDialog =
            dialogBuilder.create()
        // magic of transparent background goes here
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.getWindow()?.setBackgroundDrawableResource(
            R.color.myNewTransparent
        );
        alertDialog.show()
    }



    fun hideLoading(){

        try {
            if(alertDialog.isShowing){
                alertDialog.dismiss()
            }
        } catch (e: UninitializedPropertyAccessException) {
           // Log.d("TAG","AlertDialog UninitializedPropertyAccessException")
        }
    }

    fun logIt(message:String){
        if(isLogAllowed){
            Log.d("myCustomLog",message)
        }
    }



}
package com.kareemradwan.stepeer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.kradwan.stepeer.adapter.StepAdapter

/**
 * @author Kareem Radwan
 * @param context to Inflate Views
 * @param list to Create View For Each Model
 * This Class Take List of Order to Build View for Each Order
 */

class OrderAdapter(private val context: Context, list: List<Order>) : StepAdapter<Order>(list) {

    @SuppressLint("InflateParams", "SetTextI18n")
    /**
     * This Method Take Model and Return View
     * @param model  Model of Any Class Implements IStep Abstract Class
     * @return View After Inflate and Set Data
     */
    override fun onCreateView(model: Order): View {
        // Get Root Layout
        val view = LayoutInflater.from(context)
            .inflate(R.layout.order_step, null, false)
        /**
         * inflate all ui elements in View to set Data
         */
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDescriptor = view.findViewById<TextView>(R.id.tvDesc)

        tvTitle.text = model.title
        tvDescriptor.text = "This Sub Title of Cutome View ${model.id}"
        // Return View After Inflating and set Data or Listeners
        return view
    }

}
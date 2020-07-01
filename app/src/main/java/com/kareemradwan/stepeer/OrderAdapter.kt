package com.kareemradwan.stepeer

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.kradwan.stepeer.adapter.StepAdapter


class OrderAdapter(private val context: Context, list: List<Order>) : StepAdapter<Order>(list) {

    @SuppressLint("InflateParams")
    override fun onCreateView(model: Order): View {

        val view = LayoutInflater.from(context)
            .inflate(R.layout.order_step, null, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDescriptor = view.findViewById<TextView>(R.id.tvDesc)

        tvTitle.text = model.title
        tvDescriptor.text = "This Sub Title of Cutome View ${model.id}"

        return view
    }

}
package com.kareemradwan.stepeer


import com.kradwan.stepeer.model.IStep

data class Order(
    val id: Int,
    val title: String,
    var isCheck: Boolean = false
) :
    IStep {


    override fun isChecked(): Boolean = isCheck
}

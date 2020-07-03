package com.kareemradwan.stepeer

import com.kradwan.stepeer.model.IStep
import kotlinx.android.parcel.Parcelize

@Parcelize
class Order(
    val id: Int,
    val title: String,
    private var status: Boolean = false
) : IStep {

    /**
     * You Can Override this Method to Add Some Code
     */
    override fun isChecked(): Boolean {
        // You can put some code here
        return status
    }

    override fun setChecked(isChecked: Boolean) {
        status = isChecked
    }
}

package com.kradwan.stepeer.model

import android.graphics.Color

/**
 * This Class is Deal with Library Colors
 * TODO We Need More Method to Deal with Advance Color
 * for example
 *      - color from resource
 *      - color from #hex format
 */
data class StepColor(var color: Int) {

    companion object {


        val defaultColor = Color.parseColor("#1C8AFF")

        fun default(): HashMap<String, StepColor> {
            return hashMapOf(
                Pair(Constants.COLOR_CHECKED, StepColor(defaultColor)),
                Pair(Constants.COLOR_CHECKED, StepColor(defaultColor))
            )
        }
    }
}
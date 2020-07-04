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
        const val COLOR_CHECKED = "checked_color"
        const val COLOR_UNCHECKED = "unchecked_color"

        // For Number Step View

        const val NUM_TEXT_COLOR_CHECKED = "NUM_TEXt_COLOR_CHECKED"
        const val NUM_TEXT_COLOR_UNCHECKED = "NUM_TEXt_COLOR_UNCHECKED"

        const val STEP_ICON_CHECKED = "STEP_ICON_CHECKED"
        const val STEP_ICON_UNCHECKED = "STEP_ICON_UNCHECKED"

        const val DIVIDER_COLOR_CHECKED = "DIVIDER_COLOR_CHECKED"
        const val DIVIDER_COLOR_UNCHECKED = "DIVIDER_COLOR_UNCHECKED"


        val defaultColor = Color.parseColor("#1C8AFF")

        fun default(): HashMap<String, StepColor> {
            return hashMapOf(
                Pair(COLOR_CHECKED, StepColor(defaultColor)),
                Pair(COLOR_CHECKED, StepColor(defaultColor))
            )
        }
    }
}
package com.kradwan.stepeer.model

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
    }
}
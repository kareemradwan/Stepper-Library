package com.kradwan.stepeer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.graphics.drawable.DrawableCompat
import com.kradwan.stepeer.R
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.StepColor

/**
 * This Class Represent Each Step as View
 * @param colors Get From Your XML [ 'checked_color' , 'unchecked_color']
 */

@SuppressLint("ViewConstructor")
class SingleStepView(
    context: Context,
    private var colors: HashMap<String, StepColor>?
) :
    FrameLayout(context) {

    // Setup All Require Views in Layout
    private lateinit var view: View
    private lateinit var rootView: LinearLayout
    private lateinit var stepInfo: LinearLayout
    private lateinit var stepCheckBox: CheckBox
    private lateinit var stepDivider: View
    private lateinit var stepDividerSolid: View
    private lateinit var stepContentContainer: LinearLayout

    // Setup Default Color if you not set in your XML View `StepperView`
    private var checkBoxSelectedColor: StepColor = StepColor(Color.CYAN)
    private var checkBoxUnSelectedColor: StepColor = StepColor(Color.BLUE)

    /**
     * This Code Call When Construct this Class
     */
    init {
        // Bind Each View With Kotlin Class :)
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        view = inflate(context, R.layout.item_step, this)
        rootView = view.findViewById(R.id.rootSingleStep)
        stepInfo = view.findViewById(R.id.stepInfo)
        stepCheckBox = view.findViewById(R.id.stepCheckBok)
        stepDivider = view.findViewById(R.id.stepDivider)
        stepDividerSolid = view.findViewById(R.id.stepDividerSolid)
        stepContentContainer = view.findViewById(R.id.stepContentContainer)

        /**
         * Check if you Override Default Colors
         */
        if (colors != null) {
            // Check if you override `Checked` state Color
            if (colors?.contains(StepColor.COLOR_CHECKED)!!) {
                // Change Default Color to your Custom Color
                checkBoxSelectedColor = colors?.get(StepColor.COLOR_CHECKED)!!
            }

            /**
             *  Check if you override `unChecked` state Color
             *  because UnCheck Color is Default and initial State you need Override View Color
             */
            if (colors?.contains(StepColor.COLOR_UNCHECKED)!!) {
                checkBoxUnSelectedColor = colors?.get(StepColor.COLOR_UNCHECKED)!!
                stepDivider.setBackgroundColor(checkBoxUnSelectedColor.color)
                stepDividerSolid.setBackgroundColor(checkBoxUnSelectedColor.color)

                /**
                 * Support sdk 15 and higher than it
                 * Change Tint of Checkbox to checkBoxUnSelectedColor
                 */
                val checkDrawable =
                    DrawableCompat.wrap(getDrawable(context, R.drawable.custom_checkbox)!!)
                DrawableCompat.setTint(checkDrawable, checkBoxUnSelectedColor.color)
                stepCheckBox.buttonDrawable = checkDrawable
            }
        }
    }

    /**
     * Here We Bind Actual Data to View
     * @param model your Data Class
     * @param view Generate View from Adapter
     * @param last to hide last Divider
     */
    fun <T : IStep> setModel(model: T, view: View, last: Boolean) {
        // Set Init State of Checkbox
        stepCheckBox.isChecked = model.isChecked()
        stepDivider.visibility = if (last) View.GONE else View.VISIBLE

        /**
         * The Code inside post method call when
         * `stepContentContainer` finish Draw in Screen
         * to can add View in it  Successfully
         * and if you need get Width or height of View
         */
        stepContentContainer.post {
            // Try to Avoid Exception Cases
            try {
                // Add Custom View in Step Layout
                stepContentContainer.addView(view)
            } catch (ex: Exception) {
                Log.e("STEPPER", ex.localizedMessage!!)
            }

            /**
             * Check if Step is Done
             * to Change Default State to Checked State
             */
            if (model.isChecked()) {
                // We Pass here if This Model is Last or Not
                selectAsDone()
            }

            if (last) {
                // The LayoutParams form FrameLayout Class
                stepDivider.layoutParams = LayoutParams(0, 0)
                stepDividerSolid.layoutParams = LayoutParams(0, 0)
            }
        }
    }

    /**
     * this private method called when step is be Done
     * @param last to check if
     *
     * Deprecated in 2 Jue 2020
     * No We Make one if Condition
     */
    @Deprecated("You Can Use `SelectAsDone() without parameters`", ReplaceWith("selectAsDone()"))
    fun selectAsDone(last: Boolean) {
        selectAsDone()
    }

    /**
     * This Method Change UI State From UnChecked to Checked
     */
    fun selectAsDone() {

        stepCheckBox.isChecked = true

        /**
         * Support sdk 15 and higher than it
         * Change Tint of Checkbox to checkBoxSelectedColor
         */
        val checkDrawable = DrawableCompat.wrap(getDrawable(context, R.drawable.custom_checkbox)!!)
        DrawableCompat.setTint(checkDrawable, checkBoxSelectedColor.color)
        stepCheckBox.buttonDrawable = checkDrawable

        stepDividerSolid.setBackgroundColor(checkBoxSelectedColor.color)
        stepDividerSolid.visibility = View.VISIBLE
        val move = AnimationUtils.loadAnimation(context, R.anim.move)
        stepDividerSolid.animation = move
    }

}
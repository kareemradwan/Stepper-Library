package com.kradwan.stepeer.view.verticalStepper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import com.kradwan.stepeer.R
import com.kradwan.stepeer.model.*

/**
 * This Class Represent Each Step as View
 * @param colors Get From Your XML [ 'checked_color' , 'unchecked_color']
 */

@SuppressLint("ViewConstructor")
internal class SingleStepView(
    context: Context,
    private var colors: HashMap<String, StepColor>,
    private var icons: HashMap<String, StepDrawable>
) :
    FrameLayout(context) {

    // Setup All Require Views in Layout
    private lateinit var view: View
    private lateinit var rootView: LinearLayout
    private lateinit var stepInfo: LinearLayout
    private lateinit var stepCheckBox: ImageView
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


        if (icons[StepDrawable.DRAWABLE_UNCHECKED] != null) {
            stepCheckBox.setImageDrawable(icons[StepDrawable.DRAWABLE_UNCHECKED]?.drawable)
        }
        /**
         * Check if you Override Default Colors
         */
        // Check if you override `Checked` state Color
        // Change Default Color to your Custom Color
        checkBoxSelectedColor = colors[Constants.COLOR_CHECKED]!!

        /**
         *  Check if you override `unChecked` state Color
         *  because UnCheck Color is Default and initial State you need Override View Color
         */
        checkBoxUnSelectedColor = colors[Constants.COLOR_UNCHECKED]!!
        stepDivider.setBackgroundColor(checkBoxUnSelectedColor.color)
        stepDividerSolid.setBackgroundColor(checkBoxUnSelectedColor.color)

        /**
         * Support sdk 15 and higher than it
         * Change Tint of Checkbox to checkBoxUnSelectedColor
         */

    }

    /**
     * Here We Bind Actual Data to View
     * @param model your Data Class
     * @param view Generate View from Adapter
     * @param last to hide last Divider
     */
    fun <T : IStep> setModel(model: T, view: View, last: Boolean, animated: Boolean) {
        // Set Init State of Checkbox
//        stepCheckBox.isChecked = model.isChecked()
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
                selectAsDone(animated)
            }

            if (last) {
                // The LayoutParams form FrameLayout Class
                stepDivider.layoutParams = LayoutParams(0, 0)
                stepDividerSolid.layoutParams = LayoutParams(0, 0)
            }
        }
    }


    /**
     * This Method Change UI State From UnChecked to Checked
     */
    fun selectAsDone(animated: Boolean) {
        stepCheckBox.setImageDrawable(icons[StepDrawable.DRAWABLE_CHECKED]?.drawable)

        /**
         * Support sdk 15 and higher than it
         * Change Tint of Checkbox to checkBoxSelectedColor
         */

        if (animated) {
            stepDividerSolid.setBackgroundColor(colors[Constants.COLOR_CHECKED]!!.color)
            stepDividerSolid.visibility = View.VISIBLE
            val move = AnimationUtils.loadAnimation(context, R.anim.move)
            stepDividerSolid.animation = move
        } else {
            stepDividerSolid.setBackgroundColor(checkBoxSelectedColor.color)
            stepDividerSolid.visibility = View.VISIBLE
        }
    }

}
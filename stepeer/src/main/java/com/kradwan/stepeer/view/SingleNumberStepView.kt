package com.kradwan.stepeer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import com.kradwan.stepeer.R
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.StepColor
import com.kradwan.stepeer.model.StepDrawable
import com.kradwan.stepeer.model.StepResource

/**
 * This Class Represent Each Step as View
 * @param colors Get From Your XML [ 'checked_color' , 'unchecked_color']
 */

@SuppressLint("ViewConstructor")
class SingleNumberStepView(
    context: Context,
    private var colors: HashMap<String, StepColor>,
    private var icons: HashMap<String, StepDrawable>
) :
    FrameLayout(context) {

    // Setup All Require Views in Layout
    private lateinit var view: View
    private lateinit var rootView: LinearLayout
    private lateinit var stepInfo: LinearLayout

    private lateinit var stepCheckBox: FrameLayout
    private lateinit var stepLabel: TextView
    //    private lateinit var stepCheckBox: CheckBox
    private lateinit var stepDivider: View
    private lateinit var stepDividerSolid: View
    private lateinit var stepContentContainer: LinearLayout


    /**
     * This Code Call When Construct this Class
     */
    init {
        // Bind Each View With Kotlin Class :)
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        view = inflate(context, R.layout.item_num_step, this)
        rootView = view.findViewById(R.id.rootSingleStep)
        stepInfo = view.findViewById(R.id.stepInfo)
        stepCheckBox = view.findViewById(R.id.stepCheckBok)
        stepLabel = view.findViewById(R.id.tvStepLabel)
        stepDivider = view.findViewById(R.id.stepDivider)
        stepDividerSolid = view.findViewById(R.id.stepDividerSolid)
        stepContentContainer = view.findViewById(R.id.stepContentContainer)

        stepCheckBox.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_24dp)

//        if (icons[StepDrawable.DRAWABLE_UNCHECKED] != null) {
//            stepCheckBox.setImageDrawable(icons[StepDrawable.DRAWABLE_UNCHECKED]?.drawable)
//        }
        /**
         * Check if you Override Default Colors
         */
        // Check if you override `Checked` state Color
        // Change Default Color to your Custom Color

        /**
         *  Check if you override `unChecked` state Color
         *  because UnCheck Color is Default and initial State you need Override View Color
         */
        stepDivider.setBackgroundColor(colors[StepColor.DIVIDER_COLOR_UNCHECKED]!!.color)
        stepDividerSolid.setBackgroundColor(colors[StepColor.DIVIDER_COLOR_UNCHECKED]!!.color)

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
    fun <T : IStep> setModel(model: T, view: View, last: Boolean, animated: Boolean, label: Int) {
        // Set Init State of Checkbox
        stepLabel.setTextColor(colors[StepColor.COLOR_UNCHECKED]!!.color)

//        stepCheckBox.setBackgroundResource(R.drawable.ic_radio_button_unchecked_black_24dp)
        stepDivider.visibility = if (last) View.GONE else View.VISIBLE
//        stepCheckBox.setBackgroundResource(icons[StepDrawable.DRAWABLE_UNCHECKED]!!.id)
//        stepCheckBox.background = icons[StepColor.STEP_ICON_CHECKED]!!.drawable

        Log.d("DDDD", " Label ${label}")
        stepLabel.text = "$label"
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
     * This Method Change UI State From UnChecked to
     * Checked
     */
    fun selectAsDone(animated: Boolean) {

//        stepCheckBox.setBackgroundResource(icons[StepDrawable.DRAWABLE_CHECKED]!!.id)
//        stepCheckBox.background = icons[StepColor.STEP_ICON_UNCHECKED]!!.drawable

//        stepCheckBox.background =
//       stepCheckBox.background
//        stepCheckBox.setBackgroundResource(R.drawable.bg_rounded_fill)
        stepLabel.setTextColor(colors[StepColor.COLOR_CHECKED]!!.color)

        /**
         * Support sdk 15 and higher than it
         * Change Tint of Checkbox to checkBoxSelectedColor
         */

        if (animated) {
            stepDividerSolid.setBackgroundColor(colors[StepColor.COLOR_CHECKED]!!.color)
            stepDividerSolid.visibility = View.VISIBLE
            val move = AnimationUtils.loadAnimation(context, R.anim.move)
            stepDividerSolid.animation = move
        } else {
            stepDividerSolid.setBackgroundColor(colors[StepColor.DIVIDER_COLOR_CHECKED]!!.color)
            stepDividerSolid.visibility = View.VISIBLE
        }
    }

}
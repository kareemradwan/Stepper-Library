package com.kradwan.stepeer.view.numberStepper

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.kradwan.stepeer.R
import com.kradwan.stepeer.StepperState
import com.kradwan.stepeer.adapter.StepAdapter
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.Constants
import com.kradwan.stepeer.model.StepColor
import com.kradwan.stepeer.model.StepDrawable
import java.lang.Exception

/**
 * This Class Represent View in XML
 * @param context passed to FrameLayout (parent)
 * @param attrs passed to FrameLayout (parent)
 */
class NumberStepperView(context: Context, private val attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    // The Root View
    private lateinit var view: View
    private lateinit var containerSteeper: LinearLayout
    // Controller For Finish Step
    private var callback: SteeperHandler? = null

    // Index to Current Step to Save State
    private var currentStep: Int = 0
    // Colors of `unChecked` and `Checked` state
    private var colors = StepColor.default()
    private var icons = HashMap<String, StepDrawable>()
    // Adapter of Stepper
    private lateinit var mAdapter: StepAdapter<IStep>

    init {
        initViews()
    }

    /**
     * Called When App need to reCreate View for Example `rotate`
     * To Save State of View
     */
    override fun onSaveInstanceState(): Parcelable? {
        val superState = super.onSaveInstanceState()
        return StepperState(superState, mAdapter.models)
    }

    /**
     * Called When App need to reCreate Activity After rotate for example etc.
     */
    override fun onRestoreInstanceState(state: Parcelable?) {
        // cast state to Custom State [ StepperState]
        val myState = state as? StepperState
        // Pass the State to Parent
        super.onRestoreInstanceState(myState?.superSaveState)
        // Get The List Of Steps if null [ First Time Open] Assign Empty List
        mAdapter.models = myState?.steps ?: listOf()
        // Pass Adapter to Build UI
        setAdapter(mAdapter, false)
        //redraw
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        // Inflate Root View From XML
        view = inflate(context, R.layout.steeper_layout, this)
        // Get The Root Element in `steeper_layout`
        containerSteeper = view.findViewById(R.id.rootSteeper)
        // Check if attrs not null
        if (attrs != null) {
            // Declare Default Color if The Developer Not Override the
            // 'unchecked_color' , 'checked_color';
            // Get the xml style attribute form view
            val style =
                context.theme.obtainStyledAttributes(attrs, R.styleable.NumberStepperView, 0, 0)
            /**
             * get First Value [ First from attr file not in XML Order ]
             * In our Example the First attr is `check`
             */
            val numTextColorChecked =
                style.getColor(R.styleable.NumberStepperView_num_text_color_checked, -1)
            val numTextColorUnChecked =
                style.getColor(R.styleable.NumberStepperView_num_text_color_unchecked, -1)
            val dividerColorChecked =
                style.getColor(R.styleable.NumberStepperView_divider_color_checked, -1)
            val dividerColorUnChecked =
                style.getColor(R.styleable.NumberStepperView_divider_color_unchecked, -1)


            colors[Constants.NUM_TEXT_COLOR_CHECKED] =
                if (numTextColorChecked != -1) StepColor(numTextColorChecked) else StepColor(
                    Color.parseColor(
                        "#456333"
                    )
                )

            colors[Constants.NUM_TEXT_COLOR_UNCHECKED] =
                if (numTextColorUnChecked != -1) StepColor(numTextColorUnChecked) else StepColor(
                    Color.parseColor("#456333")
                )

            colors[Constants.DIVIDER_COLOR_CHECKED] =
                if (dividerColorChecked != -1) StepColor(dividerColorChecked) else StepColor(
                    Color.parseColor(
                        "#456333"
                    )
                )
            colors[Constants.DIVIDER_COLOR_UNCHECKED] =
                if (dividerColorUnChecked != -1) StepColor(dividerColorUnChecked) else StepColor(
                    Color.parseColor("#456333")
                )


            val stepIconChecked = style.getDrawable(R.styleable.NumberStepperView_step_icon_checked)
            val stepIconUnChecked =
                style.getDrawable(R.styleable.NumberStepperView_step_icon_unchecked)
//
            icons[Constants.STEP_ICON_CHECKED] =
                if (stepIconChecked == null) StepDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_rounded_fill
                    )!!
                ) else StepDrawable(stepIconChecked)

            icons[Constants.STEP_ICON_UNCHECKED] =
                if (stepIconUnChecked == null) StepDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_radio_button_unchecked_black_24dp
                    )!!
                ) else StepDrawable(stepIconUnChecked)

        }
    }

    /**
     * Assign Adapter To View To Start Create Step View
     * and Show in screen
     * @param adapter Any Class Inherited [StepAdapter]
     */
    fun <T : IStep> setAdapter(adapter: StepAdapter<T>, animated: Boolean = true) {
        try {
            // because we need assign adapter to global variable
            // we need Cast generic Type to IStep
            this.mAdapter = adapter as StepAdapter<IStep>
            /**
             * We need Remove all View in `containerSteeper`
             * because when restore State `onRestoreInstanceState` to duplicate Views
             */
            currentStep = 0
            containerSteeper.removeAllViews()
            var index = 1
            // Iterate in List of IStep and Build View for Each Model From Adapter
            mAdapter.models.forEach {

                // check if step is `Checked` by default
                if (it.isChecked()) {
                    // Increase Indicator of Where Stepper is Stopped
                    currentStep++
                }
                // View of Single Step Without Any Actual Data
                val step =
                    SingleNumberStepView(
                        view.context,
                        colors,
                        icons
                    )
                // Assign Actual Data to Step View
                step.setModel(
                    it,
                    adapter.onCreateView(it),
                    adapter.models.last() == it,
                    animated,
                    index++
                )
                // Add Step in Container of Steps
                containerSteeper.addView(step)
            }
        } catch (ex: Exception) {
            callback?.onError(ex.localizedMessage)
        }
    }

    /**
     * to Assign Callback Handler to Stepper
     */
    fun setController(controller: SteeperHandler) {
        callback = controller
    }

    /**
     *  This Method work in Change the UI state from UnChecked to Checked
     *  @param index of model in list of adapter
     */
    private fun selectAsDone(index: Int) {
        val stepView = containerSteeper[index] as SingleNumberStepView
        stepView.selectAsDone(true)
    }

    fun nextStep() {
        // Check if Stepper is Finished or not
        if (currentStep != containerSteeper.childCount) {
            // Change the ui form UnChecked to Checked
            selectAsDone(currentStep)
            // Change the boolean Variable in list of Adapter
            mAdapter.models[currentStep].setChecked(true)
            // Increase Indicator to Next Step
            currentStep += 1
        }
        // Check if we Arrive the End of Stepper
        // to notify the callback
        if (currentStep == containerSteeper.childCount) {
            callback?.onFinish()
        }
    }

    /**
     * Interface represent the handler of actions
     * By This Interface you Can Pass Notification to Activity
     * When Step reach to last Step
     * or if get any error
     */
    interface SteeperHandler {
        fun onFinish()

        fun onError(msg: String?)
    }
}
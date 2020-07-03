package com.kradwan.stepeer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.get
import com.kradwan.stepeer.R
import com.kradwan.stepeer.StepperState
import com.kradwan.stepeer.adapter.StepAdapter
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.StepColor
import java.lang.Exception

/**
 * This Class Represent View in XML
 * @param context passed to FrameLayout (parent)
 * @param attrs passed to FrameLayout (parent)
 */
class SteeperView(context: Context, private val attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    // The Root View
    private lateinit var view: View
    private lateinit var containerSteeper: LinearLayout
    // Controller For Finish Step
    private var callback: SteeperHandler? = null

    // Index to Current Step to Save State
    private var currentStep: Int = 0
    // Colors of `unChecked` and `Checked` state
    private var colors = HashMap<String, StepColor>()
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
        setAdapter(mAdapter)
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
            val defaultColor = StepColor(Color.parseColor("#1C8AFF"))
            // Get the xml style attribute form view
            val style = context.theme.obtainStyledAttributes(attrs, R.styleable.SteeperView, 0, 0)
            /**
             * get First Value [ First from attr file not in XML Order ]
             * In our Example the First attr is `check`
             */
            val checkColor = style.getColor(0, -1)
            // In our Example the First attr is `uncheck`
            val unCheckColor = style.getColor(1, -1)
            /**
             * Short IF To Check if Override Values of [checked , unchecked color]
             */
            colors[StepColor.COLOR_CHECKED] =
                if (checkColor != -1) StepColor(checkColor) else defaultColor
            colors[StepColor.COLOR_UNCHECKED] =
                if (unCheckColor != -1) StepColor(unCheckColor) else defaultColor
        }
    }

    /**
     * Assign Adapter To View To Start Create Step View
     * and Show in screen
     * @param adapter Any Class Inherited [StepAdapter]
     */
    fun <T : IStep> setAdapter(adapter: StepAdapter<T>) {
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
            // Iterate in List of IStep and Build View for Each Model From Adapter
            mAdapter.models.forEach {
                // check if step is `Checked` by default
                if (it.isChecked()) {
                    // Increase Indicator of Where Stepper is Stopped
                    currentStep++
                }
                // View of Single Step Without Any Actual Data
                val step = SingleStepView(view.context, colors)
                // Assign Actual Data to Step View
                step.setModel(it, adapter.onCreateView(it), adapter.models.last() == it)
                // Add Step in Container of Steps
                containerSteeper.addView(step)
            }
        } catch (ex: Exception) {
            // Handle Any Error and return back to Activity
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
        val stepView = containerSteeper[index] as SingleStepView
        stepView.selectAsDone()
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
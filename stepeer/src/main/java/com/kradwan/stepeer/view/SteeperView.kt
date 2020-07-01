package com.kradwan.stepeer.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.core.view.get
import com.kradwan.stepeer.R
import com.kradwan.stepeer.adapter.StepAdapter
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.StepColor

class SteeperView(context: Context, private val attrs: AttributeSet?) :
    FrameLayout(context, attrs) {

    private lateinit var view: View
    private lateinit var containerSteeper: LinearLayout
    // Controller For Finish Step
    private var callback: SteeperHandler? = null

    private var currentStep: Int = 0
    private var colors = HashMap<String, StepColor>()

    init {
        initViews()
    }

    @SuppressLint("ResourceType")
    private fun initViews() {
        view = inflate(context, R.layout.steeper_layout, this)
        containerSteeper = view.findViewById(R.id.rootSteeper)

        if (attrs != null) {
            val defaultColor = StepColor(Color.parseColor("#1C8AFF"))
            val style = context.theme.obtainStyledAttributes(attrs, R.styleable.SteeperView, 0, 0)
            val checkColor = style.getColor(0, -1)
            val unCheckColor = style.getColor(1, -1)
            colors[StepColor.COLOR_CHECKED] =
                if (checkColor != -1) StepColor(checkColor) else defaultColor
            colors[StepColor.COLOR_UNCHECKED] =
                if (unCheckColor != -1) StepColor(unCheckColor) else defaultColor
        }
    }

    fun <T : IStep> setAdapter(adapter: StepAdapter<T>) {
        adapter.models.forEach {
            val view = adapter.onCreateView(it)

            if (it.isChecked()) {
                currentStep++
            }
            val step = SingleStepView(view.context, colors)
            step.setModel(it, view, adapter.models.last() == it)
            containerSteeper.addView(step)
        }
    }

    fun setController(controller: SteeperHandler) {
        callback = controller
    }

    private fun selectAsDone(index: Int) {
        val stepView = containerSteeper[index] as SingleStepView
        stepView.selectAsDone(index == containerSteeper.childCount - 1)
    }

    fun nextStep() {
        if (currentStep != containerSteeper.childCount) {
            selectAsDone(currentStep++)
        }

        if (currentStep == containerSteeper.childCount) {
            callback?.onFinish()
        }
    }

    interface SteeperHandler {
        fun onFinish()
    }
}
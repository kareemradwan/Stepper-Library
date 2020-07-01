package com.kradwan.stepeer.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kradwan.stepeer.R
import com.kradwan.stepeer.model.IStep
import com.kradwan.stepeer.model.StepColor


@SuppressLint("ViewConstructor")
class SingleStepView(context: Context, private var colors: HashMap<String, StepColor>?) :
    FrameLayout(context) {

    private lateinit var view: View
    private lateinit var rootView: LinearLayout
    private lateinit var stepInfo: LinearLayout
    private lateinit var stepCheckBox: CheckBox
    private lateinit var stepDivider: View
    private lateinit var stepDividerSolid: View
    private lateinit var stepContentContainer: LinearLayout


    private var checkBoxSelectedColor: StepColor = StepColor(Color.CYAN)
    private var checkBoxUnSelectedColor: StepColor = StepColor(Color.BLUE)

    init {
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



        if (colors != null) {
            if (colors?.contains("checked_color")!!) {
                checkBoxSelectedColor = colors?.get("checked_color")!!
            }

            if (colors?.contains("unchecked_color")!!) {
                checkBoxUnSelectedColor = colors?.get("unchecked_color")!!
                stepDivider.setBackgroundColor(checkBoxUnSelectedColor.color)
                stepDividerSolid.setBackgroundColor(checkBoxUnSelectedColor.color)
                stepCheckBox.buttonTintList = ColorStateList.valueOf(checkBoxUnSelectedColor.color)
            }
        }
    }

    fun <T : IStep> setModel(model: T, view: View, last: Boolean) {
        stepCheckBox.isChecked = model.isChecked()
        stepContentContainer.post {
            try {
                stepContentContainer.addView(view)
            } catch (ex: Exception) {
            }
            if (model.isChecked())
                selectAsDone(last)

            stepDivider.visibility = if (last) View.GONE else View.VISIBLE
        }
    }

    fun selectAsDone(last: Boolean) {
        stepCheckBox.isChecked = true
        stepCheckBox.buttonTintList = ColorStateList.valueOf(checkBoxSelectedColor.color)

        if (!last) {
            stepDividerSolid.setBackgroundColor(checkBoxSelectedColor.color)
            stepDividerSolid.visibility = View.VISIBLE
            val move = AnimationUtils.loadAnimation(context, R.anim.move)
            stepDividerSolid.animation = move
        }
    }
}
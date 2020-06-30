package com.kradwan.stepeer.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.kradwan.stepeer.R
import com.kradwan.stepeer.model.IStep
import java.lang.Exception

class SingleStepView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {


    private lateinit var view: View
    private lateinit var rootView: LinearLayout
    private lateinit var stepInfo: LinearLayout
    private lateinit var stepCheckBox: CheckBox
    private lateinit var stepDivider: View
    private lateinit var stepDividerSolid: View
    private lateinit var stepContentContainer: LinearLayout

    init {
        initViews()
    }

    private fun initViews() {
        view = inflate(context, R.layout.item_step, this)
        rootView = view.findViewById(R.id.rootSingleStep)
        stepInfo = view.findViewById(R.id.stepInfo)
        stepCheckBox = view.findViewById(R.id.stepCheckBok)
        stepDivider = view.findViewById(R.id.stepDivider)
        stepDividerSolid = view.findViewById(R.id.stepDividerSolid)
        stepContentContainer = view.findViewById(R.id.stepContentContainer)
    }

    fun <T : IStep> setModel(model: T, view: View, last: Boolean) {

        stepCheckBox.isChecked = model.isChecked()
        stepContentContainer.post {

            if (model.isChecked())
                selectAsDone(last)


            try {
                stepContentContainer.addView(view)
            } catch (ex: Exception) {
            }


            if (last)
                stepDivider.visibility = View.GONE

        }

    }

    fun selectAsDone(last: Boolean) {
        stepCheckBox.isChecked = true
        if (!last) {
            stepDividerSolid.visibility = View.VISIBLE
            val move = AnimationUtils.loadAnimation(context, R.anim.move)
            stepDividerSolid.animation = move
        }
    }
}
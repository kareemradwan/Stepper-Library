package com.kradwan.stepeer

import android.os.Parcelable
import android.view.View
import com.kradwan.stepeer.model.IStep
import kotlinx.android.parcel.Parcelize

/**
 * This Class is Saver of Stepper View
 */
@Parcelize
class StepperState(val superSaveState: Parcelable?, val steps: List<IStep>) :
    View.BaseSavedState(superSaveState), Parcelable
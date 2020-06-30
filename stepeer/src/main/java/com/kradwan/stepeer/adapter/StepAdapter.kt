package com.kradwan.stepeer.adapter

import android.view.View
import com.kradwan.stepeer.model.IStep

abstract class StepAdapter<T : IStep>(var models: List<T>)  {
    abstract fun onCreateView(model: T): View
}
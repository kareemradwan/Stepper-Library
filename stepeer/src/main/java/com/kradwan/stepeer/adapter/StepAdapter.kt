package com.kradwan.stepeer.adapter

import android.view.View
import com.kradwan.stepeer.model.IStep

/**
 * This Abstract Class Take List Of Class Inherited `IStep` abstract Class
 * The Main Feature of This Adapter
 * Create View Foreach Model in `models`
 *
 * This Class is Require for `StepperView` to Work
 */
abstract class StepAdapter<T : IStep>(var models: List<T>) {

    /**
     * you must be Implement this method when create your Custom adapter
     * @param model Data Class
     * @return View with Actual Data from `model`
     */
    abstract fun onCreateView(model: T): View
}
package com.kradwan.stepeer.model

import android.os.Parcelable

/**
 * Any Model Must Be Implements This Abstract Class
 *
 * The Main Feature of This Class
 *      - Know Any Step is Checked or Not
 *      - Make Step as Checked or Not
 *
 * The Class you need To Create Must be Annotated with
 * `@Parcelize` Annotation to be save state when Device Rotate for example
 */
abstract class IStep(private var isChecked: Boolean = false) : Parcelable {

    /**
     * Getter Method for Checked Variable
     * you can Override this Method to run custom code
     */
    open fun isChecked(): Boolean = isChecked

    /**
     * Setter Method For Checked Variable
     * you can Override this Method to run custom code
     */
    open fun setChecked(isChecked: Boolean) {
        this.isChecked = isChecked
    }
}
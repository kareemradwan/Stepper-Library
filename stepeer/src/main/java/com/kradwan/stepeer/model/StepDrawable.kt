package com.kradwan.stepeer.model

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.kradwan.stepeer.R

class StepDrawable(var drawable: Drawable) {

    companion object {

        const val DRAWABLE_CHECKED = "checked_drawable"
        const val DRAWABLE_UNCHECKED = "unchecked_drawable"


        private val defaultIconId = R.drawable.ic_star_black_24dp

        fun default(context: Context): HashMap<String, StepDrawable> {
            val drawable = ContextCompat.getDrawable(context, defaultIconId)!!
            return hashMapOf(
                Pair(DRAWABLE_CHECKED, StepDrawable(drawable)),
                Pair(DRAWABLE_UNCHECKED, StepDrawable(drawable))
            )
        }

        fun  fromId(context: Context , id: Int)  : StepDrawable{
            return StepDrawable(ContextCompat.getDrawable(context , id)!!)
        }
    }
}

class StepResource(var id: Int)
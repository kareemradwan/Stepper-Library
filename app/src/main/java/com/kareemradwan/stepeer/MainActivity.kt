package com.kareemradwan.stepeer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kradwan.stepeer.view.verticalStepper.SteeperView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SteeperView.SteeperHandler {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val order1 = Order(1, "Step1", true)
        order1.setChecked(true)
        val adapter = OrderAdapter(
            this, listOf(
                order1,
                Order(2, "Step2"),
                Order(3, "Step3"),
                Order(4, "Step4")
            )
        )
        steeper.setAdapter(adapter)
        steeper2.setAdapter(adapter)
        // Assign Controller
//        steeper.setController(this)
        nextStep.setOnClickListener {
            steeper.nextStep()
            steeper2.nextStep()
        }

    }

    override fun onFinish() {
        Toast.makeText(this, "Finish Steps", Toast.LENGTH_LONG).show()
    }

    override fun onError(msg: String?) {
        Toast.makeText(this, " Error $msg", Toast.LENGTH_LONG).show()
    }
}

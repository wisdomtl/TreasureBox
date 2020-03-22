package taylor.com.treasurebox

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import taylor.com.animation_dsl.animSet

class MainActivity : AppCompatActivity() {
    private var lastX: Int = 0
    private var lastY: Int = 0

    private val moveAnim by lazy {
        animSet {
            objectAnim {
                target = tv
                translationY = floatArrayOf(0f,100f)
                duration = 300
                interpolator = LinearInterpolator()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChange?.setOnClickListener {
            //hide red point of the way of setting radius to zero
            redPoint?.setValue(R.id.tv, RedPointTreasure.TYPE_RADIUS, 0f)
        }

        btnChangePosition?.setOnClickListener {
            redPoint?.setValue(R.id.btn, RedPointTreasure.TYPE_OFFSET_X, 20f)
        }

//        tv?.setOnTouchListener { v, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    lastX = event.rawX.toInt()
//                    lastY = event.rawY.toInt()
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    var dx = event.rawX - lastX
//                    var dy = event.rawY - lastY
//                    tv.translationX = dx
//                    tv.translationY = dy
//                }
//            }
//            true
//        }
        tv.setOnClickListener{
            moveAnim.start()
        }

    }
}

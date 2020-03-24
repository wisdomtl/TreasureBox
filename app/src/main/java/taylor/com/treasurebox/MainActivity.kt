package taylor.com.treasurebox

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import taylor.com.animation_dsl.animSet

class MainActivity : AppCompatActivity() {
    private var lastX: Int = 0
    private var lastY: Int = 0
    private val SCALE_UNSELECTED = 0.65f
    private val SCALE_SELECTED = 1f

    private val moveAnim by lazy {
        animSet {
            objectAnim {
                target = tv
                translationY = floatArrayOf(0f, 100f)
                duration = 300
                interpolator = LinearInterpolator()
            } before objectAnim {
                target = tv
                scaleX = floatArrayOf(1.0f, 0.5f)
                duration = 300
                interpolator = LinearInterpolator()
            }
        }
    }

    private val highlightAnim by lazy {
        animSet {
            anim {
                values = floatArrayOf(SCALE_SELECTED, SCALE_UNSELECTED)
                duration = 70
                interpolator = AccelerateDecelerateInterpolator()
                action = { value ->
                    tvRecommend.scaleX = value as Float
                    tvRecommend.scaleY = value
                    tvConcern.scaleX = (SCALE_SELECTED + SCALE_UNSELECTED) - value
                    tvConcern.scaleY = (SCALE_SELECTED + SCALE_UNSELECTED) - value
                }
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

        tvRecommend.post {
            tvRecommend.pivotX = (tvRecommend.width / 2).toFloat()
            tvConcern.pivotX = (tvConcern.width / 2).toFloat()
            tvRecommend.pivotY = tvRecommend.height.toFloat()
            tvConcern.pivotY = tvConcern.height.toFloat()
        }

        btnChangePosition?.setOnClickListener {
            redPoint?.setValue(R.id.btn, RedPointTreasure.TYPE_OFFSET_X, 20f)
        }

        tv.setOnClickListener {
            moveAnim.start()
        }

        tvRecommend.setOnClickListener { highlightAnim.reverse() }
        tvConcern.setOnClickListener { highlightAnim.start() }
    }
}

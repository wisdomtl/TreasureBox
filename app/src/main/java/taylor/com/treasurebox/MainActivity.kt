package taylor.com.treasurebox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnChange?.setOnClickListener {
            //hide red point of the way of setting radius to zero
            redPoint?.setValue(R.id.tv, RedPointTreasure.TYPE_RADIUS, 0f)
        }

        btnChangePosition?.setOnClickListener {
            redPoint?.setValue(R.id.btn,RedPointTreasure.TYPE_OFFSET_X,20f)
        }
    }
}

package taylor.com.treasurebox

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import test.taylor.com.taylorcode.ui.custom_view.treasure_box.Treasure
import test.taylor.com.taylorcode.ui.custom_view.treasure_box.TreasureBox

/**
 * a [Treasure] which shows red point along it's reference sibling views
 */
class RedPointTreasure @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    Treasure(context, attrs, defStyleAttr) {
    companion object {
        @JvmStatic
        val TYPE_RADIUS = "radius"
        @JvmStatic
        val TYPE_OFFSET_X = "offset_x"
        @JvmStatic
        val TYPE_OFFSET_Y = "offset_y"
    }

    private val DEFAULT_RADIUS = 5F
    private lateinit var offsetXs: MutableList<Float>
    private lateinit var offsetYs: MutableList<Float>
    private lateinit var radiuses: MutableList<Float>
    private var bgPaint: Paint = Paint()

    init {
        initPaint()
    }

    private fun initPaint() {
        bgPaint.apply {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = Color.parseColor("#ff0000")
        }
    }

    override fun readAttrs(attributeSet: AttributeSet?) {
        super.readAttrs(attributeSet)
        attributeSet?.let { attrs ->
            context.obtainStyledAttributes(attrs, R.styleable.RedPointTreasure)?.let {
                divideRadiuses(it.getString(R.styleable.RedPointTreasure_reference_radius))
                dividerOffsets(
                    it.getString(R.styleable.RedPointTreasure_reference_offsetX),
                    it.getString(R.styleable.RedPointTreasure_reference_offsetY)
                )
                it.recycle()
            }
        }
    }

    override fun drawTreasure(treasureBox: TreasureBox, canvas: Canvas?) {
        ids.forEachIndexed { index, id ->
            treasureBox.findViewById<View>(id)?.let { v ->
                val cx = v.right + offsetXs.getOrElse(index) { 0F }.dp2px()
                val cy = v.top + offsetYs.getOrElse(index) { 0F }.dp2px()
                val radius = radiuses.getOrElse(index) { DEFAULT_RADIUS }.dp2px()
                canvas?.drawCircle(cx, cy, radius, bgPaint)
            }
        }
    }

    private fun dividerOffsets(offsetXString: String?, offsetYString: String?) {
        offsetXs = mutableListOf()
        offsetYs = mutableListOf()
        offsetXString?.split(",")?.forEach { offset -> offsetXs.add(offset.trim().toFloat()) }
        offsetYString?.split(",")?.forEach { offset -> offsetYs.add(offset.trim().toFloat()) }
    }

    private fun divideRadiuses(radiusString: String?) {
        radiuses = mutableListOf()
        radiusString?.split(",")?.forEach { radius -> radiuses.add(radius.trim().toFloat()) }
    }

    private fun Float.dp2px(): Float {
        val scale = Resources.getSystem().displayMetrics.density
        return this * scale + 0.5f
    }

    fun setValue(id: Int, type: String, value: Float) {
        val dirtyIndex = ids.indexOf(id)
        if (dirtyIndex != -1) {
            when (type) {
                TYPE_OFFSET_X -> offsetXs[dirtyIndex] = value
                TYPE_OFFSET_Y -> offsetYs[dirtyIndex] = value
                TYPE_RADIUS -> radiuses[dirtyIndex] = value
            }
            (parent as? TreasureBox)?.postInvalidate()
        }
    }
}
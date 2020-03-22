package test.taylor.com.taylorcode.ui.custom_view.treasure_box

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import taylor.com.treasurebox.R
import taylor.com.treasurebox.print

/**
 * the child of [TreasureBox]
 */
abstract class Treasure @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    internal var ids = mutableListOf<Int>()
    var layoutParams = mutableListOf<LayoutParam>()

    init {
        readAttrs(attrs)
    }

    fun updateLayout(treasureBox: TreasureBox) {

    }

    fun onPreDraw(treasureBox: TreasureBox) {
        ids.forEachIndexed { index, id ->
            treasureBox.findViewById<View>(id)?.let { v ->
                LayoutParam(v.width, v.height, v.x, v.y).let { lp ->
                    if (layoutParams[index] != lp) {
                        if (layoutParams[index].isValid()) {
                            treasureBox.postInvalidate()
                        }
                        layoutParams[index] = lp
                    }
                }
            }
        }
    }

    /**
     * draw something with [TreasureBox]'s canvas
     */
    abstract fun drawTreasure(treasureBox: TreasureBox, canvas: Canvas?)

    open fun readAttrs(attributeSet: AttributeSet?) {
        attributeSet?.let { attrs ->
            context.obtainStyledAttributes(attrs, R.styleable.Treasure)?.let {
                divideIds(it.getString(R.styleable.Treasure_reference_ids))
                it.recycle()
            }
        }
    }

    private fun divideIds(idString: String?) {
        idString?.split(",")?.forEach { id ->
            ids.add(resources.getIdentifier(id.trim(), "id", context.packageName))
            layoutParams.add(LayoutParam())
        }
        ids.toCollection(mutableListOf()).print("ids") { it.toString() }
    }

    data class LayoutParam(var width: Int = 0, var height: Int = 0, var x: Float = 0f, var y: Float = 0f) {
        private var id: Int? = null
        override fun equals(other: Any?): Boolean {
            if (other == null || other !is LayoutParam) return false
            return width == other.width && height == other.height && x == other.x && y == other.y
        }

        fun isValid() = width != 0 && height != 0
    }
}
package test.taylor.com.taylorcode.ui.custom_view.treasure_box

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
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
    var rects = mutableListOf<Rect>()
    var lastRects = mutableListOf<Rect>()
//    var rect: Rect = Rect()
//    var lastRect: Rect = Rect()

    init {
        readAttrs(attrs)
    }

    fun updateLayout(treasureBox: TreasureBox) {

    }

    fun onPreDraw(treasureBox: TreasureBox) {
        ids.forEachIndexed { index, id ->
            treasureBox.findViewById<View>(id)?.let { v ->
                v.getHitRect(rects[index])
                if (rects[index] != lastRects[index]) {
                    treasureBox.postInvalidate()
                    lastRects[index].set(rects[index])
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
            rects.add(Rect())
            lastRects.add(Rect())
        }
        ids.toCollection(mutableListOf()).print("ids") { it.toString() }
    }
}
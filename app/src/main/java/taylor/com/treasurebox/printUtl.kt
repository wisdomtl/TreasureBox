package taylor.com.treasurebox

import android.util.Log

/**
 * print collection
 */
fun <T> Collection<T>.print(tag: String, map: (T) -> String) {
    this.let { c ->
        StringBuilder("\n[").apply {
            c.forEach { element -> append("\n\t${map.invoke(element)},") }
            append("\n]")
        }.also { Log.d(tag, it.toString()) }
    }
}
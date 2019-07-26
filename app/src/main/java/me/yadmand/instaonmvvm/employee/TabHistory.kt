package me.yadmand.instaonmvvm.employee

import java.io.Serializable
import java.util.AbstractCollection

class TabHistory : Serializable {

    public  val stack = mutableMapOf<String, Int>()

    private val isEmpty: Boolean
        get() = stack.size == 0

    val size: Int
        get() = stack.size

    @Synchronized fun push(type: String, entry: Int) {
        stack.remove(type)
        stack.put(type, entry)
    }

    @Synchronized fun popPrevious(): Int {
        var entry = -1
        var key: String

        if (!isEmpty) {
            entry = (stack.values as AbstractCollection).toArray()[stack.values.size - 2] as Int
            key = (stack.keys as AbstractCollection<*>).toArray()[stack.keys.size - 1] as String
            if (key != "navigation_home")
                stack.remove(key)
        }
        return entry
    }

    fun clear() {
        stack.clear()
    }
}
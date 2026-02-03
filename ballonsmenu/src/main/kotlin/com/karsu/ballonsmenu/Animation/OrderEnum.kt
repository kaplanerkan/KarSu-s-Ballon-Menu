/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Animation

enum class OrderEnum(val value: Int) {
    DEFAULT(0),
    REVERSE(1),
    RANDOM(2),
    Unknown(-1);

    companion object {
        @JvmStatic
        fun getEnum(value: Int): OrderEnum {
            return if (value < 0 || value >= entries.size) Unknown
            else entries[value]
        }
    }
}

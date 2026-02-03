/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

enum class ButtonEnum(val value: Int) {
    /**
     * KarSu-buttons which are just simple circles with an image for each.
     */
    SimpleCircle(0),

    /**
     * KarSu-buttons which are circles with a text and image inside for each.
     */
    TextInsideCircle(1),

    /**
     * KarSu-buttons which are circles with a text outside and image inside for each.
     */
    TextOutsideCircle(2),

    /**
     * KarSu-buttons which are rectangles with a title, subtitle and image inside for each.
     */
    Ham(3),

    Unknown(-1);

    companion object {
        @JvmStatic
        fun getEnum(value: Int): ButtonEnum {
            return if (value < 0 || value > entries.size) Unknown
            else entries[value]
        }
    }
}

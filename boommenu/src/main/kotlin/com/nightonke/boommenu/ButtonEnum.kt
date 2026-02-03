package com.nightonke.boommenu

enum class ButtonEnum(val value: Int) {
    /**
     * Boom-buttons which are just simple circles with an image for each.
     */
    SimpleCircle(0),

    /**
     * Boom-buttons which are circles with a text and image inside for each.
     */
    TextInsideCircle(1),

    /**
     * Boom-buttons which are circles with a text outside and image inside for each.
     */
    TextOutsideCircle(2),

    /**
     * Boom-buttons which are rectangles with a title, subtitle and image inside for each.
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

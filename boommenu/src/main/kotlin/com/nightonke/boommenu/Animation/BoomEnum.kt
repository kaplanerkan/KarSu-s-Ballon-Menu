package com.nightonke.boommenu.Animation

enum class BoomEnum(val value: Int) {
    LINE(0),
    PARABOLA_1(1),
    PARABOLA_2(2),
    PARABOLA_3(3),
    PARABOLA_4(4),
    HORIZONTAL_THROW_1(5),
    HORIZONTAL_THROW_2(6),
    RANDOM(7),
    Unknown(-1);

    companion object {
        @JvmStatic
        fun getEnum(value: Int): BoomEnum {
            return if (value < 0 || value >= entries.size) Unknown
            else entries[value]
        }
    }
}

package com.nightonke.boommenu.Animation

enum class EaseEnum(val value: Int) {
    EaseInSine(0),
    EaseOutSine(1),
    EaseInOutSine(2),
    EaseInQuad(3),
    EaseOutQuad(4),
    EaseInOutQuad(5),
    EaseInCubic(6),
    EaseOutCubic(7),
    EaseInOutCubic(8),
    EaseInQuart(9),
    EaseOutQuart(10),
    EaseInOutQuart(11),
    EaseInQuint(12),
    EaseOutQuint(13),
    EaseInOutQuint(14),
    EaseInExpo(15),
    EaseOutExpo(16),
    EaseInOutExpo(17),
    EaseInCirc(18),
    EaseOutCirc(19),
    EaseInOutCirc(20),
    EaseInBack(21),
    EaseOutBack(22),
    EaseInOutBack(23),
    EaseInElastic(24),
    EaseOutElastic(25),
    EaseInOutElastic(26),
    EaseInBounce(27),
    EaseOutBounce(28),
    EaseInOutBounce(29),
    Linear(30),
    Unknown(-1);

    companion object {
        @JvmStatic
        fun getEnum(value: Int): EaseEnum {
            return if (value < 0 || value >= entries.size) Unknown
            else entries[value]
        }
    }
}

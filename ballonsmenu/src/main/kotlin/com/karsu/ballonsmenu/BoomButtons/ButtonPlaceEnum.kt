/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

enum class ButtonPlaceEnum(val value: Int) {
    SC_1(0),
    SC_2_1(1),
    SC_2_2(2),
    SC_3_1(3),
    SC_3_2(4),
    SC_3_3(5),
    SC_3_4(6),
    SC_4_1(7),
    SC_4_2(8),
    SC_5_1(9),
    SC_5_2(10),
    SC_5_3(11),
    SC_5_4(12),
    SC_6_1(13),
    SC_6_2(14),
    SC_6_3(15),
    SC_6_4(16),
    SC_6_5(17),
    SC_6_6(18),
    SC_7_1(19),
    SC_7_2(20),
    SC_7_3(21),
    SC_7_4(22),
    SC_7_5(23),
    SC_7_6(24),
    SC_8_1(25),
    SC_8_2(26),
    SC_8_3(27),
    SC_8_4(28),
    SC_8_5(29),
    SC_8_6(30),
    SC_8_7(31),
    SC_9_1(32),
    SC_9_2(33),
    SC_9_3(34),
    HAM_1(35),
    HAM_2(36),
    HAM_3(37),
    HAM_4(38),
    HAM_5(39),
    HAM_6(40),
    Horizontal(Int.MAX_VALUE - 2),
    Vertical(Int.MAX_VALUE - 1),
    Custom(Int.MAX_VALUE),
    Unknown(-1);

    /**
     * Get the number of boom-button for button-place-enum.
     * -1 for unknown, and MAX_INT for horizontal or vertical place-enum.
     *
     * @return the number of boom-button
     */
    fun buttonNumber(): Int = when (this) {
        SC_1, HAM_1 -> 1
        SC_2_1, SC_2_2, HAM_2 -> 2
        SC_3_1, SC_3_2, SC_3_3, SC_3_4, HAM_3 -> 3
        SC_4_1, SC_4_2, HAM_4 -> 4
        SC_5_1, SC_5_2, SC_5_3, SC_5_4, HAM_5 -> 5
        SC_6_1, SC_6_2, SC_6_3, SC_6_4, SC_6_5, SC_6_6, HAM_6 -> 6
        SC_7_1, SC_7_2, SC_7_3, SC_7_4, SC_7_5, SC_7_6 -> 7
        SC_8_1, SC_8_2, SC_8_3, SC_8_4, SC_8_5, SC_8_6, SC_8_7 -> 8
        SC_9_1, SC_9_2, SC_9_3 -> 9
        Unknown -> 0
        else -> -1
    }

    /**
     * Get the minimum button number for this buttonPlaceEnum
     *
     * @return minimum button number
     */
    fun minButtonNumber(): Int = when (this) {
        Horizontal, Vertical, Custom -> 1
        Unknown -> 0
        else -> -1
    }

    /**
     * Get the maximum button number for this buttonPlaceEnum
     *
     * @return maximum button number
     */
    fun maxButtonNumber(): Int = when (this) {
        Horizontal, Vertical, Custom -> Int.MAX_VALUE
        Unknown -> 0
        else -> -1
    }

    companion object {
        @JvmStatic
        fun getEnum(value: Int): ButtonPlaceEnum {
            return if (value < 0 || value >= entries.size) Unknown
            else entries[value]
        }
    }
}

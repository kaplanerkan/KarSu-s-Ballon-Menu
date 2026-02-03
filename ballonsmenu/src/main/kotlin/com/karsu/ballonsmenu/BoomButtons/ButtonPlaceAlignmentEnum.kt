/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

enum class ButtonPlaceAlignmentEnum(val value: Int) {
    Center(0),
    Top(1),
    Bottom(2),
    Left(3),
    Right(4),
    TL(5),
    TR(6),
    BL(7),
    BR(8),
    Unknown(-1);

    companion object {
        @JvmStatic
        fun getEnum(value: Int): ButtonPlaceAlignmentEnum {
            return if (value < 0 || value >= entries.size) Unknown
            else entries[value]
        }
    }
}

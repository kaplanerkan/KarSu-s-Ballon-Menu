package com.nightonke.boommenu.BoomButtons

/**
 * Created by Weiping Huang at 20:36 on 16/11/19
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */

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

/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.Piece

enum class PiecePlaceEnum(val value: Int) {

    DOT_1(0),
    DOT_2_1(1),
    DOT_2_2(2),
    DOT_3_1(3),
    DOT_3_2(4),
    DOT_3_3(5),
    DOT_3_4(6),
    DOT_4_1(7),
    DOT_4_2(8),
    DOT_5_1(9),
    DOT_5_2(10),
    DOT_5_3(11),
    DOT_5_4(12),
    DOT_6_1(13),
    DOT_6_2(14),
    DOT_6_3(15),
    DOT_6_4(16),
    DOT_6_5(17),
    DOT_6_6(18),
    DOT_7_1(19),
    DOT_7_2(20),
    DOT_7_3(21),
    DOT_7_4(22),
    DOT_7_5(23),
    DOT_7_6(24),
    DOT_8_1(25),
    DOT_8_2(26),
    DOT_8_3(27),
    DOT_8_4(28),
    DOT_8_5(29),
    DOT_8_6(30),
    DOT_8_7(31),
    DOT_9_1(32),
    DOT_9_2(33),
    DOT_9_3(34),

    HAM_1(35),
    HAM_2(36),
    HAM_3(37),
    HAM_4(38),
    HAM_5(39),
    HAM_6(40),

    Share(99999),

    Custom(100000),

    Unknown(-1);

    /**
     * Get number of pieces from a piece-place-enum.
     *
     * @return the number of pieces
     */
    fun pieceNumber(): Int = when (this) {
        DOT_1, HAM_1 -> 1
        DOT_2_1, DOT_2_2, HAM_2 -> 2
        DOT_3_1, DOT_3_2, DOT_3_3, DOT_3_4, HAM_3 -> 3
        DOT_4_1, DOT_4_2, HAM_4 -> 4
        DOT_5_1, DOT_5_2, DOT_5_3, DOT_5_4, HAM_5 -> 5
        DOT_6_1, DOT_6_2, DOT_6_3, DOT_6_4, DOT_6_5, DOT_6_6, HAM_6 -> 6
        DOT_7_1, DOT_7_2, DOT_7_3, DOT_7_4, DOT_7_5, DOT_7_6 -> 7
        DOT_8_1, DOT_8_2, DOT_8_3, DOT_8_4, DOT_8_5, DOT_8_6, DOT_8_7 -> 8
        DOT_9_1, DOT_9_2, DOT_9_3 -> 9
        Unknown -> 0
        else -> -1
    }

    /**
     * Get the minimum piece number for this piecePlaceEnum
     *
     * @return minimum piece number
     */
    fun minPieceNumber(): Int = when (this) {
        Share -> 3
        Custom -> 1
        Unknown -> 0
        else -> -1
    }

    /**
     * Get the maximum piece number for this piecePlaceEnum
     *
     * @return maximum piece number
     */
    fun maxPieceNumber(): Int = when (this) {
        Share, Custom -> Int.MAX_VALUE
        Unknown -> 0
        else -> -1
    }

    companion object {
        @JvmStatic
        fun getEnum(value: Int): PiecePlaceEnum {
            if (value < 0 || value >= values().size) return Unknown
            return values()[value]
        }
    }
}

/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import com.karsu.ballonsmenu.Animation.KarSuEnum
import com.karsu.ballonsmenu.KarSuButtons.KarSuButtonBuilder
import com.karsu.ballonsmenu.KarSuButtons.ButtonPlaceEnum
import com.karsu.ballonsmenu.Piece.PiecePlaceEnum

internal object ExceptionManager {

    @JvmStatic
    fun judge(bmb: KarSuMenuButton, builders: ArrayList<KarSuButtonBuilder<*>>?) {
        if (bmb.buttonEnum == ButtonEnum.Unknown) {
            throw RuntimeException("Unknown button-enum!")
        }
        if (bmb.piecePlaceEnum == PiecePlaceEnum.Unknown) {
            throw RuntimeException("Unknown piece-place-enum!")
        }
        if (bmb.buttonPlaceEnum == ButtonPlaceEnum.Unknown) {
            throw RuntimeException("Unknown button-place-enum!")
        }
        if (bmb.boomEnum == null || bmb.boomEnum == KarSuEnum.Unknown) {
            throw RuntimeException("Unknown boom-enum!")
        }
        if (builders == null || builders.isEmpty()) {
            throw RuntimeException("Empty builders!")
        }

        val pieceNumber = bmb.piecePlaceEnum.pieceNumber()
        val minPieceNumber = bmb.piecePlaceEnum.minPieceNumber()
        val maxPieceNumber = bmb.piecePlaceEnum.maxPieceNumber()
        val customPiecePositionsNumber = bmb.customPiecePlacePositions.size

        val buttonNumber = bmb.buttonPlaceEnum.buttonNumber()
        val minButtonNumber = bmb.buttonPlaceEnum.minButtonNumber()
        val maxButtonNumber = bmb.buttonPlaceEnum.maxButtonNumber()
        val customButtonPositionsNumber = bmb.customButtonPlacePositions.size

        val builderNumber = builders.size

        if (pieceNumber == -1) {
            // The piece number is in a range
            if (buttonNumber != -1 && !(minPieceNumber <= buttonNumber && buttonNumber <= maxPieceNumber)) {
                // The button-place-enum has a certain number of buttons, then it must be in the range
                throw RuntimeException(
                    "The number($buttonNumber) of buttons of " +
                            "button-place-enum(${bmb.buttonPlaceEnum}) is not in the " +
                            "range([$minPieceNumber, $maxPieceNumber]) of the " +
                            "piece-place-enum(${bmb.piecePlaceEnum})"
                )
            }
            if (!(minPieceNumber <= builderNumber && builderNumber <= maxPieceNumber)) {
                // The number of builders must be in the range
                throw RuntimeException(
                    "The number of builders($builderNumber) is not " +
                            "in the range([$minPieceNumber, $maxPieceNumber]) of the " +
                            "piece-place-enum(${bmb.piecePlaceEnum})"
                )
            }
        } else {
            if (buttonNumber != -1) {
                // The piece-place-enum and button-place-enum both have a certain number of pieces and buttons. They must be the same
                if (pieceNumber != buttonNumber) {
                    throw RuntimeException(
                        "The number of piece($pieceNumber) is not " +
                                "equal to buttons'($buttonNumber)"
                    )
                }
                if (pieceNumber != builderNumber) {
                    throw RuntimeException(
                        "The number of piece($pieceNumber) is not " +
                                "equal to builders'($builderNumber)"
                    )
                }
            }
        }

        if (bmb.piecePlaceEnum == PiecePlaceEnum.Custom) {
            if (customPiecePositionsNumber <= 0) {
                throw RuntimeException(
                    "When the positions of pieces are customized, the " +
                            "custom-piece-place-positions array is empty"
                )
            }
            if (buttonNumber == -1) {
                // The button number is in a range
                if (!(minButtonNumber <= customPiecePositionsNumber && customPiecePositionsNumber <= maxButtonNumber)) {
                    throw RuntimeException(
                        "When the positions of pieces is customized, the " +
                                "length($customPiecePositionsNumber) of " +
                                "custom-piece-place-positions array is not in the range([" +
                                "$minButtonNumber, $maxButtonNumber])"
                    )
                }
            } else {
                if (customPiecePositionsNumber != buttonNumber) {
                    throw RuntimeException(
                        "The number of piece($customPiecePositionsNumber" +
                                ") is not equal to buttons'($buttonNumber)"
                    )
                }
            }
            if (customPiecePositionsNumber != builderNumber) {
                throw RuntimeException(
                    "The number of piece($customPiecePositionsNumber" +
                            ") is not equal to builders'($builderNumber)"
                )
            }
        }

        if (bmb.buttonPlaceEnum == ButtonPlaceEnum.Custom) {
            if (customButtonPositionsNumber <= 0) {
                throw RuntimeException(
                    "When the positions of buttons are customized, the " +
                            "custom-button-place-positions array is empty"
                )
            }
            if (pieceNumber == -1) {
                // The piece number is in a range
                if (!(minPieceNumber <= customButtonPositionsNumber && customButtonPositionsNumber <= maxPieceNumber)) {
                    throw RuntimeException(
                        "When the positions of buttons is customized, the " +
                                "length($customButtonPositionsNumber) of " +
                                "custom-button-place-positions array is not in the range([" +
                                "$minPieceNumber, $maxPieceNumber])"
                    )
                }
            } else {
                if (customButtonPositionsNumber != pieceNumber) {
                    throw RuntimeException(
                        "The number of button($customButtonPositionsNumber" +
                                ") is not equal to pieces'($pieceNumber)"
                    )
                }
            }
            if (customButtonPositionsNumber != builderNumber) {
                throw RuntimeException(
                    "The number of button($customButtonPositionsNumber" +
                            ") is not equal to builders'($builderNumber)"
                )
            }
        }
    }
}

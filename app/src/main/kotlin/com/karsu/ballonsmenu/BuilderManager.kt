/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu

import android.graphics.Color
import android.util.Pair

import com.karsu.ballonsmenu.app.R
import com.karsu.ballonsmenu.karsu_buttons.ButtonPlaceEnum
import com.karsu.ballonsmenu.karsu_buttons.HamButton
import com.karsu.ballonsmenu.karsu_buttons.SimpleCircleButton
import com.karsu.ballonsmenu.karsu_buttons.TextInsideCircleButton
import com.karsu.ballonsmenu.karsu_buttons.TextOutsideCircleButton
import com.karsu.ballonsmenu.piece.PiecePlaceEnum

object BuilderManager {

    private val imageResources = intArrayOf(
        R.drawable.bat,
        R.drawable.bear,
        R.drawable.bee,
        R.drawable.butterfly,
        R.drawable.cat,
        R.drawable.deer,
        R.drawable.dolphin,
        R.drawable.eagle,
        R.drawable.horse,
        R.drawable.elephant,
        R.drawable.owl,
        R.drawable.peacock,
        R.drawable.pig,
        R.drawable.rat,
        R.drawable.snake,
        R.drawable.squirrel
    )

    private var imageResourceIndex = 0

    @JvmStatic
    fun getImageResource(): Int {
        if (imageResourceIndex >= imageResources.size) imageResourceIndex = 0
        return imageResources[imageResourceIndex++]
    }

    @JvmStatic
    fun getSimpleCircleButtonBuilder(): SimpleCircleButton.Builder =
        SimpleCircleButton.Builder()
            .normalImageRes(getImageResource())

    @JvmStatic
    fun getSquareSimpleCircleButtonBuilder(): SimpleCircleButton.Builder =
        SimpleCircleButton.Builder()
            .isRound(false)
            .shadowCornerRadius(Util.dp2px(20f))
            .buttonCornerRadius(Util.dp2px(20f))
            .normalImageRes(getImageResource())

    @JvmStatic
    fun getTextInsideCircleButtonBuilder(): TextInsideCircleButton.Builder =
        TextInsideCircleButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_inside_circle_button_text_normal)

    @JvmStatic
    fun getSquareTextInsideCircleButtonBuilder(): TextInsideCircleButton.Builder =
        TextInsideCircleButton.Builder()
            .isRound(false)
            .shadowCornerRadius(Util.dp2px(10f))
            .buttonCornerRadius(Util.dp2px(10f))
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_inside_circle_button_text_normal)

    @JvmStatic
    fun getTextInsideCircleButtonBuilderWithDifferentPieceColor(): TextInsideCircleButton.Builder =
        TextInsideCircleButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_inside_circle_button_text_normal)
            .pieceColor(Color.WHITE)

    @JvmStatic
    fun getTextOutsideCircleButtonBuilder(): TextOutsideCircleButton.Builder =
        TextOutsideCircleButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_outside_circle_button_text_normal)

    @JvmStatic
    fun getSquareTextOutsideCircleButtonBuilder(): TextOutsideCircleButton.Builder =
        TextOutsideCircleButton.Builder()
            .isRound(false)
            .shadowCornerRadius(Util.dp2px(15f))
            .buttonCornerRadius(Util.dp2px(15f))
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_outside_circle_button_text_normal)

    @JvmStatic
    fun getTextOutsideCircleButtonBuilderWithDifferentPieceColor(): TextOutsideCircleButton.Builder =
        TextOutsideCircleButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_outside_circle_button_text_normal)
            .pieceColor(Color.WHITE)

    @JvmStatic
    fun getHamButtonBuilder(): HamButton.Builder =
        HamButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_ham_button_text_normal)
            .subNormalTextRes(R.string.text_ham_button_sub_text_normal)

    @JvmStatic
    fun getHamButtonBuilder(text: String, subText: String): HamButton.Builder =
        HamButton.Builder()
            .normalImageRes(getImageResource())
            .normalText(text)
            .subNormalText(subText)

    @JvmStatic
    fun getPieceCornerRadiusHamButtonBuilder(): HamButton.Builder =
        HamButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_ham_button_text_normal)
            .subNormalTextRes(R.string.text_ham_button_sub_text_normal)

    @JvmStatic
    fun getHamButtonBuilderWithDifferentPieceColor(): HamButton.Builder =
        HamButton.Builder()
            .normalImageRes(getImageResource())
            .normalTextRes(R.string.text_ham_button_text_normal)
            .subNormalTextRes(R.string.text_ham_button_sub_text_normal)
            .pieceColor(Color.WHITE)

    @JvmStatic
    fun getCircleButtonData(piecesAndButtons: ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>): List<String> {
        val data = ArrayList<String>()
        for (p in 0 until PiecePlaceEnum.values().size - 1) {
            for (b in 0 until ButtonPlaceEnum.values().size - 1) {
                val piecePlaceEnum = PiecePlaceEnum.getEnum(p)
                val buttonPlaceEnum = ButtonPlaceEnum.getEnum(b)
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                    || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
                    || buttonPlaceEnum == ButtonPlaceEnum.Vertical
                ) {
                    piecesAndButtons.add(Pair(piecePlaceEnum, buttonPlaceEnum))
                    data.add("$piecePlaceEnum $buttonPlaceEnum")

                    val shouldRemove = when {
                        piecePlaceEnum == PiecePlaceEnum.HAM_1 -> true
                        piecePlaceEnum == PiecePlaceEnum.HAM_2 -> true
                        piecePlaceEnum == PiecePlaceEnum.HAM_3 -> true
                        piecePlaceEnum == PiecePlaceEnum.HAM_4 -> true
                        piecePlaceEnum == PiecePlaceEnum.HAM_5 -> true
                        piecePlaceEnum == PiecePlaceEnum.HAM_6 -> true
                        piecePlaceEnum == PiecePlaceEnum.Share -> true
                        piecePlaceEnum == PiecePlaceEnum.Custom -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_1 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_2 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_3 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_4 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_5 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.HAM_6 -> true
                        buttonPlaceEnum == ButtonPlaceEnum.Custom -> true
                        else -> false
                    }

                    if (shouldRemove) {
                        piecesAndButtons.removeAt(piecesAndButtons.size - 1)
                        data.removeAt(data.size - 1)
                    }
                }
            }
        }
        return data
    }

    @JvmStatic
    fun getHamButtonData(piecesAndButtons: ArrayList<Pair<PiecePlaceEnum, ButtonPlaceEnum>>): List<String> {
        val data = ArrayList<String>()
        for (p in 0 until PiecePlaceEnum.values().size - 1) {
            for (b in 0 until ButtonPlaceEnum.values().size - 1) {
                val piecePlaceEnum = PiecePlaceEnum.getEnum(p)
                val buttonPlaceEnum = ButtonPlaceEnum.getEnum(b)
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                    || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
                    || buttonPlaceEnum == ButtonPlaceEnum.Vertical
                ) {
                    piecesAndButtons.add(Pair(piecePlaceEnum, buttonPlaceEnum))
                    data.add("$piecePlaceEnum $buttonPlaceEnum")

                    val shouldRemove = piecePlaceEnum.value < PiecePlaceEnum.HAM_1.value
                            || piecePlaceEnum == PiecePlaceEnum.Share
                            || piecePlaceEnum == PiecePlaceEnum.Custom
                            || buttonPlaceEnum.value < ButtonPlaceEnum.HAM_1.value

                    if (shouldRemove) {
                        piecesAndButtons.removeAt(piecesAndButtons.size - 1)
                        data.removeAt(data.size - 1)
                    }
                }
            }
        }
        return data
    }

    // Singleton instance for Java interop
    @JvmStatic
    fun getInstance(): BuilderManager = this
}

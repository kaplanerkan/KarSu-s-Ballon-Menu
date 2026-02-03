/** Created by Erkan Kaplan on 2026-02-03 */
package com.karsu.ballonsmenu.BoomButtons

import android.graphics.Rect
import android.graphics.Typeface
import android.text.TextUtils

@Suppress("UNCHECKED_CAST")
abstract class BoomButtonWithTextBuilder<T : BoomButtonWithTextBuilder<T>> : BoomButtonBuilder<T>() {

    /**
     * Set the text when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalText text
     * @return the builder
     */
    fun normalText(normalText: String?): T {
        if (this.normalText == null || this.normalText != normalText) {
            this.normalText = normalText
            button()?.let { button ->
                button.normalText = normalText
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the text resource when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextRes text resource
     * @return the builder
     */
    fun normalTextRes(normalTextRes: Int): T {
        if (this.normalTextRes != normalTextRes) {
            this.normalTextRes = normalTextRes
            button()?.let { button ->
                button.normalTextRes = normalTextRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the text when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedText text
     * @return the builder
     */
    fun highlightedText(highlightedText: String?): T {
        if (this.highlightedText == null || this.highlightedText != highlightedText) {
            this.highlightedText = highlightedText
            button()?.let { button ->
                button.highlightedText = highlightedText
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the text resource when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextRes text resource
     * @return the builder
     */
    fun highlightedTextRes(highlightedTextRes: Int): T {
        if (this.highlightedTextRes != highlightedTextRes) {
            this.highlightedTextRes = highlightedTextRes
            button()?.let { button ->
                button.highlightedTextRes = highlightedTextRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the text when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableText text
     * @return the builder
     */
    fun unableText(unableText: String?): T {
        if (this.unableText == null || this.unableText != unableText) {
            this.unableText = unableText
            button()?.let { button ->
                button.unableText = unableText
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the text resource when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextRes text resource
     * @return the builder
     */
    fun unableTextRes(unableTextRes: Int): T {
        if (this.unableTextRes != unableTextRes) {
            this.unableTextRes = unableTextRes
            button()?.let { button ->
                button.unableTextRes = unableTextRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextColor color of text
     * @return the builder
     */
    fun normalTextColor(normalTextColor: Int): T {
        if (this.normalTextColor != normalTextColor) {
            this.normalTextColor = normalTextColor
            button()?.let { button ->
                button.normalTextColor = normalTextColor
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at normal-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param normalTextColorRes color resource of text
     * @return the builder
     */
    fun normalTextColorRes(normalTextColorRes: Int): T {
        if (this.normalTextColorRes != normalTextColorRes) {
            this.normalTextColorRes = normalTextColorRes
            button()?.let { button ->
                button.normalTextColorRes = normalTextColorRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextColor color of text
     * @return the builder
     */
    fun highlightedTextColor(highlightedTextColor: Int): T {
        if (this.highlightedTextColor != highlightedTextColor) {
            this.highlightedTextColor = highlightedTextColor
            button()?.let { button ->
                button.highlightedTextColor = highlightedTextColor
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at highlighted-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param highlightedTextColorRes color resource of text
     * @return the builder
     */
    fun highlightedTextColorRes(highlightedTextColorRes: Int): T {
        if (this.highlightedTextColorRes != highlightedTextColorRes) {
            this.highlightedTextColorRes = highlightedTextColorRes
            button()?.let { button ->
                button.highlightedTextColorRes = highlightedTextColorRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextColor color of text
     * @return the builder
     */
    fun unableTextColor(unableTextColor: Int): T {
        if (this.unableTextColor != unableTextColor) {
            this.unableTextColor = unableTextColor
            button()?.let { button ->
                button.unableTextColor = unableTextColor
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the color of text when boom-button is at unable-state.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param unableTextColorRes color resource of text
     * @return the builder
     */
    fun unableTextColorRes(unableTextColorRes: Int): T {
        if (this.unableTextColorRes != unableTextColorRes) {
            this.unableTextColorRes = unableTextColorRes
            button()?.let { button ->
                button.unableTextColorRes = unableTextColorRes
                button.updateText()
            }
        }
        return this as T
    }

    /**
     * Set the rect of text.
     * By this method, you can set the position and size of the text-view in boom-button.
     * For example, builder.textRect(new Rect(0, 50, 100, 100)) will make the
     * text-view's size to be 100 * 50 and margin-top to be 50 pixel.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param textRect the text rect, in pixel.
     * @return the builder
     */
    fun textRect(textRect: Rect): T {
        if (this.textRect != textRect) {
            this.textRect = textRect
            button()?.let { button ->
                button.textRect = textRect
                button.updateTextRect()
            }
        }
        return this as T
    }

    /**
     * Set the padding of text.
     * By this method, you can control the padding in the text-view.
     * For instance, builder.textPadding(new Rect(10, 10, 10, 10)) will make the
     * text-view content 10-pixel padding to itself.
     *
     * Synchronicity: If the boom-button existed,
     * then synchronize this change to boom-button.
     *
     * @param textPadding the text padding
     * @return the builder
     */
    fun textPadding(textPadding: Rect): T {
        if (this.textPadding != textPadding) {
            this.textPadding = textPadding
            button()?.let { button ->
                button.textPadding = textPadding
                button.updateTextPadding()
            }
        }
        return this as T
    }

    /**
     * Set the typeface of the text.
     *
     * @param typeface typeface
     * @return the builder
     */
    fun typeface(typeface: Typeface?): T {
        this.typeface = typeface
        return this as T
    }

    /**
     * Set the maximum of the lines of text-view.
     *
     * @param maxLines maximum lines
     * @return the builder
     */
    fun maxLines(maxLines: Int): T {
        this.maxLines = maxLines
        return this as T
    }

    /**
     * Set the gravity of text-view.
     *
     * @param gravity gravity, for example, Gravity.CENTER
     * @return the builder
     */
    fun textGravity(gravity: Int): T {
        this.textGravity = gravity
        return this as T
    }

    /**
     * Set the ellipsize of the text-view.
     *
     * @param ellipsize ellipsize
     * @return the builder
     */
    fun ellipsize(ellipsize: TextUtils.TruncateAt): T {
        this.ellipsize = ellipsize
        return this as T
    }

    /**
     * Set the text size of the text-view.
     *
     * @param textSize size of text, in sp
     * @return the builder
     */
    fun textSize(textSize: Int): T {
        this.textSize = textSize
        return this as T
    }
}

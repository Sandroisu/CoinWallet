package ru.coin.alexwallet.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import ru.coin.alexwallet.R

class CoinEditText :
    androidx.appcompat.widget.AppCompatEditText {

    private var mOnImeBackCoin: CoinEditTextImeBackListener? = null
    private var closeIcon: Drawable? = null
    private var searchIcon: Drawable? = null
    private var iconSize = 0
    private var iconMargin = 0
    private var gotFocus = false
    private var endIconClickListener: OnEndIconClickListener? = null
    private var closeRect = Rect()

    constructor(context: Context) : super(context) {
        setWillNotDraw(false)
        closeIcon = AppCompatResources.getDrawable(context, R.drawable.ic_close)
        searchIcon = AppCompatResources.getDrawable(context, R.drawable.ic_search)
        closeIcon?.let { iconSize = it.intrinsicWidth }
        iconMargin = iconSize / 3
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setWillNotDraw(false)
        closeIcon = AppCompatResources.getDrawable(context, R.drawable.ic_close)
        searchIcon = AppCompatResources.getDrawable(context, R.drawable.ic_search)
        closeIcon?.let { iconSize = it.intrinsicWidth }
        iconMargin = iconSize / 3
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setWillNotDraw(false)
        closeIcon = AppCompatResources.getDrawable(context, R.drawable.ic_close)
        searchIcon = AppCompatResources.getDrawable(context, R.drawable.ic_search)
        closeIcon?.let { iconSize = it.intrinsicWidth }
        iconMargin = iconSize / 3
    }

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent?): Boolean {
        if (event?.keyCode == KeyEvent.KEYCODE_BACK &&
            event.action == KeyEvent.ACTION_UP
        ) {
            if (mOnImeBackCoin != null)
                mOnImeBackCoin?.onImeBack(this, this.text.toString())
        }
        return super.dispatchKeyEvent(event)
    }

    fun setOnImeBackListener(listenerCoin: CoinEditTextImeBackListener) {
        mOnImeBackCoin = listenerCoin;
    }

    fun setOnCloseIconListener(listener: OnEndIconClickListener) {
        endIconClickListener = listener;
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        val left = measuredWidth - iconSize - iconMargin
        val right = left + iconSize
        val top = measuredHeight / 2 - iconSize / 2
        val bottom = measuredHeight / 2 + iconSize / 2
        if (gotFocus) {
            closeIcon?.setBounds(
                left,
                top,
                right,
                bottom
            )

            closeIcon?.draw(canvas)
            val iconCopy = closeIcon
            iconCopy?.let {
                closeRect = iconCopy.bounds
            }

        } else {
            searchIcon?.setBounds(
                left,
                top,
                right,
                bottom
            )
            searchIcon?.draw(canvas)
        }


    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        gotFocus = focused
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (endIconClickListener != null && gotFocus) {
            val endIconClickListenerCopy = endIconClickListener
            if (event.action == MotionEvent.ACTION_DOWN &&
                closeRect.contains(event.x.toInt(), event.y.toInt())
            ) {
                text = Editable.Factory.getInstance().newEditable("")
                endIconClickListenerCopy?.onClose()
                gotFocus = false
                return true
            }
        }
        return super.onTouchEvent(event)
    }

}


interface CoinEditTextImeBackListener {
    fun onImeBack(ctrl: CoinEditText, text: String)
}

interface OnEndIconClickListener {
    fun onClose()
}

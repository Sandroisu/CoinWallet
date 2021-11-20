package ru.coin.alexwallet.component

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import ru.coin.alexwallet.R


class CoinEditText : androidx.appcompat.widget.AppCompatEditText {

    private var mOnImeBackCoin: CoinEditTextImeBackListener? = null
    private var closeIcon: Drawable? = null
    private var searchIcon: Drawable? = null
    private var iconSize = 0
    private var iconMargin = 0
    private var gotFocus = false
    private var endIconClickListener: OnEndIconClickListener? = null
    private var closeRect = Rect()
    private var backgroundRect = Rect()
    private var realBoundsRect = Rect()
    private var backgroundPaint = Paint()
    private var strokeWidth = 0
    private var viewHeight = 0
    private var viewWidth = 0
    private var firstTime = true

    constructor(context: Context) : super(context) {
        setWillNotDraw(false)
        closeIcon = AppCompatResources.getDrawable(context, R.drawable.ic_close)
        searchIcon = AppCompatResources.getDrawable(context, R.drawable.ic_search)
        closeIcon?.let { iconSize = it.intrinsicWidth }
        iconMargin = iconSize / 3
        paint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.isAntiAlias = true
        backgroundPaint.isDither = true
        backgroundPaint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        strokeWidth = resources.getDimensionPixelSize(R.dimen.standard_margin)
        setPadding(strokeWidth, strokeWidth, (iconSize + strokeWidth), strokeWidth)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setWillNotDraw(false)
        closeIcon = AppCompatResources.getDrawable(context, R.drawable.ic_close)
        searchIcon = AppCompatResources.getDrawable(context, R.drawable.ic_search)
        closeIcon?.let { iconSize = it.intrinsicWidth }
        iconMargin = iconSize / 3
        paint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.isAntiAlias = true
        backgroundPaint.isDither = true
        backgroundPaint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        strokeWidth = resources.getDimensionPixelSize(R.dimen.standard_margin)
        setPadding(strokeWidth, strokeWidth, (iconSize + strokeWidth), strokeWidth)
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
        paint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.isAntiAlias = true
        backgroundPaint.isDither = true
        backgroundPaint.color =
            AppCompatResources.getColorStateList(context, R.color.backgroundColor).defaultColor
        strokeWidth = resources.getDimensionPixelSize(R.dimen.standard_margin)
        setPadding(strokeWidth, strokeWidth, (iconSize + strokeWidth), strokeWidth)
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
        mOnImeBackCoin = listenerCoin
    }

    fun setOnCloseIconListener(listener: OnEndIconClickListener) {
        endIconClickListener = listener
    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
        if (canvas == null) {
            return
        }
        getDrawingRect(realBoundsRect)
        editableText
        val leftIcon = realBoundsRect.right - iconSize - iconMargin
        val rightIcon = leftIcon + iconSize
        val topIcon = height / 2 - iconSize / 2
        val bottomIcon = height / 2 + iconSize / 2
        val leftRect = realBoundsRect.right - iconSize - iconMargin
        val rightRect = realBoundsRect.right - strokeWidth
        val topRect = 0 + strokeWidth
        val bottomRect = height - strokeWidth
        backgroundRect.set(leftRect, topRect, rightRect, bottomRect)
        if (gotFocus) {
            closeIcon?.setBounds(
                leftIcon,
                topIcon,
                rightIcon,
                bottomIcon
            )
            canvas.drawRect(backgroundRect, backgroundPaint)
            closeIcon?.draw(canvas)
            val iconCopy = closeIcon
            iconCopy?.let {
                val leftCloseRect = width - iconSize - iconMargin
                val rightCloseRect = leftIcon + iconSize
                val topCloseRect = height / 2 - iconSize / 2
                val bottomCloseRect = height / 2 + iconSize / 2
                closeRect.set(leftCloseRect, topCloseRect, rightCloseRect, bottomCloseRect)
            }
        } else {
            searchIcon?.setBounds(
                leftIcon,
                topIcon,
                rightIcon,
                bottomIcon
            )
            canvas.drawRect(backgroundRect, backgroundPaint)
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (firstTime) {
            viewWidth = measuredWidth
            viewHeight = measuredHeight
            firstTime = false
        }
    }
}

interface CoinEditTextImeBackListener {
    fun onImeBack(ctrl: CoinEditText, text: String)
}

interface OnEndIconClickListener {
    fun onClose()
}

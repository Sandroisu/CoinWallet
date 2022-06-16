package ru.slatinin.nytnews.component

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import ru.slatinin.nytnews.R

class IconTextArrow : LinearLayoutCompat {
    val ivIcon: AppCompatImageView
    val ivArrow: AppCompatImageView
    val tvText: AppCompatTextView

    constructor(context: Context) : super(context) {
        orientation = HORIZONTAL
        val v = inflate(context, R.layout.icon_text_arrow_view, this)
        ivIcon = v.findViewById(R.id.icon_text_arrow_icon)
        tvText = v.findViewById(R.id.icon_text_arrow_text)
        ivArrow = v.findViewById(R.id.icon_text_arrow_arrow)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        orientation = HORIZONTAL
        val v = inflate(context, R.layout.icon_text_arrow_view, this)
        ivIcon = v.findViewById(R.id.icon_text_arrow_icon)
        tvText = v.findViewById(R.id.icon_text_arrow_text)
        ivArrow = v.findViewById(R.id.icon_text_arrow_arrow)
        val resAttrs = context.obtainStyledAttributes(attrs, R.styleable.IconTextArrow, 0, 0)
        val iconResId = resAttrs.getResourceId(R.styleable.IconTextArrow_startIcon, -1)
        if (iconResId >= 0) {
            setIcon(iconResId)
        }
        val text = resAttrs.getString(R.styleable.IconTextArrow_middleText)
        text?.let {
            setText(text)
        }
        resAttrs.recycle()
    }

    fun setIcon(icon: Drawable) {
        ivIcon.setImageDrawable(icon)
    }

    fun setIcon(resId: Int) {
        ivIcon.setImageResource(resId)
    }

    fun setText(text: String) {
        tvText.text = text
    }
}
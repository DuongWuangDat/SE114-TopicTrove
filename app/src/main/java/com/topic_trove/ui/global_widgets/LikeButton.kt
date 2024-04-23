package com.topic_trove.ui.global_widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import com.topic_trove.R

class LikeButton : Button {

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        // Apply attributes from XML layout
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.LikeButton, defStyle, 0)

            val drawableStart = typedArray.getResourceId(R.styleable.LikeButton_iconsource, -1)
            if (drawableStart != -1) {
                setCompoundDrawablesWithIntrinsicBounds(drawableStart, 0, 0, 0)
            }

            val background = typedArray.getResourceId(R.styleable.LikeButton_border_style, -1)
            if (background != -1) {
                setBackgroundResource(background)
            }

            val text = typedArray.getString(R.styleable.LikeButton_text)
            text?.let {
                setText(it)
            }

            typedArray.recycle()
        }
    }
}
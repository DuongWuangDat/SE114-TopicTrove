package com.topic_trove.ui.global_widgets

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.topic_trove.R

class PostCard : LinearLayout {

    private var textHashtag: TextView
    private var imageAvatar: ImageView
    private var imagetick: ImageView
    private var textName: TextView
    private var textDate: TextView
    private var textContent: TextView
    private var btnLike: LikeButton
    private var btnComment: CommentButton
    private var textTitle: TextView

    constructor(context: Context) : super(context) {
        LayoutInflater.from(context).inflate(R.layout.sample_post_card, this, true)
        textHashtag = findViewById(R.id.textHashtag)
        imageAvatar = findViewById(R.id.imageAvatar)
        imagetick = findViewById(R.id.imageVerificationTick)
        textName = findViewById(R.id.textName)
        textDate = findViewById(R.id.textDate)
        textContent = findViewById(R.id.textContent)
        btnLike = findViewById(R.id.btnLike)
        btnComment = findViewById(R.id.btnComment)
        textTitle = findViewById(R.id.textTitle)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        LayoutInflater.from(context).inflate(R.layout.sample_post_card, this, true)
        textHashtag = findViewById(R.id.textHashtag)
        imageAvatar = findViewById(R.id.imageAvatar)
        imagetick = findViewById(R.id.imageVerificationTick)
        textName = findViewById(R.id.textName)
        textDate = findViewById(R.id.textDate)
        textContent = findViewById(R.id.textContent)
        btnLike = findViewById(R.id.btnLike)
        btnComment = findViewById(R.id.btnComment)
        textTitle = findViewById(R.id.textTitle)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        LayoutInflater.from(context).inflate(R.layout.sample_post_card, this, true)
        textHashtag = findViewById(R.id.textHashtag)
        imageAvatar = findViewById(R.id.imageAvatar)
        imagetick = findViewById(R.id.imageVerificationTick)
        textName = findViewById(R.id.textName)
        textDate = findViewById(R.id.textDate)
        textContent = findViewById(R.id.textContent)
        btnLike = findViewById(R.id.btnLike)
        btnComment = findViewById(R.id.btnComment)
        textTitle = findViewById(R.id.textTitle)
    }

    fun setHashtag(hashtag: String) {
        textHashtag.text = hashtag
    }

    fun setAvatar(avatarDrawable: Drawable) {
        imageAvatar.setImageDrawable(avatarDrawable)
    }

    fun setVerificationTick(verificationDrawable: Drawable){
        imagetick.setImageDrawable(verificationDrawable)
    }
    fun setName(name: String) {
        textName.text = name
    }

    fun setDate(date: String) {
        textDate.text = date
    }

    fun setTitle(title: String) {
        textTitle.text = title
    }
    fun setContent(content: String) {
        textContent.text = content
    }

    fun setLikeButtonClickListener(listener: OnClickListener) {
        btnLike.setOnClickListener(listener)
    }

    fun setCommentButtonClickListener(listener: OnClickListener) {
        btnComment.setOnClickListener(listener)
    }
}
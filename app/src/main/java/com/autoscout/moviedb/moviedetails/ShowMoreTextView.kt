package com.autoscout.moviedb.moviedetails

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.autoscout.moviedb.R

class ShowMoreTextView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var overviewExpanded: Boolean = false
    private lateinit var textView: TextView
    private lateinit var showMoreButton: Button

    override fun onFinishInflate() {
        super.onFinishInflate()
        val view = inflate(context, R.layout.expandable_text_view, this)
        textView = view.findViewById(R.id.movie_details_overview)
        showMoreButton = view.findViewById(R.id.show_more)
        showMoreButton.setOnClickListener {
            overviewExpanded = if (overviewExpanded) {
                val animation: ObjectAnimator = ObjectAnimator.ofInt(textView, "maxLines", 3)
                animation.setDuration(200).start()
                showMoreButton.setText(R.string.show_more)
                false
            } else {
                val animation: ObjectAnimator = ObjectAnimator.ofInt(textView, "maxLines", 40)
                animation.setDuration(200).start()
                showMoreButton.setText(R.string.show_less)
                true
            }
        }

    }

    fun setText(text: String) {
        textView.text = text
    }
}
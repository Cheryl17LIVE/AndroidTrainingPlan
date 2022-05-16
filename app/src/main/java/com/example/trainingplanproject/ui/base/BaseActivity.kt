package com.example.trainingplanproject.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.trainingplanproject.R
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {

    private var view: View? = null
    private val loadingView by lazy { view?.findViewById<LinearLayout>(R.id.ll_loading) }

    open fun loading(message: String?= null) {
        if (view == null) {
            view = layoutInflater.inflate(R.layout.layout_loading, null)
            val params = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
            )
            addContentView(view, params)
        } else {
            loadingView?.visibility = View.VISIBLE
            loadingView?.isClickable = true
        }
    }

    /*关闭加载界面*/
    open fun hideLoading() {
        if (view == null) {
            Timber.d("loadingView不存在")
        } else {
            loadingView?.visibility = View.GONE
        }
    }

}
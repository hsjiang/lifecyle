package com.hsjiang.lifecycle.origin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.hsjiang.lifecycle.databinding.ActivityOriginBinding

class ComposeInAndroidViewsActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityOriginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityOriginBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupView()
    }

    private fun setupView() {
        mBinding.composeView.setContent {

        }
        mBinding.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnDetachedFromWindow)
        mBinding.composeView.setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(
                this
            )
        )
        mBinding.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    override fun onDestroy() {
        super.onDestroy()
//        mBinding.composeView.disposeComposition()
    }
}
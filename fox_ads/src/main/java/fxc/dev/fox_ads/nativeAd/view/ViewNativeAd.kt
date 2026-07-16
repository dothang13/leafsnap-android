package fxc.dev.fox_ads.nativeAd.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.children
import com.faltenreich.skeletonlayout.Skeleton
import com.faltenreich.skeletonlayout.SkeletonConfig
import com.faltenreich.skeletonlayout.SkeletonLayout
import com.faltenreich.skeletonlayout.createSkeleton
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import fxc.dev.fox_ads.R
import fxc.dev.fox_ads.databinding.ViewNativeAdBinding
import fxc.dev.fox_ads.nativeAd.NativeAdCalculator
import timber.log.Timber

class ViewNativeAd : FrameLayout {
    private var binding: ViewNativeAdBinding? = null
    private var nativeAdViewLayout: View? = null
    private var skeleton: Skeleton? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        binding = ViewNativeAdBinding.inflate(LayoutInflater.from(context))
        addView(binding?.root)

        // Load attributes
        val styledAttributes = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ViewNativeAd,
            defStyle,
            0
        )
        val layoutNativeAdRes = styledAttributes.getResourceId(
            R.styleable.ViewNativeAd_native_ad_layout,
            R.layout.layout_medium_native_ads
        )
        val maskColor = styledAttributes.getColor(
            R.styleable.ViewNativeAd_mask_color,
            resources.getColor(R.color.viewNativeAdMaskColor, context.theme)
        )
        val shimmerColor = styledAttributes.getColor(
            R.styleable.ViewNativeAd_shimmer_color,
            resources.getColor(R.color.viewNativeAdShimmerColor, context.theme)
        )
        styledAttributes.recycle()

        binding?.run {
            // Inflate and add the native ad layout to ViewFlipper
            nativeAdViewLayout = LayoutInflater.from(context).inflate(layoutNativeAdRes, null)
            viewFlipper.addView(nativeAdViewLayout)

            // Create Skeleton for the inflated layout
            skeleton = nativeAdViewLayout?.createSkeleton(
                config = SkeletonConfig(
                    maskColor = maskColor,
                    maskCornerRadius = 8f,
                    showShimmer = true,
                    shimmerColor = shimmerColor,
                    shimmerDurationInMillis = 1500L,
                    shimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
                    shimmerAngle = 20
                )
            )
            skeleton?.showSkeleton()
        }
    }

    fun setLayoutNativeAd(@LayoutRes newLayoutRes: Int) {

        if (nativeAdViewLayout != null) {
            binding?.viewFlipper?.removeAllViews()
        }

        val newNativeAdViewLayout = LayoutInflater.from(context).inflate(newLayoutRes, null)
        binding?.viewFlipper?.addView(newNativeAdViewLayout)

        nativeAdViewLayout = newNativeAdViewLayout

        skeleton = nativeAdViewLayout?.createSkeleton(
            config = SkeletonConfig(
                maskColor = resources.getColor(R.color.viewNativeAdMaskColor, context.theme),
                maskCornerRadius = 8f,
                showShimmer = true,
                shimmerColor = resources.getColor(R.color.viewNativeAdShimmerColor, context.theme),
                shimmerDurationInMillis = 1500L,
                shimmerDirection = SkeletonLayout.DEFAULT_SHIMMER_DIRECTION,
                shimmerAngle = 20
            )
        )

        skeleton?.showSkeleton()
    }


    fun populate(nativeAd: NativeAd) {
        Timber.d("Populate Native Ad View")
        if (nativeAdViewLayout is NativeAdView) {
            skeleton?.showOriginal()
            NativeAdCalculator.populateNativeAdView(
                nativeAd,
                nativeAdViewLayout as NativeAdView
            )
            binding?.viewFlipper?.displayedChild = 0
        } else if (nativeAdViewLayout is ViewGroup) {
            val viewGroup = nativeAdViewLayout as ViewGroup
            for (child in viewGroup.children) {
                if (child is NativeAdView) {
                    skeleton?.showOriginal()
                    NativeAdCalculator.populateNativeAdView(nativeAd, child)
                    binding?.viewFlipper?.displayedChild = 0
                    break
                }
            }
        } else {
            Timber.e("Native Ad View Layout not contain NativeAdView.")
        }
    }

}

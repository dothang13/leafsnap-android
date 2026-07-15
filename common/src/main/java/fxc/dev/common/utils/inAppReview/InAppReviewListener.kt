package fxc.dev.common.utils.inAppReview

interface InAppReviewListener {
    fun onReviewSuccess()
    fun onReviewFailure()
    fun onRequestReviewFailed()
}
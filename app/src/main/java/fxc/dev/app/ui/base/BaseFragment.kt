package fxc.dev.app.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import fxc.dev.common.Fox
import fxc.dev.fox_tracking.firebase.FirebaseScreenTracker
import fxc.dev.fox_tracking.tracking

abstract class BaseFragment<ViewBindingType : ViewBinding, ViewModelType : BaseViewModel> :
    Fragment(),
    ViewBindingHolder<ViewBindingType> by ViewBindingHolderImpl() {
    protected abstract val viewModel: ViewModelType

    private var _mContext: Context? = null

    protected val mContext: Context
        get() = requireNotNull(_mContext)

    protected val binding: ViewBindingType
        get() = requireBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        _mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = initBinding(
        binding = setupViewBinding(inflater, container),
        lifecycle = viewLifecycleOwner.lifecycle,
        className = this::class.simpleName,
        onBound = null
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        Fox.tracking.lastScreenBeforeSubscription = this::class.java.simpleName
        FirebaseScreenTracker.trackScreen(this::class.java.simpleName) // track screen firebase analytics
    }

    abstract fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ViewBindingType

    abstract fun init(savedInstanceState: Bundle?)

    override fun onDetach() {
        _mContext = null
        super.onDetach()
    }
}

package com.preview.android.presentation.ui.code

import android.os.Bundle
import android.os.CountDownTimer
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.appcompat.app.AlertDialog
import androidx.core.view.updateMargins
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.preview.android.R
import com.preview.android.databinding.FragmentConfirmCodeBinding
import com.preview.android.domain.models.auth.Action
import com.preview.android.presentation.ui.base.BaseFragment
import com.preview.android.presentation.ui.utils.*
import com.jkb.vcedittext.VerificationAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmCodeFragment : BaseFragment(R.layout.fragment_confirm_code) {

    private val viewModel by viewModels<ConfirmCodeViewModel>()
    private lateinit var binding: FragmentConfirmCodeBinding

    private var isAnimationInProcess: Boolean = false
    private var isViewsHidden: Boolean = false
    private var animationDistance = 0f

    private var isKeyboardOpen: Boolean = false
        set(value) {
            field = value
            if (value) hideViews()
            else showViews()
        }

    private lateinit var timer: CountDownTimer

    override val isBackPressedEnabled: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmCodeBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        viewModel.action.observe(viewLifecycleOwner, ::initActionParams)

        viewModel.init()

        val viewTreeObserver = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                binding.root.viewTreeObserver.removeOnGlobalLayoutListener(this)
                animationDistance = binding.root.measuredHeight.toFloat()
                initLogoParams()
                binding.root.initKeyboardStateListener { isOpen -> isKeyboardOpen = isOpen }
            }
        }
        binding.root.viewTreeObserver.addOnGlobalLayoutListener(viewTreeObserver)
    }

    override fun onBackPressed() {
        showChangePhoneDialog()
    }

    private fun initActionParams(action: Action) {
        binding.title.apply {
            applyBoldStyle()
            viewModel.phone.value?.also {
                val title = action.title(it)
                setColoredText(title.txt, title.colors)
            }
        }
        binding.subTitle.apply {
            applyRegularStyle()
            text = action.subTitle
        }
        binding.confirm.apply {
            applyBoldStyle()
            applyButtonClickAnimation()
            text = getString(R.string.confirm)
            setOnClickListener {
                val code = binding.inputCode.text
                if (!code.isNullOrEmpty() && code.length == 4) {
                    confirmCode(binding.inputCode.text.toString())
                }
            }
        }
        binding.changePhone.apply {
            applyBoldStyle()
            applyButtonClickAnimation()
            setTextWithUnderline(getString(R.string.changePhone))
            setOnClickListener {
                showChangePhoneDialog()
            }
        }
        binding.resendCode.apply {
            applyRegularStyle()
            applyButtonClickAnimation()
            text = action.nextCaption()
            isEnabled = action.coolDownPassed()
            setOnClickListener {
                if (action.coolDownPassed()) showResendCodeDialog()
            }
        }
        binding.inputCode.apply {
            applyBoldStyle()
            requestFocus()
            showKeyBoard(this.context)
            setOnVerificationCodeChangedListener(object :
                VerificationAction.OnVerificationCodeChangedListener {
                override fun onVerCodeChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    binding.inputCodeError.gone()
                }

                override fun onInputCompleted(s: CharSequence?) {
                    confirmCode(s.toString())
                }
            })
        }
        binding.inputCodeError.apply {
            applyRegularStyle()
            text = viewModel.config.value?.errorPin.orEmpty()
        }

        startResendButtonTimer()
    }

    private fun handleState(state: ConfirmCodeState) {
        handleLoadingState(state)
        when (state) {
            is ConfirmCodeState.NavigateToOffers -> findNavController().navigate(R.id.navigateToOffersScreen)
            is ConfirmCodeState.InvalidCodeError -> binding.inputCodeError.visible()
            is ConfirmCodeState.ConfirmCodeError -> handleConfirmCodeError()
            is ConfirmCodeState.ResendCodeError -> handleResendCodeError()
            else -> Unit
        }
    }

    private fun handleConfirmCodeError() {
        showErrorDialog {
            confirmCode(binding.inputCode.text.toString())
        }
    }

    private fun handleResendCodeError() {
        showErrorDialog {
            resendCode()
        }
    }

    private fun handleLoadingState(state: ConfirmCodeState) {
        val isLoading = state == ConfirmCodeState.Loading
        binding.changePhone.isEnabled = !isLoading
        binding.confirm.apply {
            isEnabled = !isLoading
            alpha = if (isLoading) 0.6f else 1.0f
        }
        binding.resendCode.isEnabled = !isLoading
    }

    private fun hideViews() {
        if (!isAnimationInProcess && !isViewsHidden) {
            val onAnimationStart: () -> Unit = {
                isAnimationInProcess = true
            }
            val onAnimationEnd: () -> Unit = {
                isAnimationInProcess = false
                isViewsHidden = true
                if (!isKeyboardOpen) {
                    showViews()
                }
            }

            binding.logo.slideUp(
                animationDistance,
                onAnimationStart = onAnimationStart,
                onAnimationEnd = onAnimationEnd
            )
            binding.title.slideUp(
                animationDistance,
                onAnimationStart = onAnimationStart,
                onAnimationEnd = onAnimationEnd
            )
        }
    }

    private fun showViews() {
        if (!isAnimationInProcess && isViewsHidden) {
            val onAnimationStart: () -> Unit = {
                isAnimationInProcess = true
            }
            val onAnimationEnd: () -> Unit = {
                isAnimationInProcess = false
                isViewsHidden = false
                if (isKeyboardOpen) {
                    hideViews()
                }
            }

            binding.logo.slideDown(
                animationDistance,
                onAnimationStart = onAnimationStart,
                onAnimationEnd = onAnimationEnd
            )
            binding.title.slideDown(
                animationDistance,
                onAnimationStart = onAnimationStart,
                onAnimationEnd = onAnimationEnd
            )
        }
    }

    private fun initLogoParams() {
        if (binding.logo.isIntersectedWith(binding.title)) {
            val params = binding.logo.layoutParams as MarginLayoutParams
            params.updateMargins(left = 32.toPx(), right = 32.toPx())
        }
    }

    private fun confirmCode(code: String) {
        binding.root.hideKeyboard()
        viewModel.confirmCode(code)
    }

    private fun showChangePhoneDialog() {
        binding.root.hideKeyboard()
        viewModel.config.value?.also {
            AlertDialog.Builder(requireContext())
                .setTitle(it.dialogPhone.title)
                .setMessage(it.dialogPhone.message)
                .setPositiveButton(it.dialogsPhone.yes) { dialog, _ ->
                    dialog.dismiss()
                    findNavController().navigate(R.id.navigateToRegistrationScreen)
                }
                .setNegativeButton(it.dialogsPhone.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    private fun showResendCodeDialog() {
        binding.root.hideKeyboard()
        viewModel.config.value?.also {
            AlertDialog.Builder(requireContext())
                .setTitle(it.dialogAction.title)
                .setMessage(it.dialogAction.message)
                .setPositiveButton(it.dialogAction.yes) { dialog, _ ->
                    dialog.dismiss()
                    resendCode()
                }
                .setNegativeButton(it.dialogAction.no) { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .create()
                .show()
        }
    }

    private fun resendCode() {
        binding.inputCode.text?.clear()
        viewModel.resendCode()
    }

    private fun startResendButtonTimer() {
        viewModel.action.value?.also {
            if (this::timer.isInitialized) timer.cancel()
            timer = object : CountDownTimer(((it.actionIn * 1000) + 3000).toLong(), 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    binding.resendCode.apply {
                        text = it.nextCaption()
                        isEnabled = it.coolDownPassed()
                    }
                }
                override fun onFinish() = Unit
            }
            timer.start()
        }
    }
}
package com.preview.android.presentation.ui.registration

import android.os.Bundle
import android.view.*
import android.view.ViewGroup.MarginLayoutParams
import androidx.core.content.ContextCompat
import androidx.core.view.updateMargins
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.preview.android.R
import com.preview.android.databinding.FragmentRegistrationBinding
import com.preview.android.domain.models.config.Config
import com.preview.android.domain.models.user.UserPhone
import com.preview.android.presentation.ui.base.BaseFragment
import com.preview.android.presentation.ui.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    private val viewModel by viewModels<RegistrationViewModel>()
    private lateinit var binding: FragmentRegistrationBinding

    private var isAnimationInProcess: Boolean = false
    private var isViewsHidden: Boolean = false
    private var animationDistance = 0f

    private var isKeyboardOpen: Boolean = false
        set(value) {
            field = value
            if (value) hideViews()
            else showViews()
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        viewModel.config.observe(viewLifecycleOwner, ::initConfigParams)
        viewModel.phone.observe(viewLifecycleOwner, ::initPhoneInputContainer)

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

    override fun onPause() {
        viewModel.saveCurrentPhone(
            binding.countryCodePicker.selectedCountryCode.toInt(),
            binding.phoneInputField.text.toString()
        )
        super.onPause()
    }

    private fun initConfigParams(config: Config) {
        binding.title.apply {
            applyBoldStyle()
            setColoredText(config.authTitle.txt, config.authTitle.colors)
        }
        binding.subTitle.apply {
            applyBoldStyle()
            setColoredText(config.authSubTitle.txt, config.authSubTitle.colors)
        }
        binding.register.apply {
            applyBoldStyle()
            applyButtonClickAnimation()
            text = config.authGetAccess
            setOnClickListener {
                validatePhone()
            }
        }
        binding.playLikeGuest.apply {
            applyBoldStyle()
            applyButtonClickAnimation()
            setTextWithUnderline(config.authDemo)
        }
        binding.privacyPolicyTitle.apply {
            applyRegularStyle()
            text = config.authPPAccept
        }
        binding.privacyPolicy.apply {
            applyRegularStyle()
            setColoredText(config.authPP.txt, config.authPP.colors, true)
        }
    }

    private fun handleState(state: RegistrationState) {
        handleLoadingState(state)
        when (state) {
            is RegistrationState.NavigateToConfirmCode -> findNavController().navigate(R.id.navigateToConfirmCodeScreen)
            is RegistrationState.NavigateToOffers -> findNavController().navigate(R.id.navigateToOffersScreen)
            is RegistrationState.Error -> handleError()
            else -> Unit
        }
    }

    private fun handleError() {
        showErrorDialog {
            validatePhone()
        }
    }

    private fun handleLoadingState(state: RegistrationState) {
        val isLoading = state == RegistrationState.Loading
        binding.phoneInputField.isEnabled = !isLoading
        binding.register.apply {
            isEnabled = !isLoading
            alpha = if (isLoading) 0.6f else 1.0f
        }
        binding.playLikeGuest.isEnabled = !isLoading
        //TODO lock click by privacy policy
    }

    private fun initPhoneInputContainer(phone: UserPhone?) {
        val config = viewModel.config.value
        binding.phoneInputField.applyRegularStyle()
        binding.countryCodePicker.apply {
            if (phone != null) {
                binding.countryCodePicker.setCountryForPhoneCode(phone.code)
                binding.phoneInputField.setText(phone.number)
            } else {
                detectSIMCountry(true)
                if (selectedCountryNameCode == "US") {
                    setCountryForNameCode("RU")
                }
            }

            textView_selectedCountry.apply {
                setTextColor(ContextCompat.getColor(requireContext(), R.color.colorAccent))
                applyBoldStyle()
            }
            setCustomMasterCountries(config?.countriesAllowed)
            registerCarrierNumberEditText(binding.phoneInputField)
        }
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
            binding.titleContainer.slideUp(
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
            binding.titleContainer.slideDown(
                animationDistance,
                onAnimationStart = onAnimationStart,
                onAnimationEnd = onAnimationEnd
            )
        }
    }

    private fun initLogoParams() {
        if (binding.logo.isIntersectedWith(binding.titleContainer)) {
            val params = binding.logo.layoutParams as MarginLayoutParams
            params.updateMargins(left = 32.toPx(), right = 32.toPx())
        }
    }

    private fun validatePhone() {
        val config = viewModel.config.value
        val isValid = binding.countryCodePicker.isValidFullNumber
        if (isValid) {
            binding.root.hideKeyboard()
            binding.root.clearFocus()
            viewModel.saveCurrentPhone(
                binding.countryCodePicker.selectedCountryCode.toInt(),
                binding.phoneInputField.text.toString()
            )
            viewModel.register(binding.countryCodePicker.fullNumberWithPlus)
        } else {
            binding.phoneInputField.error = config?.errorNumber.orEmpty()
        }
    }
}
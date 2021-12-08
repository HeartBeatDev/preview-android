package com.preview.android.presentation.ui.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.preview.android.R
import com.preview.android.domain.models.config.Config
import com.preview.android.presentation.ui.base.BaseFragment
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        viewModel.config.observe(viewLifecycleOwner, ::handleConfig)
        viewModel.init()
    }

    private fun handleState(state: SplashState) {
        when (state) {
            is SplashState.NavigateToOffers -> findNavController().navigate(R.id.navigateToOffersScreen)
            is SplashState.NavigateToRegistration -> findNavController().navigate(R.id.navigateToRegistrationScreen)
            is SplashState.NavigateToConfirmCode -> findNavController().navigate(R.id.navigateToConfirmCodeScreen)
            is SplashState.Error -> handleError()
        }
    }

    private fun handleError() {
        showErrorDialog {
            viewModel.init()
        }
    }

    private fun handleConfig(config: Config) {
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.DEBUG, OneSignal.LOG_LEVEL.NONE)
        OneSignal.initWithContext(requireContext())
        OneSignal.setAppId(config.keyOnesignal)

        val callback = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(p0: MutableMap<String, Any>?) = Unit
            override fun onConversionDataFail(p0: String) = Unit
            override fun onAppOpenAttribution(p0: Map<String, String>) = Unit
            override fun onAttributionFailure(p0: String) = Unit
        }
        AppsFlyerLib.getInstance().init(config.keyAppsflyer, callback, requireContext())
        AppsFlyerLib.getInstance().start(requireContext())
    }
}
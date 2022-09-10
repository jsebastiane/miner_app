package sebastian.company.min3rapp.ui.crypto_data

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.formatPercentage
import sebastian.company.min3rapp.ui.ViewPagerFragmentDirections

@AndroidEntryPoint
class DataFragment : Fragment() {

//    private var _binding: FragmentDataBinding? = null
//    val binding get() = _binding!!
//    private val viewModel: DataViewModel by viewModels()
//    private val marketShareAdapter = MarketShareAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DataScreen()
            }
        }
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.assetPricesCardView.setOnClickListener {
//            goToCoinPrices()
//        }
//        binding.marketShareRecycler.apply {
//            adapter = marketShareAdapter
//            layoutManager = LinearLayoutManager(context)
//        }
//        observeViewModel()
//        binding.refreshLayout.setOnRefreshListener {
//            viewModel.getGlobalCoinData()
//        }
//        viewModel.getGlobalCoinData()
//    }
//
//    private fun goToCoinPrices() {
//        val action = ViewPagerFragmentDirections.actionViewPagerFragmentToCoinPricesFragment()
//        Navigation.findNavController(binding.root).navigate(action)
//    }
//
//    private fun observeViewModel() {
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.globalCoinState.collectLatest {
//                binding.refreshLayout.isRefreshing = false
//                if (it.error.isEmpty()) {
//                    it.marketData.marketCapChange24h?.let { mktCapChange ->
//                        val change = mktCapChange.formatPercentage()
//                        binding.marketCapChange.text = change
//                        when {
//                            mktCapChange < 0.0 -> {
//                                binding.marketCapChange.setTextColor(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.red
//                                    )
//                                )
//                                binding.marketCapChangeDirection.setImageResource(
//                                    R.drawable.ic_arrow_down)
//                            }
//                            else -> {
//                                binding.marketCapChange.setTextColor(
//                                    ContextCompat.getColor(
//                                        requireContext(),
//                                        R.color.green
//                                    )
//                                )
//                                binding.marketCapChangeDirection.setImageResource(
//                                    R.drawable.ic_arrow_up)
//                            }
//                        }
//                    }
//                    it.marketData.marketShareMap?.let { marketShares ->
//                        marketShareAdapter.setData(marketShares) }
//
//                } else {
//                    val errorMessage = "error"
//                    binding.marketCapChange.text = errorMessage
//                    binding.marketCapChangeDirection.setImageResource(
//                        R.drawable.ic_error
//                    )
//                }
//            }
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }


}
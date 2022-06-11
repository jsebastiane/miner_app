package sebastian.company.min3rapp.ui.crypto_data.coin_info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentCoinPricesBinding
import sebastian.company.min3rapp.domain.model.Coin
import sebastian.company.min3rapp.ui.crypto_data.components.CoinAction

@AndroidEntryPoint
class CoinPricesFragment : Fragment(), CoinAction {

    private var _binding: FragmentCoinPricesBinding? = null
    val binding get() = _binding!!
    private val viewModel: CoinPricesViewModel by viewModels()
    private var coinsAdapter = CoinsRecyclerViewAdapter(this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCoinPricesBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.cryptoCoinsRecycler.apply {
            adapter = coinsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.searchViewCoins.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchQuery = newText
                viewModel.filterCoins()
                return true
            }

        })

        binding.refreshLayout.setOnRefreshListener {
            viewModel.getCoins()
        }


        viewModel.getCoins()
    }

    private fun observeViewModel(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.coinsState.collectLatest {
                if(it.error.isEmpty()){
                    binding.cryptoCoinsRecycler.visibility = View.VISIBLE
                    binding.errorMessage.visibility = View.GONE
                    coinsAdapter.setData(it.coins)
                    binding.refreshLayout.isRefreshing = false
                    binding.cryptoCoinsRecycler.scrollToPosition(0)
                }else{
                    binding.errorMessage.visibility = View.VISIBLE
                    binding.cryptoCoinsRecycler.visibility = View.GONE
                    binding.errorMessage.text = getString(R.string.coins_data_error)
                    binding.refreshLayout.isRefreshing = false
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(coin: Coin) {

    }

}
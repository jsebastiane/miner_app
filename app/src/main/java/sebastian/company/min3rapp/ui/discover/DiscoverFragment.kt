package sebastian.company.min3rapp.ui.discover

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.UserPrefs
//import sebastian.company.min3rapp.databinding.FragmentDiscoverBinding
import sebastian.company.min3rapp.ui.ViewPagerFragmentDirections

@AndroidEntryPoint
class DiscoverFragment : Fragment() {

//    private var _binding : FragmentDiscoverBinding? = null
//    private val binding get() = _binding!!
//    private val viewModel: DiscoverViewModel by viewModels()
//    private lateinit var userPreferences: UserPrefs
    //Is this correct?
//    private val userPreferences = UserPrefs(activity!!)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
//        val view = binding.root
//        return view
        return ComposeView(requireContext()).apply {
            setContent {
                DiscoverMenu(onNavReqReceived = {discoverItem ->
                    openResourceLink(discoverItem.websiteUrl)
                })
            }
        }
    }

    private fun openResourceLink(url: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity?.startActivity(intent)
    }

}
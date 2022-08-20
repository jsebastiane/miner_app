package sebastian.company.min3rapp.ui.policy

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentContactPolicyBinding

//I believe i need this if im implementing viewmodels otherwise not necessary
@AndroidEntryPoint
class ContactPolicyFragment : Fragment() {
    private var _binding: FragmentContactPolicyBinding? = null
    private val binding get() = _binding!!


        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentContactPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.privacyPolicyMessage.movementMethod = LinkMovementMethod.getInstance()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
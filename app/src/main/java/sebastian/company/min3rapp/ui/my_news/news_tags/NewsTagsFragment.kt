package sebastian.company.min3rapp.ui.my_news.news_tags

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch
import sebastian.company.min3rapp.common.Constants
import sebastian.company.min3rapp.databinding.FragmentNewsTagsBinding
import sebastian.company.min3rapp.domain.model.NewsTag


class NewsTagsFragment : Fragment() {

    private var _binding: FragmentNewsTagsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NewsTagsViewModel by viewModels()
    private var myNewsTags: ArrayList<String> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsTagsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        binding.doneButton.setOnClickListener {
            closeAndSave()
        }


    }

    private fun observeViewModel() {
        viewModel.myTags.observe(viewLifecycleOwner, Observer { tags ->
            if(tags.isNullOrEmpty()){
                createTags()
            }else{
                myNewsTags.addAll(tags)
                createTags()
            }
        })
        //observer for topics yet to choose
    }


    private fun createTags() {
        val tags = Constants.COINS_AVAILABLE.split(',').toMutableList()
        viewLifecycleOwner.lifecycleScope.launch {
            for (i in tags) {
                val chip = Chip(context)
                chip.setOnCheckedAction()
                chip.text = i
                chip.isChecked = myNewsTags.contains(i)
                binding.tagChipGroup.addView(chip)
            }
        }

    }



    private fun closeAndSave() {
        viewModel.saveComplete.observe(viewLifecycleOwner, Observer { saveComplete ->
            saveComplete?.let {
                findNavController().navigateUp()
            }
        })
        if(myNewsTags.isNotEmpty()){
            viewModel.saveNewTags(myNewsTags)
        }else{
            findNavController().navigateUp()
        }
    }


    private fun Chip.setOnCheckedAction(){
        this.setOnClickListener {
            if(this.isChecked){
                if(myNewsTags.size < 5){
                    myNewsTags.add(this.text.toString())
                    Log.d("ChipCheck", "$myNewsTags")
                }else{
                    Toast.makeText(context, "Already 5 Selected", Toast.LENGTH_SHORT).show()
                    this.isChecked = false
                }
            }else{
                myNewsTags.removeAll { it == this.text.toString() }
            }
        }
    }


}



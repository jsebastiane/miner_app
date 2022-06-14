package sebastian.company.min3rapp.ui

import android.os.Bundle
import android.view.*
import android.widget.HorizontalScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.FragmentViewPagerBinding
import sebastian.company.min3rapp.ui.discover.DiscoverFragment
import sebastian.company.min3rapp.ui.miner_home.HomeFragment
import sebastian.company.min3rapp.ui.my_news.MyNewsFragment
import sebastian.company.min3rapp.ui.crypto_data.DataFragment


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private var isOnMyNews: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            MyNewsFragment(),
            DataFragment(),
            DiscoverFragment()
        )

        val adapter = ViewPageAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle,
        )


        viewPager = binding.viewPager
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 1

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (position == 1){
                    updateOptionsMenu(true)
                }else{
                    updateOptionsMenu(false)
                }

            }
        })


        val tabLayout = binding.tableLayout
        TabLayoutMediator(tabLayout, viewPager) {tab, position ->
            when(position){
                0 -> {
                    tab.text = "Home"
                }
                1 -> {
                    tab.text = "My News"
                }
                2 -> {
                    tab.text = "Data"
                }
                3 -> {
                    tab.text = "Discover"
                }
            }
        }.attach()
        

        return view
    }

    private fun updateOptionsMenu(myNewsPage: Boolean){
        isOnMyNews = myNewsPage
        requireActivity().invalidateOptionsMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.edit_action ->{
                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToNewsTagsFragment()
                Navigation.findNavController(binding.root).navigate(action)
                true
            }else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.mynews_menu, menu)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.edit_action)
        item.isVisible = isOnMyNews
    }


}
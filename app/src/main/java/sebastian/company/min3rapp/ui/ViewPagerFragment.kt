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
import sebastian.company.min3rapp.ui.discuss.DiscussFragment


class ViewPagerFragment : Fragment() {

    private var _binding: FragmentViewPagerBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewPager: ViewPager2
    private var isOnMyNews: Boolean = false
    private var isOnDiscover: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
//            MyNewsFragment(),
            DiscussFragment(),
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
        viewPager.offscreenPageLimit = 3
        viewPager.isUserInputEnabled = false

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    3 -> {
                        updateOptionsMenu(3)
                    }
                    else -> {
                        updateOptionsMenu(-1)
                    }
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
//                    tab.text = "My News"
                    tab.text = "Discuss"
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

    private fun updateOptionsMenu(page: Int){
        when(page){
            3 -> {isOnDiscover = true
                isOnMyNews = false}
            else -> {isOnDiscover = false
                isOnMyNews = false}
        }
        requireActivity().invalidateOptionsMenu()


    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.info_action ->{
                val action = ViewPagerFragmentDirections.actionViewPagerFragmentToContactPolicyFragment()
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
        val editItem = menu.findItem(R.id.edit_action)
        val infoItem = menu.findItem(R.id.info_action)
        editItem.isVisible = isOnMyNews
        infoItem.isVisible = isOnDiscover
    }


}
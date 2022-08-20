package sebastian.company.min3rapp.ui


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.ads.*
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import dagger.hilt.android.AndroidEntryPoint
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navListener: NavController.OnDestinationChangedListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        MobileAds.initialize(this)

        FirebaseApp.initializeApp(/*context=*/this)
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance()
        )

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        binding.toolbarHeader.text = supportActionBar?.title
        supportActionBar?.setDisplayShowTitleEnabled(false)

        navListener = NavController.OnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.setDisplayShowTitleEnabled(true)
            if(destination.displayName == "sebastian.company.min3rapp:id/newsWebFragment"){
                supportActionBar?.title = ""
                binding.toolbarHeader.text = supportActionBar?.title
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }else if(destination.displayName == "sebastian.company.min3rapp:id/discussDetailFragment") {
                supportActionBar?.title = "comments"
                binding.toolbarHeader.text = supportActionBar?.title
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }else if(destination.displayName == "sebastian.company.min3rapp:id/commentDetailsFragment"){
                supportActionBar?.title = "replies"
                binding.toolbarHeader.text = supportActionBar?.title
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }else{
                supportActionBar?.title = getString(R.string.app_name)
                binding.toolbarHeader.text = supportActionBar?.title
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        }



        navController = Navigation.findNavController(this, R.id.fragmentContainerView)
        navController.addOnDestinationChangedListener(navListener)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)



    }


    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(navListener)
    }

    override fun onPause() {
        navController.removeOnDestinationChangedListener(navListener)
        super.onPause()
    }



}
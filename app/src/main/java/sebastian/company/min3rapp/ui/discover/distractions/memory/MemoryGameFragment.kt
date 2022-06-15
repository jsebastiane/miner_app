package sebastian.company.min3rapp.ui.discover.distractions.memory

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import sebastian.company.min3rapp.R
import sebastian.company.min3rapp.common.UserPrefs
import sebastian.company.min3rapp.databinding.FragmentMemoryGameBinding
import sebastian.company.min3rapp.databinding.GameOverDialogBinding

@AndroidEntryPoint
class MemoryGameFragment : Fragment() {

    private var _binding: FragmentMemoryGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MemoryGameViewModel by viewModels()
    private lateinit var userPreferences: UserPrefs
    private lateinit var mAdView: AdView

    //gameTimer
    private var testCountDownTimer: Job? = null
    private var pregameCountDownTimer: Job? = null

    //Maximum number of rounds with X number of cells
    private val maxRounds = 4

    //Current round in
    private var currentRound: Int = 1

    //Last number that was selected by player
    private var currentNumber: Int = 0
    private var currentScore: Int = 0
    //Number of cells the player has to remember - increases every time maxRound is achieved
    private var cellNumber: Int = 4
    private var lives: Int = 3




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemoryGameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            userPreferences = UserPrefs(it)
        }

        viewModel.memoryGameHighScore.observe(viewLifecycleOwner, Observer {highscore ->
            highscore?.let{
                viewModel.setLocalHighScore(it)
            }
        })



        //Starting game here
        restartGame()

    }

    private fun testTimer(seconds: Int) = flow{
        for(i in seconds downTo 0){
            emit(i)
            delay(1000L)
        }
    }



    private fun setUpGame(numbersList: List<Int>) {
        //It will always be a TextView as it is set in the layout

        val livesText = "\uD83D\uDC7E\uD83D\uDC7E\uD83D\uDC7E"
        binding.lives.text = livesText

        for (i in numbersList.indices) {
            val cell = binding.gameGridView.getChildAt(i) as? TextView
            //list is populated with 0's that make up for empty spaces on board
            if (numbersList[i] != 0) {
                cell?.text = numbersList[i].toString()
                cell?.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_white))
            } else {
                cell?.text = ""
            }
        }
        binding.gameTimer.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_pink))

        val seconds = getGameSeconds()

        pregameCountDownTimer = testTimer(seconds)
            .onEach {
                println("PREGAME: $it")
                binding.gameTimer.text = it.toString()
            if(it == 0){
                startGame()
            }}
            .cancellable()
            .launchIn(viewLifecycleOwner.lifecycleScope)


    }



    private fun startGame() {
        //show gridLayout as it will be hidden beforehand

        pregameCountDownTimer?.cancel()
        pregameCountDownTimer = null

        for (i in binding.gameGridView.children) {
            val cell = i as? TextView
            if (cell?.text?.isEmpty() == false) {
                cell.setOnClickAction()
                cell.setBackgroundResource(R.drawable.purple_gradient_roundcorners)
                cell.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.transparent
                    )
                )
            }
        }
        //Set timer back to white as it is pink when counting down the time the player has to
        //memorise the numbers
        binding.gameTimer.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.brand_white
            )
        )

        val seconds = getGameSeconds() - 1

        testCountDownTimer = testTimer(seconds)
            .onEach {println("IN PLAY: $it")
                binding.gameTimer.text = it.toString()
            if(it == 0){
                println("Timer Done")
                gameOver()
            }}
            .cancellable()
            .launchIn(viewLifecycleOwner.lifecycleScope)


    }






    //Add interaction to cells with playable numbers ( >0 )
    private fun TextView.setOnClickAction() {
        val number = this.text.toString().toInt()
        this.setOnClickListener {
            when {

                //end game when it's higher than previous and equal to max
                number == currentNumber + 1 && number == cellNumber -> {
                    this.setBackgroundResource(R.drawable.brandblue_round)
                    this.setOnClickListener(null)
                    currentScore += 1
                    binding.score.text = currentScore.toString()

                    testCountDownTimer?.cancel()
                    testCountDownTimer = null
                    nextLevel()
                }

                number == currentNumber + 1 && number < cellNumber -> {
                    this.setBackgroundResource(R.drawable.brandblue_round)
                    this.setOnClickListener(null)
                    currentScore += 1
                    binding.score.text = currentScore.toString()
                    currentNumber += 1
                }

                else -> {
                    when (lives) {
                        3 -> {
                            lives -= 1
                            binding.lifeThree.visibility = View.GONE
                        }
                        2 -> {
                            lives -= 1
                            binding.lifeTwo.visibility = View.GONE
                        }
                        else -> {
                            binding.lifeOne.visibility = View.GONE
                            gameOver()
                        }
                    }
                }

            }

        }
    }



    private fun nextLevel() {
        //Current number is the last selected number on the board
        //max rounds is the how many times player sees X amount of cells before moving to next level
        if (currentRound == maxRounds) {
            currentNumber = 0
            currentRound = 0
            //24 is the max number of cells that can be placed on the board
            if(cellNumber < 24){
                cellNumber += 1
            }
            resetBoard()
        }
        if (currentRound < maxRounds) {
            currentNumber = 0
            currentRound += 1
            resetBoard()
        }

    }


    private fun restartGame() {
        //set level to 0
        binding.lifeOne.visibility = View.VISIBLE
        binding.lifeTwo.visibility = View.VISIBLE
        binding.lifeThree.visibility = View.VISIBLE
        currentRound = 0
        cellNumber = 4
        currentNumber = 0
        currentScore = 0
        lives = 3
        binding.score.text = currentScore.toString()
        resetBoard()

    }

    private fun resetBoard() {
        val boardStartTime = "0"
        binding.gameTimer.text = boardStartTime
        for (i in binding.gameGridView) {
            val cell = i as? TextView
            cell?.setOnClickListener(null)
            cell?.setBackgroundResource(R.drawable.brandblue_round)
            cell?.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_darkblue))
        }
        setUpGame(viewModel.prepareCells(cellNumber))
    }

    private fun gameOver() {
        //Dialog to play again
        //------------------------------------------------------
        testCountDownTimer?.cancel()
        testCountDownTimer = null
//        countDownTimer.cancel()
        //------------------------------------------------------

        //Check if score is new high score and update accordingly
        viewModel.updateHighScore(currentScore)
        val gameOverDialog = GameOverDialogBinding.inflate(LayoutInflater.from(context))
        activity?.let {
            mAdView = gameOverDialog.adView
            val adRequest = AdRequest.Builder().build()
            mAdView.loadAd(adRequest)
            val builder = AlertDialog.Builder(it)
            builder.setView(gameOverDialog.root)

            val dialog = builder.create()
            gameOverDialog.playAgainButton.setOnClickListener {

                restartGame()
                dialog.dismiss()
            }
            gameOverDialog.leaveButton.setOnClickListener {
                dialog.dismiss()
                closeGame()
            }
            dialog.setCancelable(false)
//            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

//            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.show()

        }
    }

    private fun getGameSeconds(): Int{
        return when{
            cellNumber <= 6 -> 5
            cellNumber in 7..9 -> 9
            cellNumber in 10..13 -> 10
            cellNumber in 14..17 -> 15
            else -> 17
        }
    }




    private fun closeGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        testCountDownTimer?.cancel()
        testCountDownTimer = null

        pregameCountDownTimer?.cancel()
        pregameCountDownTimer = null

        _binding = null
    }


}

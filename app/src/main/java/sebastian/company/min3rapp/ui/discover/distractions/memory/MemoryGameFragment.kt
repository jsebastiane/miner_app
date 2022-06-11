package sebastian.company.min3rapp.ui.discover.distractions.memory

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
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

    private var currentLevel: Int = 1
    private var currentNumber: Int = 0
    private var currentScore: Int = 0
    private var cellNumber: Int = 6
    private var lives: Int = 3

    //Game timer
    private lateinit var countDownTimer: CountDownTimer
    private lateinit var pregameTimer: CountDownTimer


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

        //Initialise game timer
        val seconds = (cellNumber + 1) * 1000
        countDownTimer = object : CountDownTimer(seconds.toLong(), 1000) {
            override fun onTick(p0: Long) {
                val time = "0:0${(p0 / 1000)}"
                binding.gameTimer.text = time
            }

            override fun onFinish() {
                gameOver()
            }

        }

        //Initialise pregame timer
        pregameTimer = object : CountDownTimer(seconds.toLong(), 1000) {
            override fun onTick(p0: Long) {
                val time = "0:0${(p0 / 1000)}"
                binding.gameTimer.text = time
            }

            override fun onFinish() {
                startGame()
            }

        }

        //Starting game here
        restartGame()

    }



    private fun setUpGame(numbersList: List<Int>) {
        //It will always be a TextView as it is set in the layout

        val livesText = "\uD83D\uDC7E\uD83D\uDC7E\uD83D\uDC7E"
        binding.lives.text = livesText

        for (i in numbersList.indices) {
            val cell = binding.gameGridView.getChildAt(i) as? TextView
            if (numbersList[i] != 0) {
                cell?.text = numbersList[i].toString()
                cell?.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_white))
            } else {
                cell?.text = ""
            }
        }
        binding.gameTimer.setTextColor(ContextCompat.getColor(requireContext(), R.color.brand_pink))
        //Type 1 means the timer is a pre-game timer
        pregameTimer.start()

    }

    private fun startGame() {
        //show gridLayout as it will be hidden beforehand
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
        countDownTimer.start()
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
                    countDownTimer.cancel()
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
        //If level is currently 5 then set to 0 and add 1 to cellNumber
        if (currentLevel == 5) {
            currentNumber = 0
            currentLevel = 0
            cellNumber += 1
            resetBoard()
        }
        if (currentLevel < 5) {
            currentNumber = 0
            currentLevel += 1
            resetBoard()
        }

        //Reset board
    }

    private fun restartGame() {
        //set level to 0
        binding.lifeOne.visibility = View.VISIBLE
        binding.lifeTwo.visibility = View.VISIBLE
        binding.lifeThree.visibility = View.VISIBLE
        currentLevel = 0
        cellNumber = 6
        currentNumber = 0
        currentScore = 0
        lives = 3
        binding.score.text = currentScore.toString()
        resetBoard()

    }

    private fun resetBoard() {
        val boardStartTime = "0:00"
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
        countDownTimer.cancel()
        //Check if score is new high score and update accordingly
        viewModel.updateHighScore(currentScore)
        val gameOverDialog = GameOverDialogBinding.inflate(LayoutInflater.from(context))
        activity?.let {
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


    private fun closeGame() {
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Toast.makeText(context, "Closed", Toast.LENGTH_SHORT).show()
        countDownTimer.cancel()
        pregameTimer.cancel()
        _binding = null
    }


}

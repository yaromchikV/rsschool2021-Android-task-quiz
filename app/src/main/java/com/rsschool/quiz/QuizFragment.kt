package com.rsschool.quiz

import android.R
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.rsschool.quiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    private var launcher: Launcher? = null

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        launcher = context as Launcher
    }

    private fun setDataToFragment(questionNumber: Int, answers: MutableList<Int>) {
        // Установка цвета статус-бара в зависимости от темы
        val typedValue = TypedValue()
        context?.theme?.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
        ObjectAnimator.ofArgb(activity?.window, "statusBarColor", activity?.window?.statusBarColor!!, typedValue.data).start()

        // Отображение вопроса, вариантов ответа и кнопок
        binding.toolbar.title = "Question $questionNumber"
        if (questionNumber == 1) {
            binding.toolbar.navigationIcon = null
            binding.previousButton.isEnabled = false
        } else if (questionNumber == Database.questions.size) {
            binding.nextButton.text = "Submit"
        }

        binding.question.text = Database.questions[questionNumber - 1].question
        binding.optionOne.text = Database.questions[questionNumber - 1].answerOptions[0].first
        binding.optionTwo.text = Database.questions[questionNumber - 1].answerOptions[1].first
        binding.optionThree.text = Database.questions[questionNumber - 1].answerOptions[2].first
        binding.optionFour.text = Database.questions[questionNumber - 1].answerOptions[3].first
        binding.optionFive.text = Database.questions[questionNumber - 1].answerOptions[4].first

        when (answers[questionNumber - 1]) {
            1 -> binding.optionOne.isChecked = true
            2 -> binding.optionTwo.isChecked = true
            3 -> binding.optionThree.isChecked = true
            4 -> binding.optionFour.isChecked = true
            5 -> binding.optionFive.isChecked = true
            else -> binding.nextButton.isEnabled = false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val questionNumber = arguments?.getInt(QUESTION_NUMBER_TAG)!!
        val answers = arguments?.get(ANSWERS_TAG) as MutableList<Int>

        setDataToFragment(questionNumber, answers)

        // Обработка нажатия radioButton
        binding.radioGroup.setOnCheckedChangeListener { _, checkedID ->
            if (checkedID != -1) {
                binding.nextButton.isEnabled = true
                val radioButtonID = resources.getResourceEntryName(binding.radioGroup.checkedRadioButtonId)
                answers[questionNumber - 1] = when (radioButtonID) {
                    "option_one" -> 1
                    "option_two" -> 2
                    "option_three" -> 3
                    "option_four" -> 4
                    "option_five" -> 5
                    else -> 0
                }
            }
        }

        // Функция, открывающая предыдущий/следующий фрагмент
        fun openFragment(whichWay: Int) {
            launcher?.openQuizFragment(questionNumber + whichWay, answers)
            // Установка темы
            context?.theme?.applyStyle(Database.questions[questionNumber - 1 + whichWay].theme, true)
        }

        // Обработка нажатия кнопки "Next"
        binding.nextButton.setOnClickListener {
            if (questionNumber != answers.size) {
                openFragment(NEXT)
            } else {
                launcher?.openFinalFragment(answers)
                ObjectAnimator.ofArgb(activity?.window, "statusBarColor", activity?.window?.statusBarColor!!, Color.WHITE).start()
            }
        }

        // Обработка нажатия кнопки "Previous"
        binding.previousButton.setOnClickListener {
            openFragment(PREVIOUS)
        }

        // Обработка нажатия кнопки '<' на Toolbar'е
        binding.toolbar.setNavigationOnClickListener {
            openFragment(PREVIOUS)
        }

        // Обработка нажатия системной кнопки '<'
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (questionNumber != 1)
                    openFragment(PREVIOUS)
                else {
                    if (backPressed + 2000 > System.currentTimeMillis())
                        launcher?.closeApp()
                    else
                        Toast.makeText(context, "Press once again to exit", Toast.LENGTH_SHORT).show()
                    backPressed = System.currentTimeMillis()
                }
            }
        })
    }

    private val PREVIOUS = -1
    private val NEXT = 1
    private var backPressed: Long = 0

    companion object {
        @JvmStatic
        fun newInstance(questionNumber: Int, answers: MutableList<Int>) =
                QuizFragment().apply {
                    arguments = bundleOf(
                            QUESTION_NUMBER_TAG to questionNumber,
                            ANSWERS_TAG to answers
                    )
                }

        private const val QUESTION_NUMBER_TAG = "questionNumber"
        private const val ANSWERS_TAG = "answers"
    }
}
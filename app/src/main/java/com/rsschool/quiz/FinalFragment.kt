package com.rsschool.quiz

import android.R
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import com.rsschool.quiz.databinding.FragmentFinalBinding

class FinalFragment : Fragment() {

    private var launcher: Launcher? = null

    private var _binding: FragmentFinalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val answers = arguments?.get(ANSWERS_TAG) as MutableList<Int>
        var result = 0

        for (i in answers.indices) {
            if (Database.questions[i].answerOptions[answers[i] - 1].second)
                result += 1
        }

        binding.result.text = "Result: $result/${answers.size}"

        binding.buttonShare.setOnClickListener {
            var text = "Your result: $result/${answers.size}\n\n"
            for (i in answers.indices) {
                text += "${i + 1}) ${Database.questions[i].question}\n" +
                        "Your answer: ${Database.questions[i].answerOptions[answers[i] - 1].first}\n\n"
            }
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_SUBJECT, "Quiz results")
                putExtra(Intent.EXTRA_TEXT, text)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        binding.buttonRepeat.setOnClickListener {
            launcher?.openTheFirstQuestion()
            context?.theme?.applyStyle(Database.questions[0].theme, true)
        }

        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                launcher?.openTheFirstQuestion()
                context?.theme?.applyStyle(Database.questions[0].theme, true)
            }
        })

        binding.buttonClose.setOnClickListener {
            launcher?.closeApp()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(answers: MutableList<Int>) =
                FinalFragment().apply {
                    arguments = bundleOf(
                            ANSWERS_TAG to answers
                    )
                }

        private const val ANSWERS_TAG = "answers"
    }
}


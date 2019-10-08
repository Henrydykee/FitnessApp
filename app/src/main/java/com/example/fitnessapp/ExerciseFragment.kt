package com.example.fitnessapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import kotlinx.android.synthetic.*
import pl.droidsonroids.gif.GifImageView

class ExerciseFragment : Fragment() {

    data class Exercise(
        val exerciseType : String,
        val exerciseCount : Int
    )
    private val exercises : MutableList<Exercise> = mutableListOf(
        Exercise(exerciseType = "exercise_one",exerciseCount = 8),
        Exercise(exerciseType = "exercise_two",exerciseCount = 5),
        Exercise(exerciseType = "exercise_three",exerciseCount = 10),
        Exercise(exerciseType = "exercise_four",exerciseCount = 15),
        Exercise(exerciseType = "exercise_five",exerciseCount = 20)
    )

     lateinit var nextButton : Button
    lateinit var exitButton : Button
    lateinit var imageView: GifImageView
    lateinit var textView: TextView

    private lateinit var currentExercise : Exercise
    private  var count : Int =0
    private var exerciseIndex : Int = 0
    private var exerciseSize = Math.min((exercises.size+1)/2,3)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       val v = inflater.inflate(R.layout.fragment_exercise, container, false)
        nextButton = v.findViewById(R.id.next_button)
        exitButton = v.findViewById(R.id.exit_button)
        imageView = v.findViewById(R.id.exercise_image_view)
        textView = v.findViewById(R.id.exercise_text_view   )

        randomiseExercise()
        nextButton.setOnClickListener{ view:View ->
            exerciseIndex ++
            if (exerciseIndex < exerciseSize){
                currentExercise = exercises[exerciseIndex]
                setExercise()
            }else{
                view.findNavController().navigate(R.id.action_exerciseFragment_to_wellDoneFragment)
            }
        }
        exitButton.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_exerciseFragment_to_tryAgainFragment)
        )
        return v
    }


   private fun randomiseExercise() {
        exercises.shuffle()
        exerciseIndex = 2
        setExercise()
    }

    private fun setExercise() {
        currentExercise = exercises[exerciseIndex]
        count = currentExercise.exerciseCount
        textView.text = String.format(getString(R.string.exercise_text_view),count)
        imageView.setImageResource(resources.getIdentifier(currentExercise.exerciseType,"drawable",
            (activity as AppCompatActivity).packageName))
        (activity as AppCompatActivity).supportActionBar?.title = String.format(getString(R.string.title_android_fitness_exercise),exerciseIndex +1,exerciseSize)
    }


}

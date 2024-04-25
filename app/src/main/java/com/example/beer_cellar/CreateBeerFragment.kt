package com.example.beer_cellar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer_cellar.databinding.FragmentCreateBeerBinding
import com.example.beer_cellar.databinding.FragmentDetailsBinding
import com.example.beer_cellar.models.Beer
import com.example.beer_cellar.models.BeerViewModel

class CreateBeerFragment : Fragment() {
    // TODO: Create gestures like swipe etc

    private var _binding: FragmentCreateBeerBinding? = null
    private val beerViewModel : BeerViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBeerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCreateBeer.setOnClickListener {
            val beer = Beer(
                1,
                "123@213.com",
                "Fatons Brewery",
                binding.edittextCreateBeerName.text.toString().trim(),
                binding.edittextCreateBeerStyle.text.toString().trim(),
                binding.edittextCreateBeerAbv.text.toString().trim().toDouble(),
                binding.edittextCreateBeerVolume.text.toString().trim().toDouble(),
                "",
                binding.edittextCreateBeerHowMany.text.toString().trim().toInt()
            )

            Log.d("APPLE", "added $beer")
            beerViewModel.add(beer)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
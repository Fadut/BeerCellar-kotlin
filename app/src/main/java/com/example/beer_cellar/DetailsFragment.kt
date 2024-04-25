package com.example.beer_cellar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.beer_cellar.databinding.FragmentDetailsBinding
import com.example.beer_cellar.databinding.FragmentLogInBinding
import com.example.beer_cellar.models.Beer
import com.example.beer_cellar.models.BeerViewModel

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val beerViewModel: BeerViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val detailsFragmentArgs: DetailsFragmentArgs = DetailsFragmentArgs.fromBundle(bundle)
        val position = detailsFragmentArgs.position
        val beer = beerViewModel[position]
        if (beer == null)
        {
            binding.textviewDetailsBeer.text = "No such beer"
            return
        }

        binding.edittextUpdateBeerName.setText(beer.name)
        binding.edittextUpdateBeerStyle.setText(beer.style)
        binding.edittextUpdateBeerAbv.setText(beer.abv.toString())
        binding.edittextUpdateBeerVolume.setText(beer.volume.toString())
        binding.edittextUpdateBeerHowMany.setText(beer.howMany.toString())

        binding.buttonUpdateBeer.setOnClickListener {
            val updatedBeer = Beer(
                beer.id,
                "123@213.com",
                "Fatons Brewery",
                binding.edittextUpdateBeerName.text.toString().trim(),
                binding.edittextUpdateBeerStyle.text.toString().trim(),
                binding.edittextUpdateBeerAbv.text.toString().trim().toDouble(),
                binding.edittextUpdateBeerVolume.text.toString().trim().toDouble(),
                "",
                binding.edittextUpdateBeerHowMany.text.toString().trim().toInt()
            )

            Log.d("APPLE", "updated $beer")
            beerViewModel.update(updatedBeer)
            findNavController().popBackStack()

        }

        binding.buttonDeleteBeer.setOnClickListener {
            beerViewModel.delete(beer.id)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
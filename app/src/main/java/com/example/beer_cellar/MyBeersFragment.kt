package com.example.beer_cellar

import android.os.Bundle
import android.text.Layout.Directions
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.GestureDetectorCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beer_cellar.databinding.FragmentMyBeersBinding
import com.example.beer_cellar.models.Beer
import com.example.beer_cellar.models.BeerViewModel
import com.example.beer_cellar.models.MyAdapter

class MyBeersFragment : Fragment() {

    private var _binding: FragmentMyBeersBinding? = null
    private val beerViewModel: BeerViewModel by activityViewModels()

    private val binding get() = _binding!!
    private lateinit var mDetector: GestureDetectorCompat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyBeersBinding.inflate(inflater, container, false)
        mDetector = GestureDetectorCompat(requireContext(), MyGestureListener())
        val rootView = binding.root
        rootView.setOnTouchListener { view, motionEvent ->
            mDetector.onTouchEvent(motionEvent)
            Log.d("APPLE", "Touch" + motionEvent.x + " " + motionEvent.y)
            true
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // beerViewModel.reload()
        beerViewModel.getBeersByUser("123@213.com")

        beerViewModel.beersLiveData.observe(viewLifecycleOwner) { beers ->
            if (beers.isNullOrEmpty()) {
                binding.textviewMessageMyBeers.text = "No beers detected"
            } else {

                val adapter = MyAdapter(beers) { position ->
                    val action = MyBeersFragmentDirections.actionMyBeersFragmentToDetailsFragment(position)
                    findNavController().navigate(action /*R.id.action_MyBeersFragment_to_DetailsFragment*/)

                }
                binding.recyclerView.layoutManager = GridLayoutManager(this.context, 1)
                binding.recyclerView.adapter = adapter
            }
        }

        beerViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            if (errorMessage.isNotBlank()) {
                binding.textviewMessageMyBeers.text = errorMessage
            }
        }

        binding.buttonGoToCreateBeer.setOnClickListener {
            findNavController().navigate(R.id.action_MyBeersFragment_to_CreateBeerFragment)
        }

        binding.buttonSort.setOnClickListener {
            when (binding.spinnerSorting.selectedItemPosition) {
                0 -> beerViewModel.sortByName()
                1 -> beerViewModel.sortByBrewery()
                2-> beerViewModel.sortByAbv()
            }
        }

        binding.buttonFilter.setOnClickListener {
            val name = binding.edittextFilterName.text.toString().trim()
            beerViewModel.filterByName(name)
        }

        binding.buttonFilterAbv.setOnClickListener {
            val abv = binding.edittextFilterAbv.text.toString().trim().toDouble()
            beerViewModel.filterByAbv(abv)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onLongPress(e: MotionEvent) {
            Log.d("APPLE", "onLongPress $e")
            super.onLongPress(e)
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            Log.d("APPLE", "onScroll: $e1 \n $e2")
            doIt(e1, e2)
            return super.onScroll(e1, e2, distanceX, distanceY)
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            Log.d("APPLE", "onFling: $e1 \n $e2")
            doIt(e1, e2)
            return super.onFling(e1, e2, velocityX, velocityY)
        }

        private fun doIt(e1: MotionEvent?, e2: MotionEvent) {
            if (e1 != null) {
                val xDiff = e1.x - e2.x
                if (xDiff > 0) {
                    findNavController()
                        .navigate(R.id.action_MyBeersFragment_to_CreateBeerFragment)
                }
            }
        }

    }
}
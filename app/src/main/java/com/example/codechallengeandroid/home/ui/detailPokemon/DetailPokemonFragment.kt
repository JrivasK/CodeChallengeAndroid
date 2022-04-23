package com.example.codechallengeandroid.home.ui.detailPokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.codechallengeandroid.R
import com.example.codechallengeandroid.databinding.FragmentDetailPokemonBinding
import com.example.codechallengeandroid.extensions.observer
import com.example.codechallengeandroid.extensions.visible
import com.example.codechallengeandroid.home.data.response.BaseModel
import com.example.codechallengeandroid.home.ui.listPokemon.ListPokemonFragmentDirections
import com.example.codechallengeandroid.home.viewModel.DetailPokemonViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class DetailPokemonFragment : Fragment() {

    companion object {

    }

    private var _binding: FragmentDetailPokemonBinding? = null
    private val binding get() = _binding!!
    private val detailPokemonViewModel: DetailPokemonViewModel by viewModels()
    private val args: DetailPokemonFragmentArgs by navArgs()
    private var url = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailPokemonBinding.inflate(inflater, container, false)
        setupObservers()
        initUi()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.title = args.namePokemon
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailPokemonViewModel.loadDetailPokemon(args.namePokemon)
    }

    private fun initUi() {
        binding.btnAbilities.setOnClickListener {
            val action =
                DetailPokemonFragmentDirections.actionNavigationDetailToNavigationAbilities(
                    args.namePokemon
                )
            findNavController().navigate(action)
        }
        binding.btnEvolutions.setOnClickListener {
            if (url.isNotEmpty()) {
                val action =
                    DetailPokemonFragmentDirections.actionNavigationDetailToNavigationEvolutions(
                        args.namePokemon,
                        url
                    )
                findNavController().navigate(action)
            }
        }
    }

    private fun setupObservers() {
        detailPokemonViewModel.getPokemon().observer(viewLifecycleOwner) {
            binding.tvTitle.text = args.namePokemon.replaceFirstChar { char ->
                if (char.isLowerCase()) char.titlecase(
                    Locale.getDefault()
                ) else char.toString()
            }
            if (it.baseHappiness != null)
                binding.tvHappiness.text = getString(R.string.base_happiness, it.baseHappiness)
            if (it.captureRate != null)
                binding.tvCaptureRate.text = getString(R.string.capture_rate, it.captureRate)
            if (it.color?.name != null)
                binding.tvColor.text = getString(R.string.color, it.color.name)
            if (it.eggGroups != null && it.eggGroups.isNotEmpty())
                binding.tvEggs.text = getString(
                    R.string.eggs_group,
                    detailPokemonViewModel.getEggsGroups(it.eggGroups)
                )
            if (it.evolutionChain?.url != null)
                url = it.evolutionChain.url
        }
        detailPokemonViewModel.isLoading().observer(viewLifecycleOwner) {
            binding.clLoader.clLoader.visible(it)
            binding.btnAbilities.isEnabled = !it
            binding.btnEvolutions.isEnabled = !it
        }
        detailPokemonViewModel.getError().observer(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.codechallengeandroid.home.ui.evolutions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallengeandroid.R
import com.example.codechallengeandroid.databinding.FragmentEvolutionsBinding
import com.example.codechallengeandroid.extensions.observer
import com.example.codechallengeandroid.extensions.visible
import com.example.codechallengeandroid.home.ui.evolutions.adapter.AdapterEvolutions
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import com.example.codechallengeandroid.home.viewModel.EvolutionsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EvolutionsFragment : Fragment() {

    private var _binding: FragmentEvolutionsBinding? = null
    private val binding get() = _binding!!
    private val evolutionsViewModel: EvolutionsViewModel by viewModels()
    private val args: EvolutionsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEvolutionsBinding.inflate(inflater, container, false)
        evolutionsViewModel.loadPokemon(args.url)
        setupObservers()
        initUi()
        activity?.setTitle(R.string.evolution_line)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.evolution_line)
    }

    private fun initUi() {
        binding.rvEvolutions.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = AdapterEvolutions()
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
    }

    private fun setupObservers() {
        evolutionsViewModel.isUpdated().observer(viewLifecycleOwner) {
            if (it) {
                val action =
                    EvolutionsFragmentDirections.actionNavigationEvolutionsToNavigationListPokemon()
                findNavController().navigate(action)
            }
        }
        evolutionsViewModel.getEvolutions().observer(viewLifecycleOwner) {
            binding.rvEvolutions.apply {
                with(adapter as AdapterEvolutions) {
                    evolutions = it
                    notifyItemRangeInserted(0, it.size)
                    clickItem = object : ClickItemListener {
                        override fun onClick(value: String) {
                            evolutionsViewModel.loadPokemonByName(value)
                        }
                    }
                }
            }
        }
        evolutionsViewModel.isLoading().observer(viewLifecycleOwner) {
            binding.clLoader.clLoader.visible(it)
        }
        evolutionsViewModel.getError().observer(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
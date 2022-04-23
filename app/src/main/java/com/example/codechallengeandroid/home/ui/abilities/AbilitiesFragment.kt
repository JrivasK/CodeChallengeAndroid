package com.example.codechallengeandroid.home.ui.abilities

import android.os.Bundle
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
import com.example.codechallengeandroid.databinding.FragmentAbilitiesBinding
import com.example.codechallengeandroid.extensions.observer
import com.example.codechallengeandroid.extensions.visible
import com.example.codechallengeandroid.home.ui.abilities.adapter.AdapterAbilities
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import com.example.codechallengeandroid.home.viewModel.AbilitiesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AbilitiesFragment : Fragment() {

    private var _binding: FragmentAbilitiesBinding? = null
    private val binding get() = _binding!!
    private val abilitiesViewModel: AbilitiesViewModel by viewModels()
    private val args: AbilitiesFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAbilitiesBinding.inflate(inflater, container, false)
        activity?.setTitle(R.string.abilities)
        setupObservers()
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.rvAbilities.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = AdapterAbilities()
            abilitiesViewModel.loadDetailPokemon(args.namePokemon)
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
    }

    private fun setupObservers() {
        abilitiesViewModel.getPokemon().observer(viewLifecycleOwner) {
            binding.rvAbilities.apply {
                with(adapter as AdapterAbilities) {
                    if (it.abilities != null && it.abilities.isNotEmpty()) {
                        abilities = it.abilities
                        notifyItemRangeInserted(0, it.abilities.size)
                    }
                }
            }
        }
        abilitiesViewModel.isLoading().observer(viewLifecycleOwner) {
            binding.clLoader.clLoader.visible(it)
        }
        abilitiesViewModel.getError().observer(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
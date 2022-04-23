package com.example.codechallengeandroid.home.ui.listPokemon

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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.codechallengeandroid.R
import com.example.codechallengeandroid.databinding.FragmentListPokemonBinding
import com.example.codechallengeandroid.extensions.observer
import com.example.codechallengeandroid.extensions.visible
import com.example.codechallengeandroid.home.ui.listPokemon.adapter.AdapterPokemon
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ClickItemListener
import com.example.codechallengeandroid.home.ui.listPokemon.listener.ListenerUpdatePokemon
import com.example.codechallengeandroid.home.viewModel.ListPokemonViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListPokemonFragment : Fragment() {

    private var _binding: FragmentListPokemonBinding? = null
    private val binding get() = _binding!!
    private val listPokemonViewModel: ListPokemonViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onResume() {
        super.onResume()
        initUi()
        setupObservers()
        activity?.setTitle(R.string.title_activity_home)
    }

    private fun initUi() {
        binding.rvOfPokemon.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = AdapterPokemon()
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }
    }

    private fun setupObservers() {
        listPokemonViewModel.isUpdated().observer(viewLifecycleOwner) {
            binding.rvOfPokemon.apply {
                with(adapter as AdapterPokemon) {
                    notifyItemChanged(it)
                }
            }
        }
        listPokemonViewModel.getPokemon().observer(viewLifecycleOwner) {
            binding.rvOfPokemon.apply {
                with(adapter as AdapterPokemon) {
                    pokemonList = it
                    notifyItemRangeInserted(0, it.size)
                    clickItem = object : ClickItemListener {
                        override fun onClick(value: String) {
                            val action =
                                ListPokemonFragmentDirections.actionNavigationListPokemonToNavigationDetail(
                                    value
                                )
                            findNavController().navigate(action)
                        }
                    }
                    updatePokemon = object : ListenerUpdatePokemon {
                        override fun onChangeFavoriteOrError(value: String, position: Int) {
                            listPokemonViewModel.changePokemonFavoriteOrError(value, position)
                        }
                    }
                }
            }
        }
        listPokemonViewModel.isLoading().observer(viewLifecycleOwner) {
            binding.clLoader.clLoader.visible(it)
        }
        listPokemonViewModel.getError().observer(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
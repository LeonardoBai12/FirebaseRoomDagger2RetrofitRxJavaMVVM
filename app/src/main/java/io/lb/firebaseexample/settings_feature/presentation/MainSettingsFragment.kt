package io.lb.firebaseexample.settings_feature.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import io.lb.firebaseexample.databinding.FragmentSettingsBinding

class MainSettingsFragment : DaggerFragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupInitialValues()
        setupObservers()
        setupListeners()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
    }

    private fun setupObservers() {
        viewModel.showGreetings.observe(viewLifecycleOwner) {
            binding.scShowGreetings.isChecked = it ?: true
        }

        viewModel.allowRestartTodo.observe(viewLifecycleOwner) {
            binding.scAllowRestartTodo.isChecked = it ?: false
        }
    }

    private fun setupInitialValues() {
        viewModel.getShowGreetings()
        viewModel.getAllowRestartTodo()
    }

    private fun setupListeners() {
        binding.scAllowRestartTodo.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(SettingsEvent.OnAllowRestartTodoSwitched(isChecked))
        }

        binding.scShowGreetings.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onEvent(SettingsEvent.OnShowGreetingsSwitched(isChecked))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
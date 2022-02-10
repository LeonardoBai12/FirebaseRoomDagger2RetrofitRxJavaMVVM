package io.lb.firebaseexample.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.lb.firebaseexample.databinding.FragmentTodosBinding
import io.lb.firebaseexample.ui.todo.TodoViewModel

class MainTodosFragment : DaggerFragment() {
    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter = MainTodoAdapter()

    private lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
    }

    private fun setupRecyclerView() {
        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = todoAdapter
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[TodoViewModel::class.java]

        viewModel.loadTodosListener { todos ->
            todoAdapter.updateList(todos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
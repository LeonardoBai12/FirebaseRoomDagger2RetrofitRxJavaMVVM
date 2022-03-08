package io.lb.firebaseexample.ui.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import io.lb.firebaseexample.databinding.FragmentTodosBinding
import io.lb.firebaseexample.todo_feature.domain.model.Todo
import io.lb.firebaseexample.ui.todo.TodoViewModel
import io.lb.firebaseexample.ui.user.UserViewModel

class MainTodosFragment : DaggerFragment() {
    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter = MainTodoAdapter()

    private lateinit var viewModel: TodoViewModel
    private lateinit var userViewModel: UserViewModel

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
        startShimmer()

        binding.rvTodos.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = todoAdapter
        }
    }

    private fun startShimmer() {
        binding.rvTodos.visibility = View.GONE

        requireActivity().runOnUiThread {
            binding.shimmerMainTodos.visibility = View.VISIBLE
            binding.shimmerMainTodos.startShimmer()
        }
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(requireActivity())[TodoViewModel::class.java]
        viewModel.loadTodosListener { todos ->
            updateTodos(todos)
        }
        viewModel.setupSearchTil(binding.svTodo).subscribe {
            todoAdapter.getFilter().filter(it)
            disableShimmer()
        }

        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        userViewModel.loadUsersListener { user ->
            binding.tvUser.text = "Olá, ${user.name}!"
        }
    }

    private fun updateTodos(todos: ArrayList<Todo>) {
        todoAdapter.updateList(todos)

        Handler(Looper.getMainLooper()).postDelayed({
            disableShimmer()
        }, 1500)
    }

    private fun disableShimmer() {
        binding.rvTodos.visibility = View.VISIBLE

        requireActivity().runOnUiThread {
            binding.shimmerMainTodos.visibility = View.GONE
            binding.shimmerMainTodos.stopShimmer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
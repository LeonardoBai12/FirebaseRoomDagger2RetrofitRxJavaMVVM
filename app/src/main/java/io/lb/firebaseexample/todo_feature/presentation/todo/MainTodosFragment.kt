package io.lb.firebaseexample.todo_feature.presentation.todo

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
import io.lb.firebaseexample.user_feature.presentation.login.LoginViewModel
import io.lb.firebaseexample.util.setupDebounceSearchTil

class MainTodosFragment : DaggerFragment() {
    private var _binding: FragmentTodosBinding? = null
    private val binding get() = _binding!!
    private val todoAdapter = MainTodoAdapter()

    private lateinit var viewModel: MainTodosViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTodoSearchView()
        setupRecyclerView()
        setupViewModel()
        setupInitialValues()
    }

    private fun setupInitialValues() {
        viewModel.getHeadsets()
        viewModel.getUser()
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
        viewModel = ViewModelProvider(requireActivity())[MainTodosViewModel::class.java]
        viewModel.todos.observe(viewLifecycleOwner) {
            updateTodos(it)
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.tvUser.text = "Olá, ${it.name}!"
        }
    }

    private fun updateTodos(todos: List<Todo>) {
        todoAdapter.updateList(todos)

        Handler(Looper.getMainLooper()).postDelayed({
            disableShimmer()
        }, 1500)
    }

    private fun setupTodoSearchView() {
        setupDebounceSearchTil(binding.svTodo).subscribe {
            todoAdapter.getFilter().filter(it)
            disableShimmer()
        }
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
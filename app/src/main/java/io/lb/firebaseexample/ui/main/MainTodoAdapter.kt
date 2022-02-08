package io.lb.firebaseexample.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.lb.firebaseexample.R
import io.lb.firebaseexample.model.todo.Todo

class MainTodoAdapter : RecyclerView.Adapter<MainTodoAdapter.ViewHolder>() {
    private var todos = arrayListOf<Todo>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_todo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(todos[position])
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun updateList(todos: ArrayList<Todo>) {
        this.todos = todos
        notifyDataSetChanged()
    }

    fun updateTodo(todo: Todo) {
        todos[todos.indexOf(todo)] = todo
        notifyItemChanged(todos.indexOf(todo))
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTodoTitle: TextView = view.findViewById(R.id.tvTodoTitle)
        private val chkIsCompleted : CheckBox = view.findViewById(R.id.chkIsCompleted)

        fun bind(todo: Todo) {
            tvTodoTitle.text = todo.title
            chkIsCompleted.isChecked = todo.isCompleted
        }
    }
}
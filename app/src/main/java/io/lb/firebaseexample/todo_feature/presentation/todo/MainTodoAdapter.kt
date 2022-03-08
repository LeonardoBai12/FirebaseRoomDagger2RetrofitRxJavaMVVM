package io.lb.firebaseexample.todo_feature.presentation.todo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Filter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.lb.firebaseexample.R
import io.lb.firebaseexample.todo_feature.domain.model.Todo

class MainTodoAdapter : RecyclerView.Adapter<MainTodoAdapter.ViewHolder>() {
    private var todos = arrayListOf<Todo>()
    private var todosFull = arrayListOf<Todo>()

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
        this.todosFull = todos
        notifyDataSetChanged()
    }

    fun updateTodo(todo: Todo) {
        todos[todos.indexOf(todo)] = todo
        notifyItemChanged(todos.indexOf(todo))
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(filter: CharSequence): FilterResults {
                var typedFilter = filter
                val results = FilterResults()

                if (typedFilter.isEmpty()) {
                    todos = todosFull

                    results.count = todosFull.size
                    results.values = todosFull
                    return results
                }

                val filteredItems = ArrayList<Todo>()
                todosFull.forEach { data ->
                    typedFilter = typedFilter.toString().lowercase()

                    val title = data.title?.lowercase() ?: ""
                    if (title.contains(typedFilter)) {
                        filteredItems.add(data)
                    }
                }

                results.count = filteredItems.size
                results.values = filteredItems
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                todos = results.values as ArrayList<Todo>
                notifyDataSetChanged()
            }
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTodoTitle: TextView = view.findViewById(R.id.tv_todo_title)
        private val chkIsCompleted : CheckBox = view.findViewById(R.id.chk_is_completed)

        fun bind(todo: Todo) {
            tvTodoTitle.text = todo.title
            chkIsCompleted.isChecked = todo.isCompleted
        }
    }
}
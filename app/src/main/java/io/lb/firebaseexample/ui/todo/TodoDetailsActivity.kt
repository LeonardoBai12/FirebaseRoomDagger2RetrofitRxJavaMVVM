package io.lb.firebaseexample.ui.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.firebaseexample.databinding.ActivityTodoDetailsBinding

class TodoDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTodoDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
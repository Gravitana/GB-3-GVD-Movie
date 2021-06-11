package com.example.gvdmovie.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gvdmovie.R
import com.example.gvdmovie.databinding.MainActivityBinding
import com.example.gvdmovie.view.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitNow()
        }
    }
}
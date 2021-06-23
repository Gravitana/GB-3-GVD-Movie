package com.example.gvdmovie.view

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.gvdmovie.R
import com.example.gvdmovie.databinding.MainActivityBinding
import com.example.gvdmovie.utils.WITH_ADULT_KEY
import com.example.gvdmovie.view.contacts.ContactsFragment
import com.example.gvdmovie.view.history.HistoryFragment
import com.example.gvdmovie.view.list.ListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private val receiver = MainBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListFragment.newInstance())
                .commitNow()
        }
        registerReceiver(receiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)

        menu.getItem(0).setChecked(this.getPreferences(Context.MODE_PRIVATE).getBoolean(
            WITH_ADULT_KEY, false))

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.with_adult -> {
                item.isChecked = !item.isChecked
                saveWithAdult(item)
                true
            }
            R.id.menu_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, HistoryFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            R.id.menu_contacts -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .add(R.id.container, ContactsFragment.newInstance())
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveWithAdult(item: MenuItem) {
        this.let {
            with(it.getPreferences(Context.MODE_PRIVATE).edit()) {
                putBoolean(WITH_ADULT_KEY, item.isChecked)
                apply()
            }
        }
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}
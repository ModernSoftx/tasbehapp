package com.yourname.tasbehapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yourname.tasbehapp.data.repository.TasbehRepository
import com.yourname.tasbehapp.ui.counter.CounterActivity
import com.yourname.tasbehapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: TasbehRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        lifecycleScope.launchWhenStarted {
            // Redirect to CounterActivity
            startActivity(Intent(this@MainActivity, CounterActivity::class.java))
            finish()
        }
    }
}
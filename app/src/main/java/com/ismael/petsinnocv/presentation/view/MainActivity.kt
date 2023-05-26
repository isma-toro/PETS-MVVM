package com.ismael.petsinnocv.presentation.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.ismael.petsinnocv.R
import com.ismael.petsinnocv.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  lateinit var binding : ActivityMainBinding
  private lateinit var navController : NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    val navHostFragment = supportFragmentManager.findFragmentById(R.id.contenedor) as NavHostFragment
    navController = navHostFragment.navController
    navController.setGraph(R.navigation.nav_graph)
  }

  fun mostrarLoader(mostrar : Boolean) {
    if (mostrar) binding.lpiMain.show() else binding.lpiMain.hide()
  }
}
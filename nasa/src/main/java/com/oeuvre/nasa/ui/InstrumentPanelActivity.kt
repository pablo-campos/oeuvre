package com.oeuvre.nasa.ui

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.oeuvre.nasa.R

class InstrumentPanelActivity : AppCompatActivity() {


	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_instrument_panel)

		val navView: BottomNavigationView = findViewById(R.id.nav_view)
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController

		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_apod, R.id.navigation_rovers, R.id.navigation_notifications))
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}

}

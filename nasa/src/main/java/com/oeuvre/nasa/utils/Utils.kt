package com.oeuvre.nasa.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

class Utils {

	fun checkInternetConnection(context: Context) {
		val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
		cm.registerDefaultNetworkCallback(ConnectivityManager.NetworkCallback())


		class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
			override fun onCapabilitiesChanged(network: Network, capabilities: NetworkCapabilities) {
				val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
				// TODO: notifyConnectedState(connected)
			}
			override fun onLost(network: Network) {
				// TODO: notifyConnectedState(false)
			}
		}
	}
}
package com.example.webview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.webview.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.FusedLocationProviderClient

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        const val REQUEST_LOCATION_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Set up WebView
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://www.google.com/maps/place/Cali,+Valle+del+Cauca/@3.4049036,-76.5751268,13z/data=!4m6!3m5!1s0x8e30a6f0cc4bb3f1:0x1f0fb5e952ae6168!8m2!3d3.4516467!4d-76.5319854!16zL20vMDFwc3Nm?hl=es&entry=ttu&g_ep=EgoyMDI1MDEyMi4wIKXMDSoASAFQAw%3D%3D")

        checkLocationPermission()

        // Button setup
        binding.btnGitHub.setOnClickListener {
            val urlPagina = "https://github.com/viktorobishamu?tab=repositories"
            binding.webView.loadUrl(urlPagina)
        }

        binding.btnYouTube.setOnClickListener {
            val urlPagina = "https://5lpyyggplboq130j.live.co.dev/"
            binding.webView.loadUrl(urlPagina)
        }

        binding.btnHome.setOnClickListener {
            val urlPagina = "https://chukystore.netlify.app/"
            binding.webView.loadUrl(urlPagina)
        }

        binding.btnHome2.setOnClickListener {
            val intent = Intent(this, MainActivityg::class.java)
            startActivity(intent)
        }
    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            useGPS()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                useGPS()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun useGPS() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                Toast.makeText(this, "Latitude: ${it.latitude}, Longitude: ${it.longitude}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

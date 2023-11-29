package com.example.ch09

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.ch09.databinding.ActivityMainBinding
import com.example.ch09.network.Repository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val scope = MainScope()
    var mGoogleSignInClient: GoogleSignInClient? = null
    var googleLoginLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == -1) {
                val data = result.data
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                getGoogleInfo(task)
            }
        }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource: CancellationTokenSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        fusedLocationProviderClient = getFusedLocationProviderClient(this)

        binding.loginBtn.setOnClickListener {
            intent = mGoogleSignInClient!!.signInIntent
            googleLoginLauncher.launch(intent)

            if (mGoogleSignInClient != null) {
                binding.loginBtn.isGone = true
                binding.loginAfter.isVisible = true
                requestLocationPermissions()
            }

        }

    }

    fun getGoogleInfo(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            binding.name.text = account.familyName + "/" + account.givenName
            binding.mail.text = account.email
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    @SuppressLint("MissingPermission")
    fun getDustFine() {
        cancellationTokenSource = CancellationTokenSource()

        fusedLocationProviderClient.getCurrentLocation(
            LocationRequest.PRIORITY_HIGH_ACCURACY,
            cancellationTokenSource!!.token
        ).addOnSuccessListener {
            scope.launch {
                val monitoringStation =
                    Repository.getNearMonitoringStation(it.latitude, it.longitude)

                val measureValue =
                    Repository.getLatestAirQualityData(monitoringStation!!.stationName!!)
                binding.address.text = monitoringStation.stationName
                binding.score.text = measureValue!!.pm10Value+"μg/m3"
                when(measureValue!!.pm10String){
                    "1"-> binding.state.text = "좋음"
                    "2"-> binding.state.text ="보통"
                    "3"-> binding.state.text="나쁨"
                    "4"-> binding.state.text ="매우 나쁨"
                    else -> binding.state.text ="측정불가"

                }

            }
        }
    }


    private fun requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        } else {
            getDustFine()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                getDustFine()
            } else {
                Toast.makeText(this, "권한을 받지 못했습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }
}
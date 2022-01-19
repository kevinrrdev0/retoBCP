package com.example.retobcpcurrency.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.retobcpcurrency.ui.changeCurrency.ChangeCurrencyActivity

class SplashActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, ChangeCurrencyActivity::class.java))
        finish()
    }
}
package com.example.nfctestproject

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var vm: CardViewModel
    private lateinit var tvCardData: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupUI()
        setupNFC()
    }

    private fun setupUI() {

        setContentView(R.layout.activity_main)

        val btnTapCard = findViewById<Button>(R.id.btnTapCard)
        tvCardData = findViewById<TextView>(R.id.tvCardData)
        val etPin = findViewById<EditText>(R.id.etPin)
        val tvPinBlock = findViewById<TextView>(R.id.tvPinBlock)
        val errorMessage = findViewById<TextView>(R.id.errorMessage)

        vm = ViewModelProvider(this).get(CardViewModel::class.java)
        vm.onLoad(this)

        btnTapCard.setOnClickListener {
            // Read NFC card data
            if (tvCardData.text == "") {
                val cardData =
                    "NFC Data: Currently Not Available"
                tvCardData.text = cardData
            }

            val pin = etPin.text.toString()

            if (etPin.text.length in 4..12) {
                val pinBlock = vm.generateIso3PinBlock(pin, "1111222233334444")
                tvPinBlock.text = pinBlock
                errorMessage.text = ""
            } else {
                errorMessage.text = "Pin should be 4 to 12 digits in length"
            }
        }
    }

    private fun setupNFC() {
        if (!vm.doWeSupportNFC()) {
            Toast.makeText(this, "This device does not support NFC.", Toast.LENGTH_LONG).show()
        } else {
            if (!vm.isNFCEnabled()) {
                Toast.makeText(this, "Please enable NFC.", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            // TODO: Handle NFC info here, but cannot currently do this as I do not have access to a physical Android device and NFC does not work on a emulator
            tvCardData.text = tag?.id?.joinToString("") { "%02x".format(it) }
        }
    }
}
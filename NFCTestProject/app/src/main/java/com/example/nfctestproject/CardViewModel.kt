package com.example.nfctestproject

import android.content.Context
import androidx.lifecycle.ViewModel
import android.nfc.NfcAdapter

class CardViewModel : ViewModel() {

    private var nfcAdapter: NfcAdapter? = null

    // Lifecycle
    fun onLoad(context: Context) {
        nfcAdapter = NfcAdapter.getDefaultAdapter(context)
    }

    // NFC
    fun doWeSupportNFC(): Boolean {

        if (nfcAdapter == null) {
            // Device does not support NFC
            // Inform the user and disable NFC features
            print("NFCTestProject :: This device does not support NFC.")
            return false
        }
        return true
    }

    fun isNFCEnabled(): Boolean {
        if (nfcAdapter!!.isEnabled) {
            // NFC is disabled
            // Inform the user and ask them to enable NFC
            print("NFCTestProject :: Please enable NFC.")
            return false
        }
        return  true
    }

    // Pin Block
    fun generateIso3PinBlock(pin: String, pan: String): String {
        return PinBlockGenerator.generateIso3PinBlock(pin,pan)
    }
}
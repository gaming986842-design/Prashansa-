package com.example.ui.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.Locale

class TtsHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false

    init {
        tts = TextToSpeech(context.applicationContext, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val bengaliLocale = Locale("bn", "BD")
            val result = tts?.setLanguage(bengaliLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                // Try Indian Bengali
                val resultIn = tts?.setLanguage(Locale("bn", "IN"))
                if (resultIn == TextToSpeech.LANG_MISSING_DATA || resultIn == TextToSpeech.LANG_NOT_SUPPORTED) {
                    tts?.setLanguage(Locale.ENGLISH)
                }
            }
            isInitialized = true
        }
    }

    fun speak(text: String, phoneticFallback: String = "") {
        if (!isInitialized || tts == null) {
            if (phoneticFallback.isNotEmpty()) {
                Toast.makeText(context, "Pronunciation: $phoneticFallback", Toast.LENGTH_SHORT).show()
            }
            return
        }

        val speechText = text.trim()
        val res = tts?.speak(speechText, TextToSpeech.QUEUE_FLUSH, null, "BanglaTtsUtterance")
        if (res == TextToSpeech.ERROR && phoneticFallback.isNotEmpty()) {
            Toast.makeText(context, "Pronunciation: $phoneticFallback", Toast.LENGTH_SHORT).show()
        }
    }

    fun shutdown() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (_: Exception) {}
    }
}

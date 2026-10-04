package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.db.AppDatabase
import com.example.data.repository.BanglaRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read app_name from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Learn Bangla", appName)
    }

    @Test
    fun `repository provides initial words and quiz questions`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = BanglaRepository(db.banglaDao())

        val words = repository.getAllWords()
        assertTrue(words.isNotEmpty())
        assertTrue(words.any { it.bangla == "নমস্কার" })
        assertTrue(words.any { it.bangla == "ধন্যবাদ" })
        assertTrue(words.any { it.bangla == "আমি" })
        assertTrue(words.any { it.bangla == "ভালোবাসি" })
        assertTrue(words.any { it.bangla == "পানি" })
        assertTrue(words.any { it.bangla == "বন্ধু" })

        val questions = repository.getQuizQuestions()
        assertEquals(8, questions.size)

        val firstQ = questions.first { it.id == 1 }
        assertEquals(1, firstQ.correctIndex)
        assertEquals("Hello / Greetings", firstQ.options[firstQ.correctIndex])
    }

    @Test
    fun `alphabet list contains vowels and consonants`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val db = AppDatabase.getDatabase(context)
        val repository = BanglaRepository(db.banglaDao())

        val alphabet = repository.getAlphabetList()
        val vowels = alphabet.filter { it.isVowel }
        val consonants = alphabet.filter { !it.isVowel }

        assertTrue(vowels.isNotEmpty())
        assertTrue(consonants.isNotEmpty())
        assertEquals("অ", vowels.first().char)
        assertEquals("ক", consonants.first().char)
    }
}

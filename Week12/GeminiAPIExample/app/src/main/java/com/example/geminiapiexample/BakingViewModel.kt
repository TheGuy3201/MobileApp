package com.example.geminiapiexample

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BakingViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)
    val uiState: StateFlow<UiState> =
        _uiState.asStateFlow()

    // Lazily create the GenerativeModel so we can resolve the API key at runtime.
    private val generativeModel: GenerativeModel by lazy {
        val key = resolveApiKey()
        GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = key
        )
    }

    // Try multiple common BuildConfig field names via reflection so the code
    // compiles regardless of whether Gradle exposed `apiKey` or `API_KEY`.
    private fun resolveApiKey(): String {
        val candidates = arrayOf("apiKey", "API_KEY", "APIKEY", "OPENAI_API_KEY")
        val bcClass = try {
            BuildConfig::class.java
        } catch (e: Exception) {
            Log.w(TAG, "BuildConfig class not accessible: ${e.message}")
            return ""
        }

        for (name in candidates) {
            try {
                val field = bcClass.getField(name)
                val value = field.get(null) as? String
                if (!value.isNullOrBlank()) return value
            } catch (_: NoSuchFieldException) {
                // try next candidate
            } catch (e: Exception) {
                Log.w(TAG, "Failed to read BuildConfig.$name: ${e.message}")
            }
        }

        Log.w(TAG, "API key not found in BuildConfig (tried ${candidates.joinToString()}).\n" +
            "Make sure you add a buildConfigField in your module Gradle file or put API_KEY in local.properties.")
        return ""
    }

    fun sendPrompt(
        bitmap: Bitmap,
        prompt: String
    ) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = generativeModel.generateContent(
                    content {
                        image(bitmap)
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    _uiState.value = UiState.Success(outputContent)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }

    companion object {
        private const val TAG = "BakingViewModel"
    }
}
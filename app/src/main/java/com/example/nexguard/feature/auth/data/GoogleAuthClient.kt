package com.example.nexguard.feature.auth.data

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import com.example.nexguard.R
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential

class GoogleAuthClient(
    private val context: Context
) {
    private val credentialManager =
        CredentialManager.create(context)

    suspend fun signIn(): Result<String> {
        return try {

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(
                    context.getString(R.string.default_web_client_id)
                )
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                context = context,
                request = request
            )

            val googleIdTokenCredential =
                GoogleIdTokenCredential.createFrom(
                    result.credential.data
                )

            Result.success(googleIdTokenCredential.idToken)

        } catch (e: GetCredentialException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
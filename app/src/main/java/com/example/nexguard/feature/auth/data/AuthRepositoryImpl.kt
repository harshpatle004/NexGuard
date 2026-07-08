package com.example.nexguard.feature.auth.data

import com.example.nexguard.feature.auth.domain.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository{
    override suspend fun login(
        email: String,
        password: String
    ): Result<Unit> {
       return try{
           firebaseAuth
               .signInWithEmailAndPassword(email,password)
               .await()

           Result.success(Unit)

       }catch (e : Exception) {
           Result.failure(e)
       }
    }

    override suspend fun loginWithGoogle(
        idToken: String
    ): Result<Unit> {
        return try {
            val credential = GoogleAuthProvider.getCredential(
                idToken,
                null
            )

            firebaseAuth
                .signInWithCredential(credential)
                .await()

            Result.success(Unit)

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(
        email: String,
        password: String
    ): Result<Unit> {
        return try{
            firebaseAuth
                .createUserWithEmailAndPassword(email,password)
                .await()

            Result.success(Unit)
        }
        catch(e: Exception){
            Result.failure(e)

        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}
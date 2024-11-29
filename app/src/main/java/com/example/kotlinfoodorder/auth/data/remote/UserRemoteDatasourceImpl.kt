package com.example.kotlinfoodorder.login.data.remote

import com.example.kotlinfoodorder.auth.data.remote.UserNotFoundException
import com.example.kotlinfoodorder.auth.data.remote.UserRemoteDatasource
import com.example.kotlinfoodorder.login.model.User
import com.example.kotlinfoodorder.login.model.UserData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRemoteDatasourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
): UserRemoteDatasource {
    override fun isSessionValid(): Boolean {
        return firebaseAuth.currentUser != null
    }

    override suspend fun createAccount(name: String, email: String, password: String): User {
        val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            updateUserName(firebaseUser, name)
            updateCreatedUserDate(firebaseUser)
        }

        return mapToUserAuth(authResult)
    }

    private suspend fun updateCreatedUserDate(user: FirebaseUser) {
        val userCollection = firestore.collection("user")

        val newUserData = UserData(
            id = user.uid,
            userName = user.displayName ?: "",
            userCreatedDate = System.currentTimeMillis()
        )

        userCollection.document(user.uid).set(newUserData).await()
    }

    private suspend fun updateUserName(user: FirebaseUser, name: String) {
        try {
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user.updateProfile(profileUpdates).await()
        } catch (e: Exception) {
            println("Erro ao atualizar nome: ${e.message}")
        }
    }

    override suspend fun signIn(email: String, password: String): User {
        val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
        return mapToUserAuth(authResult)
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override suspend fun recover(email: String) {
        firebaseAuth.sendPasswordResetEmail(email).await()
    }

    override suspend fun getCurrentUserInfo(): User {
        val user = firebaseAuth.currentUser ?: throw UserNotFoundException()
        return mapToUserAuth(user)
    }

    private fun mapToUserAuth(authResult: AuthResult): User {
        authResult.user?.let { user ->
            return User(
                user.uid,
                user.displayName,
                user.email,
                user.photoUrl,
                user.phoneNumber,
                user.isEmailVerified
            )
        } ?: throw UserNotFoundException()
    }

    private fun mapToUserAuth(userFirebase: FirebaseUser): User {
        userFirebase.let { user ->
            return User(
                user.uid,
                user.displayName,
                user.email,
                user.photoUrl,
                user.phoneNumber,
                user.isEmailVerified
            )
        }
    }
}
package com.example.kotlinfoodorder.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinfoodorder.data.model.RegisterUserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RegisterViewModel : ViewModel() {
    private val _registryState = MutableStateFlow<RegistryState>(RegistryState.NotSuccess)
    val registryState: StateFlow<RegistryState> get() = _registryState

    private val _currentRegistry = MutableStateFlow<RegisterUserModel?>(null)
    val currentRegistry: StateFlow<RegisterUserModel?> get() = _currentRegistry

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun register(name: String, email: String, password: String, confirmPassword: String) {
        if (password != confirmPassword) {
            _registryState.value = RegistryState.Error("As senhas não coincidem")
            return
        }

        _registryState.value = RegistryState.Loading
        viewModelScope.launch {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val firebaseUser = result.user
                if (firebaseUser != null) {
                    updateUserName(firebaseUser, name)

                    _currentRegistry.value = RegisterUserModel(
                        name = name,
                        email = email,
                        password = password,
                        confirmedPassword = confirmPassword
                    )

                    _registryState.value = RegistryState.Success
                } else {
                    _registryState.value = RegistryState.Error("Erro desconhecido")
                }
            } catch (e: Exception) {
                _registryState.value = RegistryState.Error(e.message ?: "Erro durante o registro")
            }
        }
    }

    private suspend fun updateUserName(user: FirebaseUser, name: String) {
        try {
            val profileUpdates = userProfileChangeRequest {
                displayName = name
            }
            user.updateProfile(profileUpdates).await()
            println("Nome de usuário atualizado com sucesso")
        } catch (e: Exception) {
            println("Erro ao atualizar nome: ${e.message}")
        }
    }

    sealed class RegistryState {
        object NotSuccess : RegistryState()
        object Loading : RegistryState()
        object Success : RegistryState()
        data class Error(val message: String?) : RegistryState()
    }
}

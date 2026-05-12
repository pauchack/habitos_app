package com.example.projecte_m07

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.projecte_m07.viewmodel.RegisterViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RegisterViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: RegisterViewModel

    // Datos válidos por defecto para reutilizar en tests
    private val validUsername = "usuario"
    private val validEmail = "mail@gmail.com"
    private val validPhone = "612345678"
    private val validPassword = "Abcd@1234"

    @Before
    fun setup() {
        viewModel = RegisterViewModel()
    }

    // ======== USERNAME ========

    @Test
    fun usuarioCorrecto() {
        assertTrue(viewModel.validate(validUsername, validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioCurto_falla() {
        assertFalse(viewModel.validate("ab", validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioExactamente3_falla() {
        // Límite: longitud 3 debe fallar (necesita MÁS de 3)
        assertFalse(viewModel.validate("abc", validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioExactamente4_correcto() {
        // Límite: longitud 4 debe pasar
        assertTrue(viewModel.validate("abcd", validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioExactamente25_correcto() {
        // Límite superior: 25 caracteres debe pasar
        assertTrue(viewModel.validate("a".repeat(25), validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioExactamente26_falla() {
        // Límite superior: 26 caracteres debe fallar
        assertFalse(viewModel.validate("a".repeat(26), validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioMuyLargo_falla() {
        assertFalse(viewModel.validate("a".repeat(100), validEmail, validPhone, validPassword, validPassword))
    }

    @Test
    fun usuarioVacio_falla() {
        assertFalse(viewModel.validate("", validEmail, validPhone, validPassword, validPassword))
    }

    // ======== TELÉFONO ========

    @Test
    fun telefonoCorrecto() {
        assertTrue(viewModel.validate(validUsername, validEmail, "612345678", validPassword, validPassword))
    }

    @Test
    fun telefonoCorto_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "123", validPassword, validPassword))
    }

    @Test
    fun telefonoLargo_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "1234567890", validPassword, validPassword))
    }

    @Test
    fun telefonoConLetras_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "61234abc9", validPassword, validPassword))
    }

    @Test
    fun telefonoVacio_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "", validPassword, validPassword))
    }

    @Test
    fun telefonoExactamente9Ceros_correcto() {
        // 9 dígitos aunque sean todos ceros debe pasar la validación de formato
        assertTrue(viewModel.validate(validUsername, validEmail, "000000000", validPassword, validPassword))
    }

    // ======== EMAIL ========

    @Test
    fun emailCorrecto() {
        assertTrue(viewModel.validate(validUsername, "correo@dominio.com", validPhone, validPassword, validPassword))
    }

    @Test
    fun emailSinArroba_falla() {
        assertFalse(viewModel.validate(validUsername, "correodominio.com", validPhone, validPassword, validPassword))
    }

    @Test
    fun emailSinDominio_falla() {
        assertFalse(viewModel.validate(validUsername, "correo@", validPhone, validPassword, validPassword))
    }

    @Test
    fun emailVacio_falla() {
        assertFalse(viewModel.validate(validUsername, "", validPhone, validPassword, validPassword))
    }

    @Test
    fun emailConSubdominio_correcto() {
        assertTrue(viewModel.validate(validUsername, "user@mail.co.uk", validPhone, validPassword, validPassword))
    }

    // ======== CONTRASEÑA ========

    @Test
    fun contrasenaCorrecto() {
        assertTrue(viewModel.validate(validUsername, validEmail, validPhone, "Abcd@1234", "Abcd@1234"))
    }

    @Test
    fun contrasenaDebil_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "abc123", "abc123"))
    }

    @Test
    fun contrasenaSinMayuscula_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "abcd@1234", "abcd@1234"))
    }

    @Test
    fun contrasenaSinMinuscula_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "ABCD@1234", "ABCD@1234"))
    }

    @Test
    fun contrasenaSinNumero_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "Abcd@abcd", "Abcd@abcd"))
    }

    @Test
    fun contrasenaSinSimbolo_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "Abcd1234", "Abcd1234"))
    }

    @Test
    fun contrasenaDemasiadoCorta_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "Ab@1", "Ab@1"))
    }

    @Test
    fun contrasenasNoCoinciden_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "Abcd@1234", "Diferent@123"))
    }

    @Test
    fun confirmarContrasenaVacia_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, validPhone, "Abcd@1234", ""))
    }
}

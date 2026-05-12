package com.example.projecte_m07

import com.example.projecte_m07.viewmodel.EditarPerfilViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EditarPerfilViewModelTest {

    private lateinit var viewModel: EditarPerfilViewModel

    // Datos válidos por defecto para reutilizar en tests
    private val validUsername = "usuario"
    private val validEmail = "mail@gmail.com"
    private val validPhone = "612345678"

    @Before
    fun setup() {
        viewModel = EditarPerfilViewModel()
    }

    // ======== USERNAME ========

    @Test
    fun usuarioCorrecto() {
        assertTrue(viewModel.validate(validUsername, validEmail, validPhone))
    }

    @Test
    fun usuarioVacio_falla() {
        assertFalse(viewModel.validate("", validEmail, validPhone))
    }

    @Test
    fun usuarioCurto_falla() {
        assertFalse(viewModel.validate("ab", validEmail, validPhone))
    }

    @Test
    fun usuarioExactamente3_falla() {
        // Límite: longitud 3 debe fallar (necesita MÁS de 3)
        assertFalse(viewModel.validate("abc", validEmail, validPhone))
    }

    @Test
    fun usuarioExactamente4_correcto() {
        // Límite: longitud 4 debe pasar
        assertTrue(viewModel.validate("abcd", validEmail, validPhone))
    }

    @Test
    fun usuarioExactamente25_correcto() {
        // Límite superior: 25 caracteres debe pasar
        assertTrue(viewModel.validate("a".repeat(25), validEmail, validPhone))
    }

    @Test
    fun usuarioExactamente26_falla() {
        // Límite superior: 26 caracteres debe fallar
        assertFalse(viewModel.validate("a".repeat(26), validEmail, validPhone))
    }

    @Test
    fun usuarioMuyLargo_falla() {
        assertFalse(viewModel.validate("a".repeat(100), validEmail, validPhone))
    }

    // ======== TELÉFONO ========

    @Test
    fun telefonoCorrecto() {
        assertTrue(viewModel.validate(validUsername, validEmail, "612345678"))
    }

    @Test
    fun telefonoVacio_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, ""))
    }

    @Test
    fun telefonoCorto_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "12345"))
    }

    @Test
    fun telefonoLargo_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "1234567890"))
    }

    @Test
    fun telefonoConLetras_falla() {
        assertFalse(viewModel.validate(validUsername, validEmail, "61234abc9"))
    }

    @Test
    fun telefonoExactamente9Ceros_correcto() {
        // 9 dígitos aunque sean todos ceros debe pasar la validación de formato
        assertTrue(viewModel.validate(validUsername, validEmail, "000000000"))
    }

    // ======== EMAIL ========

    @Test
    fun emailCorrecto() {
        assertTrue(viewModel.validate(validUsername, "correo@dominio.com", validPhone))
    }

    @Test
    fun emailVacio_falla() {
        assertFalse(viewModel.validate(validUsername, "", validPhone))
    }

    @Test
    fun emailSinArroba_falla() {
        assertFalse(viewModel.validate(validUsername, "correodominio.com", validPhone))
    }

    @Test
    fun emailSinDominio_falla() {
        assertFalse(viewModel.validate(validUsername, "correo@", validPhone))
    }

    @Test
    fun emailConSubdominio_correcto() {
        assertTrue(viewModel.validate(validUsername, "user@mail.co.uk", validPhone))
    }
}

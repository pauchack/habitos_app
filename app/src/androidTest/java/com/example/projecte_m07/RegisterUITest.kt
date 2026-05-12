package com.example.projecte_m07

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterUITest {

    @get:Rule
    val activityRule = ActivityScenarioRule(Register::class.java)

    // Datos válidos por defecto
    private val validUsername = "usuario"
    private val validEmail = "test@gmail.com"
    private val validPhone = "612345678"
    private val validPassword = "Abcd@1234"

    /**
     * Helper: rellena el formulario completo y pulsa el botón de registro.
     * Usa los valores válidos por defecto si no se especifica un parámetro.
     */
    private fun fillAndSubmit(
        username: String = validUsername,
        email: String = validEmail,
        phone: String = validPhone,
        password: String = validPassword,
        confirmPassword: String = validPassword
    ) {
        onView(withId(R.id.editTextRegisterUsername)).perform(typeText(username), closeSoftKeyboard())
        onView(withId(R.id.editTextRegisterEmail)).perform(typeText(email), closeSoftKeyboard())
        onView(withId(R.id.editTextRegisterPhone)).perform(typeText(phone), closeSoftKeyboard())
        onView(withId(R.id.editTextRegisterPassword)).perform(typeText(password), closeSoftKeyboard())
        onView(withId(R.id.editTextRegisterConfirmPassword)).perform(typeText(confirmPassword), closeSoftKeyboard())
        onView(withId(R.id.buttonConfirmRegister)).perform(click())
    }

    private fun checkError(errorText: String) {
        onView(withId(R.id.textViewRegisterError)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewRegisterError)).check(matches(withText(errorText)))
    }

    // ======== USERNAME ========

    @Test
    fun usuarioVacio_muestraError() {
        fillAndSubmit(username = "")
        checkError("El nombre de usuario debe tener más de 3 caracteres")
    }

    @Test
    fun usuarioCurto_muestraError() {
        fillAndSubmit(username = "ab")
        checkError("El nombre de usuario debe tener más de 3 caracteres")
    }

    @Test
    fun usuarioExactamente3_muestraError() {
        fillAndSubmit(username = "abc")
        checkError("El nombre de usuario debe tener más de 3 caracteres")
    }

    @Test
    fun usuarioLargo_muestraError() {
        fillAndSubmit(username = "a".repeat(100))
        checkError("El nombre de usuario debe tener menos de 25 caracteres")
    }

    @Test
    fun usuarioExactamente26_muestraError() {
        fillAndSubmit(username = "a".repeat(26))
        checkError("El nombre de usuario debe tener menos de 25 caracteres")
    }

    // ======== EMAIL ========

    @Test
    fun emailVacio_muestraError() {
        fillAndSubmit(email = "")
        checkError("Introduce un correo electrónico válido")
    }

    @Test
    fun emailSinArroba_muestraError() {
        fillAndSubmit(email = "testgmail.com")
        checkError("Introduce un correo electrónico válido")
    }

    @Test
    fun emailSinDominio_muestraError() {
        fillAndSubmit(email = "test@")
        checkError("Introduce un correo electrónico válido")
    }

    // ======== TELÉFONO ========

    @Test
    fun telefonoVacio_muestraError() {
        fillAndSubmit(phone = "")
        checkError("El número de teléfono debe tener exactamente 9 cifras")
    }

    @Test
    fun telefonoCorto_muestraError() {
        fillAndSubmit(phone = "12345")
        checkError("El número de teléfono debe tener exactamente 9 cifras")
    }

    @Test
    fun telefonoLargo_muestraError() {
        fillAndSubmit(phone = "1234567890")
        checkError("El número de teléfono debe tener exactamente 9 cifras")
    }

    @Test
    fun telefonoConLetras_muestraError() {
        fillAndSubmit(phone = "abcdefghi")
        checkError("El número de teléfono debe tener exactamente 9 cifras")
    }

    // ======== CONTRASEÑA ========

    @Test
    fun contrasenaVacia_muestraError() {
        fillAndSubmit(password = "", confirmPassword = "")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenaSinSimbolo_muestraError() {
        fillAndSubmit(password = "Abcd1234", confirmPassword = "Abcd1234")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenaSinMayuscula_muestraError() {
        fillAndSubmit(password = "abcd@1234", confirmPassword = "abcd@1234")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenaSinMinuscula_muestraError() {
        fillAndSubmit(password = "ABCD@1234", confirmPassword = "ABCD@1234")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenaSinNumero_muestraError() {
        fillAndSubmit(password = "Abcd@abcd", confirmPassword = "Abcd@abcd")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenaDemasiadoCorta_muestraError() {
        fillAndSubmit(password = "Ab@1", confirmPassword = "Ab@1")
        checkError("La contraseña debe tener más de 8 caracteres, mayúscula, minúscula, número y símbolo")
    }

    @Test
    fun contrasenasNoCoinciden_muestraError() {
        fillAndSubmit(password = "Abcd@1234", confirmPassword = "abcd@1234")
        checkError("Las contraseñas no coinciden")
    }

    @Test
    fun confirmarContrasenaVacia_muestraError() {
        fillAndSubmit(password = "Abcd@1234", confirmPassword = "")
        checkError("Las contraseñas no coinciden")
    }

    // ======== ÉXITO (descomenta cuando el backend esté disponible en los tests) ========

    /*
    @Test
    fun todosCamposValidos_navegaAMenu() {
        androidx.test.espresso.intent.Intents.init()
        fillAndSubmit(username = "usuarioValido")
        androidx.test.espresso.intent.Intents.intended(
            androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent(Menu::class.java.name)
        )
        androidx.test.espresso.intent.Intents.release()
    }
    */
}

package org.nibuitrago.junit_app.models;

import org.junit.jupiter.api.*;
import org.nibuitrago.junit_app.exceptions.DineroInsuficienteException;

import javax.swing.*;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    @Test
    @DisplayName("Probando el nombre de la cuenta de Nicolas")
    void testNombreCuenta() {
        Cuenta cuenta = new Cuenta("Nicolas", new BigDecimal("1000.12345"));
        String esperado = "Nicolas";
        String real = cuenta.getPersona();
        assertEquals(esperado, real);
    }

    @Test
    @DisplayName("Probando el saldo de la cuenta")
    void testSaldoCuenta() {
        Cuenta cuenta = new Cuenta("Nicolas", new BigDecimal("1000.12345"));
        assertEquals(1000.12345, cuenta.getSaldo().doubleValue());
        assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) >= 0);

    }

    @Test
    @DisplayName("Comparando los atributos de dos cuentas")
    void compararCuentaTest() {
        Cuenta cuenta = new Cuenta("Jhon Doe", new BigDecimal("12345.000"));
        Cuenta cuenta2 = new Cuenta("Jhon Doe", new BigDecimal("12345.000"));

        //assertNotEquals(cuenta2,cuenta);
        assertEquals(cuenta2, cuenta);
    }

    @Test
    @DisplayName("Testeando los dèbitos")
    void debitoTets() {
        Cuenta cuenta = new Cuenta("Nicolas", new BigDecimal("1000.567"));
        cuenta.debitar(new BigDecimal("100"));
        assertNotNull(cuenta);
        assertNotNull(cuenta.getSaldo());
        assertEquals(900, cuenta.getSaldo().intValue());
    }

    @Test
    @DisplayName("Probando los crèditos")
    void creditoTets() {
        Cuenta cuenta = new Cuenta("Nicolas", new BigDecimal("1000.567"));
        cuenta.acreditar(new BigDecimal("100"));
        assertNotNull(cuenta);
        assertNotNull(cuenta.getSaldo());
        assertEquals(1100, cuenta.getSaldo().intValue());
    }

    @Test
    @DisplayName("Proabdno la funcionalidad de dinero induficiente")
    @Disabled //Se coloca para que aparezca en el informe. Aparece como "ignored"
    void dineroInsuficienteExceptionTest() {
        fail("Se fuerza el fallo del test");
        Cuenta cuenta = new Cuenta("Nicolas", new BigDecimal("1000.12345"));
        Exception e = assertThrows(DineroInsuficienteException.class, () -> {
            cuenta.debitar(new BigDecimal(1500));
        });
        String actual = e.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }
}
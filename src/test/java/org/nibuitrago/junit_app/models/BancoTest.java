package org.nibuitrago.junit_app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.nibuitrago.junit_app.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BancoTest {
    Cuenta cuentaOrigen;
    Cuenta cuentaDestino;
    Banco banco;

    @BeforeEach
    void setUp() {
        cuentaOrigen = new Cuenta("Nicolas", new BigDecimal(500));
        cuentaDestino = new Cuenta("Andres", new BigDecimal(1500));
        banco = new Banco("Davivienda");
    }

    @Test
    void transferir() {


        BigDecimal monto = new BigDecimal(100);

        BigDecimal montoFinalEsperadoOrigen = new BigDecimal(400);

        BigDecimal montoFinalEsperadoDestino = new BigDecimal(1600);

        banco.transferir(cuentaOrigen, cuentaDestino, monto);

        assertEquals(montoFinalEsperadoOrigen, cuentaOrigen.getSaldo());
        assertEquals(montoFinalEsperadoDestino, cuentaDestino.getSaldo());

    }

    @Test
    @DisplayName("Transferir con excepciòn de dinero insuficiente")
    void transferirConDineroInsuficienteExcepcionTest() {

        BigDecimal monto = new BigDecimal(600);

        Exception e = assertThrows(DineroInsuficienteException.class, () -> {
            banco.transferir(cuentaOrigen, cuentaDestino, monto);
        });
        String actual = e.getMessage();
        String esperado = "Dinero insuficiente";
        assertEquals(esperado, actual);
    }

    @Test
    @DisplayName("Relaciòn de cuentas")
    void relacionDeCuentasTest() {

        BigDecimal monto = new BigDecimal(600);

        Exception e = assertThrows(DineroInsuficienteException.class, () -> {
            banco.transferir(cuentaOrigen, cuentaDestino, monto);
        });
        banco.addCuenta(cuentaOrigen);
        banco.addCuenta(cuentaDestino);
        String actual = e.getMessage();
        String esperado = "Dinero insuficiente";

        assertAll(
                () -> assertEquals(esperado, actual, "Se puede colocar un mensaje que se instancia por debajo siempre (falle o no falle)"),
                () -> assertEquals(2, banco.getCuentas().size(), () -> "Este es un llamado futuro. Solo se invoca cuando falla. Al ojo humano no se nota, pero por debajo solo se ejecuta si falla"),
                () -> assertEquals("Davivienda", cuentaOrigen.getBanco().getNombre()),
                () -> assertEquals("Nicolas", banco.getCuentas().stream()
                        .filter(c -> c.getPersona().equals("Nicolas")).findFirst().get().getPersona())
        );
    }
}
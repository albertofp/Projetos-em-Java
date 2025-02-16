import net.jqwik.api.*;
import static org.assertj.core.api.Assertions.*;
import net.jqwik.api.constraints.Positive;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

class CalculoIMCTest {

  @Property
  void testCalcularIMC(@ForAll double peso, @ForAll @Positive double altura) {
    Assume.that(peso >= 0);
    double imc = CalculoIMC.calcularPeso(peso, altura);
    double expected = peso / (altura * altura);
    assertThat(imc).isCloseTo(expected, within(0.01));
  }

  @Property
  void testClassificarIMC(@ForAll double peso, @ForAll @Positive double altura) {
    Assume.that(peso >= 0);
    double imc = CalculoIMC.calcularPeso(peso, altura);
    String classificacao = CalculoIMC.classificarIMC(imc);
    assertThat(classificacao).isIn(
        "Magreza grave",
        "Magreza moderada",
        "Magreza leve",
        "Saudável",
        "Sobrepeso",
        "Obesidade Grau I",
        "Obesidade Grau II",
        "Obesidade Grau III");
  }
}

class IMCExtremoTest {

  @Provide
  static Arbitrary<Double> alturasExtremas() {
    return Arbitraries.of(0.1, 0.5, 3.0, 5.0);
  }

  @Provide
  static Arbitrary<Double> pesosExtremos() {
    return Arbitraries.of(50.0, 400.0, 500.0);
  }

  @Property
  void imcComValoresExtremos(@ForAll("pesosExtremos") double peso,
      @ForAll("alturasExtremas") double altura) {
    Assume.that(peso > 0 && altura > 0);

    double imc = CalculoIMC.calcularPeso(peso, altura);

    assertThat(imc).isGreaterThanOrEqualTo(0);

    if (peso >= 400 && altura <= 0.5) {
      assertThat(imc).isGreaterThan(1000);
    }
  }
}

class IMCServiceTest {

    @Test
    void testCalculoIMCComMock() {
        // Create a mock of IMCService
        IMCService imcService = mock(IMCService.class);
        // Configure the mock to return 24.69 when called with 80kg and 1.80m
        when(imcService.calcularIMC(80, 1.80)).thenReturn(24.69);

        double imc = imcService.calcularIMC(80, 1.80);
        assertThat(imc).isEqualTo(24.69);

        // Verify the method was called with the expected parameters
        verify(imcService).calcularIMC(80, 1.80);
    }
}
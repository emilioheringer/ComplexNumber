import com.heringer.Complex;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComplexTest {

    @Test
    void testConstructorRectangularStandard() {
        Complex c = new Complex("3+4i");
        assertEquals(3.0, c.getReal(), 0.0001);
        assertEquals(4.0, c.getImaginary(), 0.0001);
        assertEquals(5.0, c.getMagnitude(), 0.001);
        assertEquals(53.13, c.getAngleDegrees(), 0.01);
    }

    @Test
    void testConstructorRectangularNegativeImag() {
        Complex c = new Complex("5-2i");
        assertEquals(5.0, c.getReal(), 0.0001);
        assertEquals(-2.0, c.getImaginary(), 0.0001);
    }

    @Test
    void testConstructorRectangularRealOnly() {
        Complex c = new Complex("7");
        assertEquals(7.0, c.getReal(), 0.0001);
        assertEquals(0.0, c.getImaginary(), 0.0001);
    }

    @Test
    void testConstructorRectangularOnlyI() {
        Complex c = new Complex("i");
        assertEquals(0.0, c.getReal(), 0.0001);
        assertEquals(1.0, c.getImaginary(), 0.0001);
    }

    @Test
    void testConstructorRectangularNegativeI() {
        Complex c = new Complex("-i");
        assertEquals(0.0, c.getReal(), 0.0001);
        assertEquals(-1.0, c.getImaginary(), 0.0001);
    }

    @Test
    void testConstructorPolarWithoutDegreeSymbol() {
        Complex c = new Complex("5<53.13");
        assertEquals(3.0, c.getReal(), 0.01);
        assertEquals(4.0, c.getImaginary(), 0.01);
    }

    @Test
    void testConstructorPolarWithDegreeSymbol() {
        Complex c = new Complex("10<90°");
        assertEquals(0.0, c.getReal(), 0.01);
        assertEquals(10.0, c.getImaginary(), 0.01);
    }

    @Test
    void testConstructorPolarWithSymbolAlt() {
        Complex c = new Complex("2<180º");
        assertEquals(-2.0, c.getReal(), 0.01);
        assertEquals(0.0, c.getImaginary(), 0.01);
    }

    @Test
    void testConversionRectangularToPolar() {
        Complex c = new Complex(1, Math.sqrt(3));
        assertEquals(2.0, c.getMagnitude(), 0.001);
        assertEquals(60.0, c.getAngleDegrees(), 0.1);
    }

    @Test
    void testConversionPolarToRectangular() {
        Complex c = new Complex("2<60");
        c.polarToRec();
        assertEquals(1.0, c.getReal(), 0.01);
        assertEquals(Math.sqrt(3), c.getImaginary(), 0.01);
    }

    @Test
    void testZeroComplex() {
        Complex c = new Complex("0+0i");
        assertEquals(0.0, c.getMagnitude(), 0.0001);
        assertEquals(0.0, c.getAngleDegrees(), 0.0001);
    }

    @Test
    void testInvalidInputReturnsZero() {
        Complex c = new Complex("notacomplexnumber");
        assertEquals(0.0, c.getReal(), 0.0001);
        assertEquals(0.0, c.getImaginary(), 0.0001);
    }

    @Test
    void testInfinityCase() {
        Complex c = new Complex(Double.POSITIVE_INFINITY, 1.0);
        assertTrue(Double.isInfinite(c.getMagnitude()));
        assertEquals(0.0, c.getAngleDegrees(), 0.1);
    }

    @Test
    void testNaNCase() {
        Complex c = new Complex(Double.NaN, 0);
        assertTrue(Double.isNaN(c.getMagnitude()));
        assertTrue(Double.isNaN(c.getAngleDegrees()));
    }

    private Complex complex1;
    private Complex complex2;

    @BeforeEach
    public void setUp() {
        complex1 = new Complex(3, 4); // 3 + 4i
        complex2 = new Complex(1, 2); // 1 + 2i
    }

    @Test
    public void testAdd() {
        Complex result = complex1.add(complex2);
        assertEquals(4.0, result.getReal(), "Real part after addition");
        assertEquals(6.0, result.getImaginary(), "Imaginary part after addition");
    }

    @Test
    public void testSubtract() {
        Complex result = complex1.subtract(complex2);
        assertEquals(2.0, result.getReal(), "Real part after subtraction");
        assertEquals(2.0, result.getImaginary(), "Imaginary part after subtraction");
    }

    @Test
    public void testMultiply() {
        Complex result = complex1.multiply(complex2);
        assertEquals(-5.0, result.getReal(), "Real part after multiplication");
        assertEquals(10.0, result.getImaginary(), "Imaginary part after multiplication");
    }
    @Test
    public void testDivide() {
        Complex complex1 = new Complex("5.0<45.0"); // Exemplo de número complexo em forma polar
        Complex complex2 = new Complex("2.0<30.0");
    
        Complex result = Complex.divide(complex1, complex2);
        result.showRec();
    
        // Ajustando a margem de erro para a comparação
        assertEquals(2.4148, result.getReal(), 0.001, "Real part after division");
        assertEquals(0.6472, result.getImaginary(), 0.001, "Imaginary part after division"); // Usando tolerância de 0.001
    }

    @Test
    public void testDivideByZero() {
        Complex complexZero = new Complex(0, 0);
        assertThrows(ArithmeticException.class, () -> {
            complex1.divide(complexZero);
        }, "Division by zero should throw ArithmeticException");
    }

    @Test
    public void testRecToPolar() {
        complex1.recToPolar();
        assertEquals(5.0, complex1.getMagnitude(), 0.001, "Magnitude after polar conversion");
        assertEquals(0.9273, complex1.getAngleRadians(), 0.0001, "Angle in radians after conversion");
        assertEquals(53.1301, complex1.getAngleDegrees(), 0.001, "Angle in degrees after conversion");
    }
    @Test
    public void testPolarToRectangular() {
        Complex complex = new Complex("5<53.1301"); // entrada em forma polar
    
        assertEquals(3.0, complex.getReal(), 0.01, "Real part after polar to rectangular conversion");
        assertEquals(4.0, complex.getImaginary(), 0.01, "Imaginary part after polar to rectangular conversion");
    }
    @Test
    public void testPolarToRec() {
        // Criando uma nova instância de Complex com a magnitude e o ângulo em radianos
        Complex complex1 = new Complex(5.0, 0.9273); // magnitude = 5.0, angleRadians = 0.9273
    
        // Convertendo para a forma retangular
        complex1.polarToRec();
    
        // Verificando se a conversão de polar para retangular está correta
        assertEquals(5, complex1.getReal(), 0.001, "Real part after polar to rectangular conversion");
        assertEquals(0.9273, complex1.getImaginary(), 0.001, "Imaginary part after polar to rectangular conversion");
    }


}

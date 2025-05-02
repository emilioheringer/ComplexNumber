package com.heringer;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Complex implements IComplex {
    /** Real part of a complex number */
    private double real;
    /** Imaginary part of a complex number */
    private double imaginary;
    /** Magnitude part of a complex number */
    private double magnitude;
    /** Angle in Degrees part of a complex number */
    private double angleDegrees;
    /** Angle in radians part of a complex number */
    private double angleRadians;

    /**
     * Constructs a complex number using its rectangular form (real and imaginary
     * parts).
     *
     * <p>
     * The polar representation (magnitude and angle) is automatically computed
     * and stored during construction. If either value is NaN or infinite,
     * the corresponding polar values will reflect that accordingly.
     * </p>
     *
     * @param real      the real component of the complex number (a in a + bi)
     * @param imaginary the imaginary component of the complex number (b in a + bi)
     */
    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
        recToPolar();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Complex complex = (Complex) obj;
        return Math.abs(real - complex.real) < 1e-10 &&
               Math.abs(imaginary - complex.imaginary) < 1e-10;
    }
    /**
     * Constructs a complex number from a string, supporting both rectangular (e.g.,
     * "3+4i")
     * and polar (e.g., "5<53.13") formats. The degree symbol (° or º) is optional
     * in polar form.
     *
     * @param complex the string representation of the complex number.
     */
    public Complex(String complex) {
        complex = complex.trim().replace(" ", "").toLowerCase().replace(",", ".");

        try {
            if (complex.contains("<")) {
                parsePolar(complex);
            } else {
                parseRectangular(complex);
            }
            recToPolar();
        } catch (NumberFormatException e) {
            System.err.println("Failed to parse complex number: " + complex);
            this.real = 0;
            this.imaginary = 0;
            recToPolar();
        }
    }

    // --- Private helpers ---

    private void parsePolar(String input) throws NumberFormatException {
        String[] parts = input.split("<");
        if (parts.length != 2)
            throw new NumberFormatException();

        double modulus = Double.parseDouble(parts[0]);
        // Remove optional degree symbols if present
        String angleStr = parts[1].replace("º", "").replace("°", "");
        double angleDegrees = Double.parseDouble(angleStr);
        double angleRadians = Math.toRadians(angleDegrees);

        this.real = modulus * Math.cos(angleRadians);
        this.imaginary = modulus * Math.sin(angleRadians);
    }

    private void parseRectangular(String input) throws NumberFormatException {
        if (input.equals("i")) {
            this.real = 0;
            this.imaginary = 1;
            return;
        } else if (input.equals("-i")) {
            this.real = 0;
            this.imaginary = -1;
            return;
        }

        if (input.endsWith("i")) {
            input = input.substring(0, input.length() - 1); // remove trailing 'i'
        }

        int opIndex = Math.max(input.lastIndexOf('+'), input.lastIndexOf('-', 1));
        if (opIndex > 0) {
            this.real = Double.parseDouble(input.substring(0, opIndex));
            this.imaginary = Double.parseDouble(input.substring(opIndex));
        } else {
            this.real = Double.parseDouble(input);
            this.imaginary = 0;
        }
    }

    /**
     * Returns the real component of this complex number.
     *
     * <p>
     * The real part is the coefficient that is not multiplied by the imaginary unit
     * 'i'
     * in the standard form of a complex number {@code a + bi}. For example, in the
     * complex number
     * {@code 5 + 2i}, this method would return {@code 5.0}.
     * </p>
     *
     * @return the real part of this complex number.
     */
    @Override
    public double getReal() {
        return real;
    }

    /**
     * Returns the imaginary component of this complex number.
     *
     * <p>
     * The imaginary part is the coefficient of the imaginary unit 'i'
     * in the complex number representation a + bi. For example, in the complex
     * number 3 + 4i,
     * this method would return 4.0.
     * </p>
     *
     * @return the imaginary part of this complex number.
     */
    @Override
    public double getImaginary() {
        return imaginary;
    }

    /**
     * Returns the magnitude (also known as the modulus or absolute value) of this
     * complex number.
     *
     * <p>
     * The magnitude is calculated as the Euclidean distance from the origin in the
     * complex plane,
     * using the formula {@code sqrt(real^2 + imaginary^2)}. It represents the
     * length of the vector
     * from the origin to the point defined by this complex number.
     * </p>
     *
     * @return the magnitude of this complex number.
     */
    @Override
    public double getMagnitude() {
        return magnitude;
    }

    /**
     * Returns the angle (or argument) of this complex number in degrees.
     *
     * <p>
     * The angle is the direction of the vector from the origin to this complex
     * number
     * in the complex plane, measured counterclockwise from the positive real axis.
     * It is expressed in degrees, typically in the range [-180, 180] or [0, 360].
     * </p>
     *
     * @return the angle of this complex number in degrees.
     */
    @Override
    public double getAngleDegrees() {
        return angleDegrees;
    }

    /**
     * Returns the angle (or argument) of this complex number in radians.
     *
     * <p>
     * The angle is the same as returned by {@link #getAngleDegrees()}, but in
     * radians instead of degrees.
     * The value is typically in the range [-π, π] or [0, 2π], depending on the
     * implementation.
     * </p>
     *
     * @return the angle of this complex number in radians.
     */
    @Override
    public double getAngleRadians() {
        return angleRadians;
    }

    /**
     * Converts the complex number from rectangular (a + bi) form to polar form (r <
     * θ).
     * Handles special cases like NaN and Infinity, and rounds the results for
     * precision.
     */
    @Override
    public void recToPolar() {
        if (Double.isNaN(real) || Double.isNaN(imaginary)) {
            setPolar(Double.NaN, Double.NaN, Double.NaN);
            return;
        }

        if (Double.isInfinite(real) || Double.isInfinite(imaginary)) {
            double angleRad = Math.atan2(imaginary, real);
            setPolar(Double.POSITIVE_INFINITY, angleRad, Math.toDegrees(angleRad));
            return;
        }

        double mag = Math.hypot(real, imaginary); // More stable than sqrt(real^2 + imag^2)
        double angleRad = Math.atan2(imaginary, real);
        double angleDeg = Math.toDegrees(angleRad);

        setPolar(round(mag, 3), round(angleRad, 4), round(angleDeg, 3));
    }

    /**
     * Converts the complex number from polar form (r < θ) to rectangular form (a +
     * bi).
     * The angle must be in radians. Results are rounded for readability and
     * consistency.
     */
    @Override
    public void polarToRec() {
        double re = magnitude * Math.cos(angleRadians);
        double im = magnitude * Math.sin(angleRadians);
        this.real = round(re, 4);
        this.imaginary = round(im, 4);
    }

    /**
     * Helper method to set polar fields with rounded values.
     */
    private void setPolar(double mag, double rad, double deg) {
        this.magnitude = mag;
        this.angleRadians = rad;
        this.angleDegrees = deg;
    }

    /**
     * Utility method to round a double to a specific number of decimal places.
     */
    private static double round(double value, int places) {
        return new BigDecimal(value).setScale(places, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * Displays the complex number in rectangular form (a + bi) using standard
     * formatting.
     * Handles cases where real or imaginary parts are zero and simplifies output
     * for readability.
     */
    @Override
    public void showRec() {
        if (imaginary == 0) {
            System.out.println(String.format("%.4f", real));
        } else if (real == 0) {
            System.out.println(String.format("%.4fi", imaginary));
        } else {
            String sign = imaginary < 0 ? " - " : " + ";
            double absImag = Math.abs(imaginary);
            System.out.println(String.format("%.4f%s%.4fi", real, sign, absImag));
        }
    }

    /**
     * Displays the complex number in polar form: magnitude < angle°
     * The angle is shown in degrees with one decimal place.
     */
    @Override
    public void showPolar() {
        System.out.println(String.format("%.4f < %.2f°", magnitude, angleDegrees));
    }

    /**
     * Adds another complex number to this complex number and updates the result.
     *
     * <p>
     * This method performs the addition of the real and imaginary parts of two
     * complex numbers.
     * It updates the real and imaginary parts of this complex number and
     * recalculates its
     * polar representation (magnitude and angle).
     * </p>
     *
     * @param a The complex number to be added to this complex number.
     * @return The updated {@code Complex} object, which now represents the sum of
     *         the two complex numbers.
     *         The result is the same object (this), modified in place.
     */

    public Complex add(Complex a) {
        this.real += a.getReal();
        this.imaginary += a.getImaginary();
        recToPolar();
        return this;
    }

    /**
     * Subtracts the given complex number from this complex number.
     * <p>
     * This method performs the subtraction of two complex numbers in rectangular
     * form
     * (a + bi), by subtracting their real and imaginary components.
     * </p>
     * 
     * @param a the complex number to subtract from this complex number
     * @return the current instance of this complex number with the updated value
     *         after the subtraction
     */
    public Complex subtract(Complex a) {
        this.real -= a.getReal();
        this.imaginary -= a.getImaginary();
        recToPolar(); // Updates magnitude and angle in polar form.
        return this;
    }

    /**
     * Multiplies this complex number by another complex number.
     * <p>
     * This method performs the multiplication of two complex numbers in rectangular
     * form
     * (a + bi), using the formula:
     * {@code (a + bi) * (c + di) = (ac - bd) + (ad + bc)i}.
     * </p>
     * 
     * @param b the complex number to multiply by this complex number
     * @return the current instance of this complex number with the updated value
     *         after the multiplication
     */
    public Complex multiply(Complex b) {
        double newReal = this.getReal() * b.getReal() - this.getImaginary() * b.getImaginary();
        double newImaginary = this.getReal() * b.getImaginary() + this.getImaginary() * b.getReal();

        this.real = newReal;
        this.imaginary = newImaginary;
        recToPolar(); // Updates magnitude and angle in polar form.
        return this;
    }

    /**
     * Divides this complex number by another complex number.
     * <p>
     * This method performs the division of two complex numbers in polar form (r <
     * θ)
     * using the formula:
     * {@code (r1 < θ1) / (r2 < θ2) = (r1 / r2) < (θ1 - θ2)}.
     * </p>
     * <p>
     * If the divisor has a magnitude of zero, an {@link ArithmeticException} is
     * thrown.
     * </p>
     * 
     * @param b the complex number to divide this complex number by
     * @return the current instance of this complex number with the updated value
     *         after the division
     * @throws ArithmeticException if the divisor has a magnitude of zero
     */
    public Complex divide(Complex b) {
        if (b.getMagnitude() == 0) {
            throw new ArithmeticException("Division by zero not possible");
        }

        this.magnitude = this.magnitude / b.getMagnitude();
        this.angleRadians = this.angleRadians - b.getAngleRadians();

        this.magnitude = round(this.magnitude, 4);
        this.angleRadians = round(this.angleRadians, 4);

        polarToRec();
        return this;
    }

    /**
     * Sums two complex numbers.
     * <p>
     * This method performs the sum of two complex numbers in rectangular form
     * (a + bi), by adding their real and imaginary components separately.
     * </p>
     * 
     * @param a the first complex number to sum
     * @param b the second complex number to sum
     * @return a new {@link Complex} instance representing the sum of the two
     *         complex numbers
     */
    public static Complex sum(Complex a, Complex b) {
        double real = a.getReal() + b.getReal();
        double ima = a.getImaginary() + b.getImaginary();
        return new Complex(real, ima);
    }

    /**
     * Subtracts two complex numbers.
     * <p>
     * This method performs the subtraction of two complex numbers in rectangular
     * form
     * (a + bi), by subtracting their real and imaginary components separately.
     * </p>
     *
     * @param a the first complex number (minuend)
     * @param b the second complex number (subtrahend)
     * @return a new {@link Complex} instance representing the result of the
     *         subtraction
     */
    public static Complex subtraction(Complex a, Complex b) {
        double real = a.getReal() - b.getReal();
        double ima = a.getImaginary() - b.getImaginary();
        return new Complex(real, ima);
    }

    /**
     * Multiplies two complex numbers.
     * <p>
     * This method multiplies two complex numbers in rectangular form (a + bi) by
     * using
     * the distributive property of multiplication, which results in the following
     * formula:
     * (a + bi) * (c + di) = (ac - bd) + (ad + bc)i.
     * </p>
     *
     * @param a the first complex number
     * @param b the second complex number
     * @return a new {@link Complex} instance representing the product of the two
     *         complex numbers
     */
    public static Complex multiply(Complex a, Complex b) {
        double real = a.getReal() * b.getReal() - a.getImaginary() * b.getImaginary();
        double imag = a.getReal() * b.getImaginary() + a.getImaginary() * b.getReal();

        return new Complex(real, imag);
    }

    /**
     * Divides two complex numbers.
     * <p>
     * This method divides two complex numbers in rectangular form (a + bi) and (c +
     * di)
     * by multiplying the numerator by the conjugate of the denominator and then
     * dividing
     * by the magnitude squared of the denominator. The formula for division is as
     * follows:
     * </p>
     * 
     * <pre>
     * (a + bi) / (c + di) = [(a + bi) * (c - di)] / (c² + d²)
     * </pre>
     * 
     * @param a the dividend complex number (a + bi)
     * @param b the divisor complex number (c + di)
     * @return a new {@link Complex} instance representing the quotient of the two
     *         complex numbers
     * @throws ArithmeticException if attempting to divide by a complex number with
     *                             zero magnitude
     */
    public static Complex divide(Complex a, Complex b) {
        if (b.getMagnitude() == 0) {
            throw new ArithmeticException("Não é possível dividir por um número complexo com magnitude zero.");
        }
    
        // Calculando a nova magnitude e ângulo
        double mag = a.getMagnitude() / b.getMagnitude();  // Dividindo as magnitudes
        double ang = a.getAngleDegrees() - b.getAngleDegrees(); // Subtraindo os ângulos
    
        // Arredondando os valores para 4 casas decimais para precisão
        mag = round(mag, 5);
        ang = round(ang, 3); // Angulo arredondado para 2 casas decimais
    
        // Criando a string para o número complexo resultante na forma polar
        String complex = mag +"<"+ String.format("%.2f", ang);
        
        // Retornando um novo número complexo com a string gerada
        return new Complex(complex);
    }

    public Complex inverse(Complex complex){
        return new Complex(1/complex.getReal(), 1/complex.getImaginary());
    }
    /**
     * Returns the string representation of this complex number in rectangular form
     * (a + bi).
     * <p>
     * The output is formatted to show the real and imaginary parts, with the
     * imaginary part
     * followed by 'i'. If either part is zero, it is omitted from the output.
     * </p>
     *
     * @return a string representation of this complex number in rectangular form.
     */
    @Override
    public String toString() {
        // Verifica se a parte decimal é zero para exibir sem casas decimais
        String realPart = (real == (int) real) ? String.format("%d", (int) real) : String.format("%s", real);
        String imaginaryPart = (imaginary == (int) imaginary) ? String.format("%d", (int) imaginary) : String.format("%s", imaginary);

        return realPart + " + " + imaginaryPart + "i";
    }


    
}

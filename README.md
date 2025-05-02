# ComplexNumber Java Library


A comprehensive Java library for working with complex numbers in both rectangular and polar forms. This library supports various operations such as addition, subtraction, multiplication, division, and conversion between forms.

## Features

- Create complex numbers from strings or real/imaginary parts.
- Parse rectangular (e.g., `3+4i`) and polar (e.g., `5<53.13`) forms.
- Retrieve real, imaginary, magnitude, and angle (degrees/radians).
- Convert between rectangular and polar representations.
- Perform arithmetic operations: add, subtract, multiply, divide.
- Includes static utility methods for operations.

## Usage

```java
Complex a = new Complex(3, 4);       // Rectangular form
Complex b = new Complex("5<53.13");  // Polar form
a.add(b).showRec();                  // Display result in rectangular form
a.showPolar();                       // Display result in polar form
```

## Documentation

📚 Full documentation is available [here]https://fabulous-gingersnap-63608b.netlify.app/com/heringer/package-summary).

## Installation


Add the following to your `build.gradle`:

```gradle
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://jitpack.io' }
		}
	}

dependencies {
    implementation 'com.github.emilioheringer:ComplexNumber:1.0.0'
}
```
Add the following to your `pom.xml`:

## License

MIT License

[![JitPack](https://jitpack.io/v/emilioheringer/ComplexNumber.svg)](https://jitpack.io/#emilioheringer/ComplexNumber)
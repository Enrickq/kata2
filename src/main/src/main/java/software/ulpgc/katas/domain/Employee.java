
package software.ulpgc.katas.domain;

import java.util.Objects;

public record Employee(
        String name,
        Department department,
        int monthlyIncome
) {

    public Employee {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre no puede estar vacio");
        }

        name = name.trim();

        Objects.requireNonNull(
                department, "El departamento es obligatorio");

        if (monthlyIncome < 0) {
            throw new IllegalArgumentException(
                    "El sueldo no puede ser negativo");
        }
    }
}

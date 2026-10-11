package software.ulpgc.katas.domain;

public record Department(String name) {
    public Department {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "El departamento no puede estar vacío");
        }
        name = name.trim();
    }
}

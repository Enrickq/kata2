
package software.ulpgc.katas.service;

import software.ulpgc.katas.domain.Department;
import software.ulpgc.katas.domain.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DepartmentCounter {

    public Map<Department, Long> count(
            List<Employee> employees
    ) {

        Map<Department, Long> counts = new HashMap<>();

        for (Employee employee : employees) {
            counts.merge(
                    employee.department(), 1L, Long::sum);
        }

        return Map.copyOf(counts);
    }
}


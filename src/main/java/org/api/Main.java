package org.api;

import org.api.entities.Employee;
import org.api.entities.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.api.constants.Constants.pathToCSV;
import static org.api.helpers.Logger.log;

public class Main {
    public static void main(String[] args) {

        printResult();
    }

    private static void printResult() {
        List<Employee> employees = getDataFromCSV(pathToCSV);
        if (employees.isEmpty()) {
            log("CSV file is empty.");
        }

        List<Pair> pairs = findAllPairsWhoWorkedTogether(employees);
        if (pairs.isEmpty()) {
            log("No pairs who worked together found.");
        }

        String thePair = findThePair(pairs);
        log(thePair);
    }

    // TODO: Use DateParser for multiple date format
    private static List<Employee> getDataFromCSV(String path) {
        // in order to prevent having duplicated employeeIds
        List<Employee> employees = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 4) {
                    mapCSVDataToEmployee(values, employees);
                }
            }
        } catch (Exception e) {
            log("Failed to get data from CSV. Error: " + e.getMessage());
        }

        return employees;
    }

    private static void mapCSVDataToEmployee(String[] values, List<Employee> employees) {
        int employeeId = Integer.parseInt(values[0].trim()); // values.getFirst()
        int projectId = Integer.parseInt(values[1].trim());
        LocalDate dateFrom = LocalDate.parse(values[2].trim());
        LocalDate dateTo = values[3].trim().equals("NULL") ? LocalDate.now() : LocalDate.parse(values[3].trim());

        employees.add(new Employee(employeeId, projectId, dateFrom, dateTo));
    }

    public static List<Pair> findAllPairsWhoWorkedTogether(List<Employee> employees) {

        List<Pair> pairs = new ArrayList<>();
        for (int i = 0; i < employees.size() - 1; i++) {
            for (int j = i + 1; j < employees.size(); j++) {
                Employee firstEmployee = employees.get(i);
                Employee secondEmployee = employees.get(j);

                if (firstEmployee.getProjectId() == secondEmployee.getProjectId() && haveWorkedTogether(firstEmployee, secondEmployee)) {
                    long duration = calculateDuration(firstEmployee, secondEmployee);

                    if (duration > 0) {
                        updatePairsCollection(pairs, firstEmployee, secondEmployee, duration);
                    }
                }
            }
        }
        return pairs;
    }

    private static boolean haveWorkedTogether(Employee firstEmployee, Employee secondEmployee) {
        return (firstEmployee.getDateFrom().isBefore(secondEmployee.getDateTo()) || firstEmployee.getDateFrom().isEqual(secondEmployee.getDateTo())) && (firstEmployee.getDateTo().isAfter(secondEmployee.getDateFrom()) || firstEmployee.getDateTo().isEqual(secondEmployee.getDateFrom()));
    }

    private static long calculateDuration(Employee firstEmployee, Employee secondEmployee) {
        LocalDate periodStartDate = firstEmployee.getDateFrom().isBefore(secondEmployee.getDateFrom()) ? secondEmployee.getDateFrom() : firstEmployee.getDateFrom();

        LocalDate periodEndDate = firstEmployee.getDateTo().isBefore(secondEmployee.getDateTo()) ? firstEmployee.getDateTo() : secondEmployee.getDateTo();

        // Including leap years as well
        return Math.abs(DAYS.between(periodStartDate, periodEndDate));
    }

    private static void updatePairsCollection(List<Pair> pairs, Employee firstEmployee, Employee secondEmployee, long duration) {
        AtomicBoolean isPresent = new AtomicBoolean(false);
        pairs.forEach(pair -> {
            if (isInCollection(pair, firstEmployee.getEmployeeId(), secondEmployee.getEmployeeId())) {
                pair.addDuration(duration);
                isPresent.set(true);
            }
        });
        if (!isPresent.get()) {
            Pair newPair = new Pair(firstEmployee.getEmployeeId(), secondEmployee.getEmployeeId(), duration);
            pairs.add(newPair);
        }
    }

    private static boolean isInCollection(Pair pair, long firstEmployeeId, long secondEmployeeId) {
        return (pair.getFirstEmployeeId() == firstEmployeeId && pair.getSecondEmployeeId() == secondEmployeeId)
                || (pair.getFirstEmployeeId() == secondEmployeeId && pair.getSecondEmployeeId() == firstEmployeeId);
    }

    private static String findThePair(List<Pair> pairs) {
        String result = "";
        long maxDuration = 0;

        for (Pair pair : pairs) {
            String key = pair.getFirstEmployeeId() + ", " + pair.getSecondEmployeeId();
            if (pair.getDuration() > maxDuration) {
                result = key + ", " + pair.getDuration();
                maxDuration = pair.getDuration();
            }
        }

        return result;
    }
}

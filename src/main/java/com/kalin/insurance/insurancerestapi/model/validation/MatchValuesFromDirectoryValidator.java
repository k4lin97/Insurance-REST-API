package com.kalin.insurance.insurancerestapi.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Validates whether the value matches any value from a specified file.
 */
public class MatchValuesFromDirectoryValidator implements ConstraintValidator<MatchValuesFromDirectoryConstraint, String> {
    private List<String> availableValues;

    /**
     * Initializes availableValues list with values from file directory.
     */
    @Override
    public void initialize(MatchValuesFromDirectoryConstraint constraintAnnotation) {
        String fileDirectory = constraintAnnotation.fileDirectory();
        URL url = MatchValuesFromDirectoryValidator.class.getResource(fileDirectory);
        try {
            assert url != null;
            Path path = Paths.get(url.toURI());
            try (Stream<String> lines = Files.lines(path)) {
                availableValues = lines.map(String::toLowerCase).collect(Collectors.toCollection(ArrayList::new));
            } catch (Exception e) {
                throw new Exception(e);
            }
        } catch (Exception e) {
            // In case of wrong file name initialize empty list
            availableValues = new ArrayList<>();
        }
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        return availableValues.contains(s.toLowerCase());
    }
}

package com.kalin.insurance.insurancerestapi.system.exception;

import com.kalin.insurance.insurancerestapi.address.exception.AddressNotFoundException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeToAddressAssignedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeDuplicatedException;
import com.kalin.insurance.insurancerestapi.address.type.exception.AddressTypeNotFoundException;
import com.kalin.insurance.insurancerestapi.car.exception.CarNotFoundException;
import com.kalin.insurance.insurancerestapi.client.exception.ClientNotFoundException;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverDuplicatedException;
import com.kalin.insurance.insurancerestapi.cover.exception.CoverNotFoundException;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyCarsCountNotEqualException;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotConsistentException;
import com.kalin.insurance.insurancerestapi.policy.exception.PolicyNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

/**
 * Handles exceptions within the api.
 */
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, apiError, headers, apiError.getError(), request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = ex.getErrorCode() + ". Expected: " + ex.getRequiredType() + ", got: " + ex.getValue() + ".";
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
        return handleExceptionInternal(ex, apiError, headers, apiError.getError(), request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage());
        return handleExceptionInternal(ex, apiError, headers, apiError.getError(), request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : ex.getConstraintViolations()) {
            errors.add(constraintViolation.getMessage());
        }
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String error = ex.getMostSpecificCause().toString();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }

    @ExceptionHandler({InvalidDataAccessApiUsageException.class})
    public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex, WebRequest request) {
        String error = ex.getMostSpecificCause().toString();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, error);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }

    @ExceptionHandler({AddressNotFoundException.class,
            AddressTypeNotFoundException.class,
            ClientNotFoundException.class,
            CarNotFoundException.class,
            CoverNotFoundException.class,
            PolicyNotFoundException.class})
    public ResponseEntity<Object> handleNotFound(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }

    @ExceptionHandler({AddressTypeDuplicatedException.class,
            AddressTypeToAddressAssignedException.class,
            CoverDuplicatedException.class,
            PolicyCarsCountNotEqualException.class})
    public ResponseEntity<Object> handleConflictException(RuntimeException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.CONFLICT, ex.getMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }

    @ExceptionHandler({PolicyNotConsistentException.class})
    public ResponseEntity<Object> handlePolicyNotConsistentException(PolicyNotConsistentException ex, WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getErrors());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getError(), request);
    }
}

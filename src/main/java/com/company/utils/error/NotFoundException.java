package com.company.utils.error;

/**
 * In a real application, this exception could be translated into a 404 Not Found response.
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message);
  }
}

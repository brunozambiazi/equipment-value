package com.company.utils.error;

/**
 * In a real application, this exception could be translated into a 500 Internal Server Error response.
 */
public class InvalidDataException extends RuntimeException {

  public InvalidDataException(String message) {
    super(message);
  }
}

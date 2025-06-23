package com.company.utils.error;

/**
 * In a real application, this exception could be translated into a 400 Bad Request response.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {
    super(message);
  }
}

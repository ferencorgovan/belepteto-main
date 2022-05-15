package hu.nye.progkor.belepteto.model.exception;

public class WrongDirectionException extends RuntimeException {
  public WrongDirectionException() {
  }

  public WrongDirectionException(final String message) {
    super(message);
  }

  public WrongDirectionException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
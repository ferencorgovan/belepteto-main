package hu.nye.progkor.belepteto.model.exception;

public class WrongDataException extends RuntimeException {
  public WrongDataException() {
  }

  public WrongDataException(final String message) {
    super(message);
  }

  public WrongDataException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
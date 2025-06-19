package daj.common.error;

public class ErrorResponse extends RuntimeException {

  private int httpStatus;
  
  public ErrorResponse(String msj, int httpStatus, String cause) {
    super(msj, new Throwable(cause));
    this.httpStatus = httpStatus;
  }

  public int getHttpStatus() {
    return this.httpStatus;
  }

  public ErrorResponseBody getBody() {
    return new ErrorResponseBody(this.getMessage(), this.getCause().getMessage());
  }
  
}

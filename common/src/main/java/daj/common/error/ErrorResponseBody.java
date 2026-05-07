package daj.common.error;

import java.util.List;

public class ErrorResponseBody {

  public final String message;

  public String cause;

  public List<Cause> causes;

  public ErrorResponseBody(String msg, String causeArg) {
    this.message = msg;
    this.cause = causeArg;
  }

  public ErrorResponseBody(String msg, List<Cause> causesArg) {
    this.message = msg;
    this.cause = msg;
    this.causes = causesArg;
  }

}
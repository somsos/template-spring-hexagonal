package daj.common.error;

public class Cause {

  private final String path;

  private final String message;

  public Cause(String pathArg, String msg) {
    this.path = pathArg;
    this.message = msg;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }
  
}

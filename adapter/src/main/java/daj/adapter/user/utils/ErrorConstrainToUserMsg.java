package daj.adapter.user.utils;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;

import daj.common.error.ErrorResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ErrorConstrainToUserMsg {

  //USER
  public final static String usernameInUse = "Username already in use";
  public final static String userEmailInUse = "email already in use";

  //PRODUCT
  public final static String productNameInUse = "product name already in use";

  //Note: don't move the order, because is used by the test
  public final static Map<String, ErrorResponse> errAndMsg = Map.of(
    "products_name_key", new ErrorResponse(productNameInUse, 400, "data integrity"), 
    "users_username_key", new ErrorResponse(usernameInUse, 400, "data integrity"),
    "users_email_key", new ErrorResponse(userEmailInUse, 400, "data integrity")
  );


  public static ErrorResponse getUserMsg(DataIntegrityViolationException ex) {
    final var exMsg = ex.getCause().getMessage();
    for (var entry : errAndMsg.entrySet()) {
      if(exMsg.contains(entry.getKey())) {
        return entry.getValue();
      }
    }

    log.warn(exMsg);
    return new ErrorResponse("error, try later", 400, "data integrity");
  }
  
}

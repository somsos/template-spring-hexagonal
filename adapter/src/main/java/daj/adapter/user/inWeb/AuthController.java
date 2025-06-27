package daj.adapter.user.inWeb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import daj.adapter.user.inWeb.reqAndResp.LoginRequest;
import daj.adapter.user.inWeb.reqAndResp.LoginResponse;
import daj.adapter.user.inWeb.reqAndResp.RegisterRequest;
import daj.adapter.user.inWeb.reqAndResp.RegisterResponse;
import daj.adapter.user.utils.IUserMapper;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.ILoginInputPort;
import daj.user.visible.port.in.IRegisterInputPort;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {
  
  public static final String LOGIN_PATH = "/auth/create-token";
  public static final String REGISTER_PATH = "/auth/register";
  
  public static final String CHECK_REGISTERED_USER = "/auth/is-logged";
  public static final String CHECK_USERS_ROLE = "/auth/check-user-role";
  public static final String CHECK_PRODUCT_ROLE = "/auth/check-product-role";

  
  final private ILoginInputPort loginPortInput;

  final private IRegisterInputPort registerInputPort;

  final private IUserMapper mapper;

  @PostMapping(LOGIN_PATH)
  public LoginResponse login(@Valid @RequestBody LoginRequest input) {
    final UserDto inputMapped = mapper.loginRequestToDto(input);
    final UserDto logged = loginPortInput.login(inputMapped);
    final var response = new LoginResponse(logged.getId(), logged.getUsername(), logged.getRoles(), logged.getToken());
    return response;
  }

  @PostMapping(REGISTER_PATH)
  @ResponseStatus(HttpStatus.CREATED)
  public RegisterResponse register(@Valid @RequestBody RegisterRequest input) {
    final UserDto inputMapped = mapper.registerRequestToDto(input);
    final UserDto registered = registerInputPort.register(inputMapped);
    final RegisterResponse output = mapper.dtoToRegisterResponse(registered);
    return output;
  }

  @GetMapping(CHECK_REGISTERED_USER)
  public String isLogged() {
    return "valid registered user";
  }

  @GetMapping(CHECK_USERS_ROLE)
  public String checkUsersRole() {
    return "users role is OK";
  }

  @GetMapping(CHECK_PRODUCT_ROLE)
  public String checkProductsRole() {
    return "products role ok";
  }

}

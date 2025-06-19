package daj.adapter.common.authConfig;


import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import daj.adapter.user.outDB.entity.RoleEntity;
import daj.common.depends.user.UserMDto;
import daj.common.error.ErrorResponse;
import daj.user.visible.port.dto.UserDto;
import daj.user.visible.port.in.IJwtService;
import daj.user.visible.port.out.IUserReaderOutputPort;

import java.io.IOException;

@Component
@Log4j2
@Profile("default")
public class AuthJwtFilter implements Filter {

    @Autowired
    private IJwtService jwtService;

    @Autowired
    private IUserReaderOutputPort userReadings;

    @Override
    public void init(FilterConfig filterConfig) {
        this.jwtService = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(IJwtService.class);

        this.userReadings = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(IUserReaderOutputPort.class);

        assert (userReadings != null);
        assert (jwtService != null);
    }

    @Override
    public void doFilter(ServletRequest requestArg, ServletResponse responseArg, FilterChain filterChain) throws IOException, ServletException {
        final var request = (HttpServletRequest) requestArg;
        final var response = (HttpServletResponse) responseArg;
        final var method = request.getMethod();
        if(method.equals("OPTIONS")) {
          filterChain.doFilter(request, response);
          return ;
        }
        
        try {
            this.jwtValidation(request, response, filterChain);
        } catch (ErrorResponse ex) {
            this.respondUserError(response, ex);
            return ;
        } catch (Exception e) {
            this.respondeUnknownError(response, e);
            return ;
        }

        filterChain.doFilter(request, response);
    }

    private void jwtValidation(HttpServletRequest request, ServletResponse response, FilterChain filterChain) {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String idUserInput = null;

        // Check if the header starts with "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Extract token
            idUserInput = jwtService.extractUsername(token); // Extract username from token
        }        

        // If the token is valid and no authentication is set in the context
        final var currentSession = SecurityContextHolder.getContext().getAuthentication();
        if (authHeader != null && currentSession == null) {

            Integer idUser = null;
            try {
                idUser = Integer.parseInt(idUserInput);    
            } catch (NumberFormatException e) {
                throw new ErrorResponse("invalid token", 400, "expected sub as integer");
            }

            UserDto authInfoFound = userReadings.findById(idUser);

            // Validate token and set authentication
            boolean validToken = jwtService.validateToken(token, authInfoFound);

            if(validToken == false) {
                throw new ErrorResponse("Invalid token", 400, "validation failed");
            }
            
            final var authToken = this.fromAuthInfoToAuthToken(authInfoFound);
            
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            
        }
    }

    private UsernamePasswordAuthenticationToken fromAuthInfoToAuthToken(UserDto authInfo) {
      final var auths = authInfo.getRoles().stream()
        .map(r -> new RoleEntity(r.getId(), r.getAuthority(), null))
        .toList()
      ;

      List<String> roles = new ArrayList<>();
      if(authInfo.getRoles() != null) {
        roles = authInfo.getRoles().stream().map(rDto -> rDto.getAuthority()).toList();
      }

      final var userInfo = new UserMDto(authInfo.getId(), roles);
      final var userDetails = new UsernamePasswordAuthenticationToken(userInfo, null, auths);
      return userDetails;
    
    }

    private void respondUserError(HttpServletResponse response, ErrorResponse ex) {
        Map<String, String> errorDetails = new HashMap<>();
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("cause", ex.getCause().getLocalizedMessage());
        log.warn("handled in FilterErrorHandler.respondUserError: " + ex.getMessage());
        this.sendResponse(response, errorDetails, ex.getHttpStatus());
    }

  private void respondeUnknownError(HttpServletResponse response, Exception e) {
    Map<String, String> errorDetails = new HashMap<>();
    errorDetails.put("message", "Internal error, try later or contact admins");
    errorDetails.put("cause", e.getLocalizedMessage().replace("\"", ""));
    errorDetails.put("exType", e.getClass().getName());
    log.warn("handled in FilterErrorHandler.respondeUnknownError: " + e.getMessage());
    this.sendResponse(response, errorDetails, 500);
  }

  private void sendResponse(HttpServletResponse response, Map<String, String> body, int status) {

    response.setStatus(status);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    try {
      final var mapper = new ObjectMapper();
      response.getWriter().write(mapper.writeValueAsString(body));
    } catch (Exception e) {
      final String message = "Internal error, try later or contact admins";
      final String cause = "Error converting filter error to json";
      final String json = String.format("{\"cause\":\"%s\",\"message\":\"%s\"}", cause, message);
      try {
        response.getWriter().write(json);
      } catch (IOException e1) {
        throw new RuntimeException("Fatal as-cd-vf-5t-kj");
      }
    }

  }

}
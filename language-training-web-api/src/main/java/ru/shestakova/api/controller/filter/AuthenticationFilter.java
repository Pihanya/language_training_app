package ru.shestakova.api.controller.filter;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.shestakova.api.configuration.AuthenticationProperties;
import ru.shestakova.api.service.AuthenticationService;

@Component
@Order(1)
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

  @NonNull AuthenticationProperties properties;
  @NonNull AuthenticationService authService;

  private boolean isWhitelisted;
  private ListMultimap<HttpMethod, Pattern> patterns;

  @PostConstruct
  private void postConstruct() {
    this.isWhitelisted = properties.getWhitelistMode();
    this.patterns = ArrayListMultimap.create();

    for (String entry : properties.getPatterns()) {
      if (entry.isBlank()) {
        continue;
      }

      entry = entry.strip();
      String[] entryArray = entry.split(" ");
      if (entryArray.length != 2) {
        throw new IllegalArgumentException("Wrong entry: " + entry);
      }

      Pattern pattern = Pattern.compile(entryArray[1]);
      if (entryArray[0].equals("*")) {
        Stream.of(HttpMethod.values()).forEach(method -> patterns.put(method, pattern));
      } else {
        Stream.of(entryArray[0].toUpperCase().split(","))
              .map(HttpMethod::resolve)
              .forEach(method -> patterns.put(method, pattern));
      }
    }
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response,
      FilterChain chain
  ) throws ServletException, IOException {
    if (request.getMethod().equals("OPTIONS")) {
      chain.doFilter(request, response);
      return;
    }

    var method = HttpMethod.resolve(request.getMethod());
    var patternList = patterns.get(method);

    boolean hasMatches = false;
    for (var pattern : patternList) {
      hasMatches |= pattern.matcher(request.getRequestURI()).matches();
    }

    if (isWhitelisted && !hasMatches) {
      // Если включен режим белого списка URL'ов, подлежащих авторизации (кто попал - тот авторизуется)
      // и запрашиваемый URL не в белом списке
      chain.doFilter(request, response);
      return;
    } else if (!isWhitelisted && hasMatches) {
      // Если включен режим черного списка URL'ов, подлежащих авторизации (кто попал - тот не авторизуется)
      // и запрашиваемый URL не в черном списке
      chain.doFilter(request, response);
      return;
    }

    if (request.getHeader("Username") == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
    } else if (request.getHeader("Authorization") == null) {
      response.setStatus(HttpStatus.FORBIDDEN.value());
    } else if (!request.getHeader("Authorization").contains("Bearer ")) {
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    } else {
      var username = request.getHeader("Username");
      var tokenStr = request.getHeader("Authorization").split(" ")[1];

      UUID token;
      try {
        token = UUID.fromString(tokenStr);
      } catch (IllegalArgumentException ex) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return;
      }

      var authResponse = authService.authenticateUserByToken(username, token);
      if (authResponse.getStatus().isSuccess()) {
        request.setAttribute("UserId", authResponse.getUserId());
        chain.doFilter(request, response);
      } else {
        response.setStatus(
            HttpStatus.UNAUTHORIZED.value(),
            authResponse.getStatus().toString()
        );
      }
    }
  }
}

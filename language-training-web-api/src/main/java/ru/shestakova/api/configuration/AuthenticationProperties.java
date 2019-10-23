package ru.shestakova.api.configuration;

import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "api.auth")
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class AuthenticationProperties {

  private Boolean whitelistMode = Boolean.TRUE;
  private List<String> patterns = Arrays.asList(
      "POST /v1/api/forum/topics",
      "PATCH /v1/api/forum/topics/[0-9]+",
      "POST,DELETE /v1/api/forum/topics/[0-9]+/[a-zA-Z]+",
      "POST /v1/api/forum/messages/[0-9]+",
      "PATCH /v1/api/forum/messages/[0-9]+/[0-9]+",
      "DELETE /v1/api/forum/messages/[0-9]+/[0-9]+/delete",
      "* /v1/api/users"
  );
}

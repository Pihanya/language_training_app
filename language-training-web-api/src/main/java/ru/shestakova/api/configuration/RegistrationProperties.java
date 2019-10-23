package ru.shestakova.api.configuration;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "api.registration")
@Data @Accessors(chain = true) @FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor @AllArgsConstructor
public class RegistrationProperties {
  private String defaultScreenName = "Anonymous";
  private String defaultAvatarUrl = "https://thesocietypages.org/socimages/files/2009/05/vimeo.jpg";

}

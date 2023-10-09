package danycode.dsm.config;

import danycode.dsm.controller.UsersPresentationProps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "dsm.presentation.users")
@Component
public class UsersPresentationConfigProps implements UsersPresentationProps {
   private int pageSize=10;
}

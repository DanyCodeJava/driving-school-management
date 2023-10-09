package danycode.dsm.config;

import danycode.dsm.repository.RepoUserPhotosProps;
import danycode.dsm.service.ServiceUserPhotoProps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Data
@ConfigurationProperties(prefix = "dsm",ignoreInvalidFields = true)
@Component
public class UserPhotosConfigProps implements RepoUserPhotosProps, ServiceUserPhotoProps {
    private Path userPhotosDir;
    private Path defaultPhotoPath;
    private byte[] magicNumberPNG;
    private byte[] magicNumberJPG;


}

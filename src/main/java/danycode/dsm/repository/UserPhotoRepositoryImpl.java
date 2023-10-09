package danycode.dsm.repository;

import danycode.dsm.entity.PhotoFile;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RequiredArgsConstructor
@Repository
public class UserPhotoRepositoryImpl implements UserPhotoRepository {


    private final RepoUserPhotosProps userPhotosProps;

    @Override
    public void save(PhotoFile userPhoto, long id) {
        var photoFile = userPhotosProps.getUserPhotosDir().resolve(String.valueOf(id));
        try {
            //această linie de cod copiază conținutul fotografiei din fluxul de intrare (InputStream) în fișierul specificat de către calea photoFile.
            // Fotografia este astfel salvată în sistemul de fișiere pentru utilizatorul cu ID-ul specificat.
            Files.copy(userPhoto.getInputStream(), photoFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UserPhotoSaveException("Failed to save photo for user: " + id, e);
        }
    }

    @Override
    @Nullable
    public byte[] getPhotoById(Long id) {
        var photoFile = userPhotosProps.getUserPhotosDir().resolve(String.valueOf(id));
        if (!Files.exists(photoFile)) {
            return null;
        }
        var outputStream = new ByteArrayOutputStream();
        try {
            Files.copy(photoFile, outputStream);
        } catch (IOException e) {
            throw new UserPhotoReadException("Failed to read photo for user " + id, e);
        }
        return outputStream.toByteArray();
    }

    @Override
    public byte[] getDefaultPhoto() {
        var defaultPhotoPath = userPhotosProps.getDefaultPhotoPath();
        var outputStream = new ByteArrayOutputStream();
        try {
            Files.copy(defaultPhotoPath, outputStream);
        } catch (IOException e) {
            throw new UserDefaultPhotoReadException("Failed to read default photo for user ", e);
        }
        return outputStream.toByteArray();
    }


}

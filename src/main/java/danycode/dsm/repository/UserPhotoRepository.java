package danycode.dsm.repository;

import danycode.dsm.entity.PhotoFile;
import jakarta.annotation.Nullable;

import java.io.IOException;

public interface UserPhotoRepository {
    void save(PhotoFile userPhoto, long id);

    @Nullable
    byte[] getPhotoById( Long id);

    byte[] getDefaultPhoto();


}

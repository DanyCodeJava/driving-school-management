package danycode.dsm.service;

import danycode.dsm.dto.PhotoFileDto;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.SequenceInputStream;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class UserPhotoFileProcessor {

    private final ServiceUserPhotoProps photoProps;

    public @Nullable PhotoFileDto process(@Nullable PhotoFileDto photoFileDto) {
        if (photoFileDto == null) {
            return null;
        }
        byte[] userPhotoMagicNumber = extractUserPhotoMagicNumber(photoFileDto);
        byte[] magicNumberPNG = photoProps.getMagicNumberPNG();
        byte[] magicNumberJPG = photoProps.getMagicNumberJPG();
        if (Arrays.equals(userPhotoMagicNumber, magicNumberPNG) || Arrays.equals(userPhotoMagicNumber, magicNumberJPG)) {
            return new PhotoFileDto(new SequenceInputStream(new ByteArrayInputStream(userPhotoMagicNumber), photoFileDto.getInputStream()));
        } else {
            throw new UserPhotoFileConstraintsException("Unexpected file format");
        }
    }

    public byte[] extractUserPhotoMagicNumber(PhotoFileDto photoFileDto) {

        try {
            return photoFileDto.getInputStream().readNBytes(4);
        } catch (IOException e) {
            throw new UserPhotoFileConstraintsException("File does not meet the minimum length");
        }
    }
}

package danycode.dsm.entity;

import lombok.Value;

import java.io.InputStream;
@Value
public class SimplePhotoFile implements PhotoFile {
      InputStream inputStream;


}

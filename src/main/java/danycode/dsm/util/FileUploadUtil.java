package danycode.dsm.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUploadUtil {

    public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath);
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }
        try(InputStream inputStream = multipartFile.getInputStream()){
            Path filePath = uploadPath.resolve(fileName);
            System.out.println(filePath);

            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException e){
            throw  new IOException("Could not save file: " + fileName, e);
        }
    }
    public static void cleanDir(String dir){
        Path dirPath = Paths.get(dir);
        try{
            Files.list(dirPath).forEach(file ->{  // List all files in the directory
                if(!Files.isDirectory(file)){ // If the file is not a directory
                    try{
                        Files.delete(file); //delete the file
                    }catch (IOException e){
                        System.out.println("Could not delete file: " + file);
                    }

                }
            });
        }catch (IOException e){
            System.out.println("Could not list directory: " + dirPath);
        }
    }
}

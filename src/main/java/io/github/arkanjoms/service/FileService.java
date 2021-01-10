package io.github.arkanjoms.service;

import io.github.arkanjoms.exception.EmptyFileException;
import io.github.arkanjoms.exception.FileTransformException;
import io.github.arkanjoms.exception.InvalidCSVException;
import org.apache.logging.log4j.util.Strings;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {

    private static final String DEFAULT_FILE_NAME = "input.csv";
    private static final String MIME_TYPE_TEXT_CSV = "text/csv";

    public File transformerMultipartToFile(MultipartFile multipartFile) {
        checkIsEmpty(multipartFile);
        try {
            File file = createTempFile(multipartFile);
            validateFile(file);
            try (InputStream inputStream = multipartFile.getInputStream();
                 OutputStream outStream = new FileOutputStream(file)) {
                byte[] buffer = new byte[inputStream.available()];
                int readBytes = inputStream.read(buffer);
                if (readBytes <= 0) {
                    throw new EmptyFileException();
                }
                outStream.write(buffer);
            }
            return file;
        } catch (IOException ex) {
            throw new FileTransformException(ex);
        }
    }

    private void validateFile(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        if (!Objects.equals(mimeType, MIME_TYPE_TEXT_CSV)) {
            throw new InvalidCSVException();
        }
    }

    private File createTempFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = getOriginalFilename(multipartFile);
        String[] filename = getSplitedFilename(originalFilename);
        return File.createTempFile(filename[0], "." + filename[1]);
    }

    private String[] getSplitedFilename(String originalFilename) {
        String[] split = originalFilename.split("\\.");
        String name = split[0];
        String extension = split.length == 2 ? split[1] : "csv";
        return new String[]{name,extension};
    }

    private String getOriginalFilename(MultipartFile multipartFile) {
        return Objects.nonNull(multipartFile) && Strings.isNotEmpty(multipartFile.getOriginalFilename())
                ? Optional.ofNullable(multipartFile.getOriginalFilename()).orElse(DEFAULT_FILE_NAME)
                : DEFAULT_FILE_NAME;
    }

    private void checkIsEmpty(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new EmptyFileException();
        }
    }
}

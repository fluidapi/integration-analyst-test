package io.github.arkanjoms.service;

import io.github.arkanjoms.exception.EmptyFileException;
import io.github.arkanjoms.exception.FileTransformException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceTest {

    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    void transformerMultipartToFile_nullFile() {
        assertThrows(EmptyFileException.class, () -> fileService.transformerMultipartToFile(null));
    }

    @Test
    void transformerMultipartToFile_emptyFile() {
        MultipartFile file = new MockMultipartFile("empty.csv", new byte[0]);
        assertThrows(EmptyFileException.class, () -> fileService.transformerMultipartToFile(file));
    }

    @Test
    void transformerMultipartToFile_ok() {
        byte[] bytes = "date,cases,deaths,uf,time\n2020-01-30,0,0,CE,16:00\n2020-01-30,0,0,MG,16:00".getBytes();
        MultipartFile file = new MockMultipartFile("file", "test.csv", MediaType.TEXT_PLAIN_VALUE, bytes);
        File resultFile = fileService.transformerMultipartToFile(file);
        assertNotNull(resultFile);
        assertTrue(resultFile.getName().startsWith("test"));
        assertTrue(resultFile.getName().endsWith(".csv"));
    }

    @Test
    void transformerMultipartToFile_okNoExtension() {
        byte[] bytes = "date,cases,deaths,uf,time\n2020-01-30,0,0,CE,16:00\n2020-01-30,0,0,MG,16:00".getBytes();
        MultipartFile file = new MockMultipartFile("file", "test", MediaType.TEXT_PLAIN_VALUE, bytes);
        File resultFile = fileService.transformerMultipartToFile(file);
        assertNotNull(resultFile);
        assertTrue(resultFile.getName().startsWith("test"));
        assertTrue(resultFile.getName().endsWith(".csv"));
    }

    @Test
    void transformerMultipartToFile_okWithoutOriginalFilename() {
        byte[] bytes = "date,cases,deaths,uf,time\n2020-01-30,0,0,CE,16:00\n2020-01-30,0,0,MG,16:00".getBytes();
        MultipartFile file = new MockMultipartFile("file", bytes);
        File resultFile = fileService.transformerMultipartToFile(file);
        assertTrue(resultFile.getName().startsWith("input"));
        assertTrue(resultFile.getName().endsWith(".csv"));
    }


    @Test
    void transformerMultipartToFile_ioException() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        doThrow(IOException.class).when(multipartFile).getInputStream();
        assertThrows(FileTransformException.class, () -> fileService.transformerMultipartToFile(multipartFile));
    }

    @Test
    void transformerMultipartToFile_noBytesRead() throws IOException {
        MultipartFile multipartFile = mock(MultipartFile.class);
        InputStream inputStream = mock(InputStream.class);
        when(multipartFile.getInputStream()).thenReturn(inputStream);
        when(inputStream.available()).thenReturn(10);
        when(inputStream.read(any())).thenReturn(-1);

        assertThrows(EmptyFileException.class, () -> fileService.transformerMultipartToFile(multipartFile));
    }
}

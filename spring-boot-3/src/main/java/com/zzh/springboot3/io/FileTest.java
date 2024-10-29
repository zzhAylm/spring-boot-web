package com.zzh.springboot3.io;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Description:
 * @Author: zzh
 * @Crete 2024/10/16 20:45
 */
@Slf4j
@Service
public class FileTest {

    private final Path path = Paths.get("/Users/zzh/Company/files/其他");


    @Test
    public void fileTest() {
        Path resolve = path.resolve("start.sh");
        URI uri = resolve.toUri();
        File file = resolve.toFile();
        log.info("file is : {}",file.getName());
        log.info("file name is : {}", resolve.getFileName());
        log.info("uri is : {}", uri.getPath());
        Resource resource = new PathResource(uri);
        log.info("resource is :{}",resource.getFilename());
    }
}

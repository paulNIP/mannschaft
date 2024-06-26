package com.opolos.mannschaft.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FileService {

    public List<String> listFilesAndFolders(String directoryPath) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(directoryPath), 1)) {
            return paths.filter(path -> !path.equals(Paths.get(directoryPath)))
                        .map(Path::toString)
                        .collect(Collectors.toList());
        }
    }
}
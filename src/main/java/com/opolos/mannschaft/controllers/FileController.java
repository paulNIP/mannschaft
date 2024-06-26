package com.opolos.mannschaft.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opolos.mannschaft.services.FileService;

import java.io.IOException;
import java.util.List;


@RestController
public class FileController {

    
    @Autowired
    private FileService fileService;

    @GetMapping("/list-files-folders")
    public List<String> listFilesAndFolders(@RequestParam String directoryPath) {
        try {
            return fileService.listFilesAndFolders(directoryPath);
        } catch (IOException e) {
            e.printStackTrace();
            return List.of("Error: " + e.getMessage());
        }
    }
    
}

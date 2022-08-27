package edu.school.cinema.services;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileService {
    public static List<File> getFiles(File root) {
        return Arrays.stream(root.listFiles()).collect(Collectors.toList());
    }
}

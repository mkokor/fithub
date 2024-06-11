package com.fithub.services.training.core.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fithub.services.training.api.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Override
    public List<String> getPublicImages() throws Exception {
        Resource resource = new ClassPathResource("public/images");

        if (resource.exists() && resource.isFile()) {
            Path imagesPath = resource.getFile().toPath();

            try (Stream<Path> paths = Files.walk(imagesPath, 1)) {
                return paths.filter(Files::isRegularFile).map(path -> "/images/" + path.getFileName().toString())
                        .collect(Collectors.toList());
            }
        }

        return null;
    }

}
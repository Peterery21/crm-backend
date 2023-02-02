package com.kodzotech.fileupload.service.impl;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.dto.FileUploadDto;
import com.kodzotech.fileupload.dto.MultiFichierDto;
import com.kodzotech.fileupload.mapper.FichierMapper;
import com.kodzotech.fileupload.exception.FileUploadException;
import com.kodzotech.fileupload.service.FichierService;
import com.kodzotech.fileupload.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.Validate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FilesStorageServiceImpl implements FilesStorageService {

    private Path root = null;

    @Value("${upload.folder.path.default}")
    public String uploadDefaultFolder;

    public Path getRoot() {
        return root;
    }

    private final FichierMapper fichierMapper;
    private final FichierService fichierService;

    @Override
    public void init(String uploadFolder, String subFolder) {
        try {
            String folder = uploadFolder != null ? uploadFolder : uploadDefaultFolder;
            if (subFolder != null) {
                folder = folder + File.separator + subFolder;
            }
            root = Paths.get(folder);
            if (!Files.exists(root)) {
                Files.createDirectory(root);
            }
        } catch (IOException e) {
            throw new FileUploadException("Could not initialize folder for upload!" + e.getMessage());
        }
    }

    @Override
    public String save(FileUploadDto fileUploadDto) {
        return fileUploadDto.getSubFolder() + File.separator + uploadFile(fileUploadDto);
    }

    private String uploadFile(FileUploadDto fileUploadDto) {
        String filename;
        init(fileUploadDto.getUploadFolder(), fileUploadDto.getSubFolder());
        if (fileUploadDto.getFile() == null) {
            throw new FileUploadException("erreur.fileupload.fichier.incorrect");
        }
        try {
            String extension = StringUtils.getFilenameExtension(fileUploadDto.getFile().getOriginalFilename());
            String name = FilenameUtils.removeExtension(fileUploadDto.getFile().getOriginalFilename());
            if (fileUploadDto.getFilename() == null) {
                filename = name + "_" + String.valueOf(System.currentTimeMillis()) + "." + extension;
            } else {
                filename = fileUploadDto.getFilename();
            }
            Files.copy(fileUploadDto.getFile().getInputStream(), this.root.resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Long saveDocument(FichierDto fichierDto) {
        FileUploadDto fileUploadDto = fichierMapper.fichierToFileUpload(fichierDto);
        fichierDto.setFilename(uploadFile(fileUploadDto));
        fichierDto.setFileUrl(fileUploadDto.getSubFolder() + File.separator + fichierDto.getFilename());
        return fichierService.save(fichierDto);
    }

    @Override
    @Transactional
    public void saveMultiDocument(MultiFichierDto multiFichierDto) {
        fichierService.deleteByCategorieAndIdObjet(multiFichierDto.getCategorie(),
                multiFichierDto.getIdObjet());
        if (multiFichierDto.getFile() != null) {
            for (MultipartFile file : multiFichierDto.getFile()) {
                String filename = uploadFile(FileUploadDto.builder()
                        .subFolder(multiFichierDto.getSubFolder())
                        .file(file)
                        .build());
                fichierService.save(FichierDto.builder()
                        .idObjet(multiFichierDto.getIdObjet())
                        .subFolder(multiFichierDto.getSubFolder())
                        .filename(filename)
                        .fileUrl(multiFichierDto.getSubFolder() + File.separator + filename)
                        .categorie(multiFichierDto.getCategorie())
                        .build());
            }
        }
    }


    @Override
    public byte[] load(String filename) {
        try {
            init(null, null);
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return Files.readAllBytes(Paths.get(resource.getURI()));
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll(String uploadFolder) {
        Validate.notNull(uploadFolder);
        init(uploadFolder, null);
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll(String uploadFolder) {
        Validate.notNull(uploadFolder);
        init(uploadFolder, null);
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }

}

package com.kodzotech.fileupload.controller;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.dto.FileSavedDto;
import com.kodzotech.fileupload.dto.FileUploadDto;
import com.kodzotech.fileupload.dto.MultiFichierDto;
import com.kodzotech.fileupload.service.FichierService;
import com.kodzotech.fileupload.service.FilesStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FilesStorageService storageService;
    private final FichierService fichierService;

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.OK)
    public String upload(@ModelAttribute FileUploadDto fileUploadDto) {
        return storageService.save(fileUploadDto);
    }

    @PostMapping("/upload_json")
    @ResponseStatus(HttpStatus.OK)
    public FileSavedDto uploadJSON(@ModelAttribute FileUploadDto fileUploadDto) {
        String fileUrl = storageService.save(fileUploadDto);
        return FileSavedDto.builder().filename(fileUrl).build();
    }

    @PostMapping("/fichiers")
    @ResponseStatus(HttpStatus.OK)
    public Long saveDocument(@ModelAttribute FichierDto fichierDto) {
        return storageService.saveDocument(fichierDto);
    }

    @PostMapping("/save_multifichiers")
    @ResponseStatus(HttpStatus.OK)
    public void saveDocuments(@ModelAttribute MultiFichierDto multiFichierDto) {
        storageService.saveMultiDocument(multiFichierDto);
    }

    @GetMapping(value = "/getFichiers")
    public List<FichierDto> getDocuments(@RequestParam String categorie,
                                         @RequestParam Long idObjet) {
        return fichierService.getDocuments(categorie, idObjet);
    }

    @GetMapping(value = "/all_fichiers")
    public List<FichierDto> getDocumentByidObjet(@RequestParam String categorie,
                                                 @RequestParam List<Long> idObjets) {
        return fichierService.getDocumentByidObjet(categorie, idObjets);
    }

    @GetMapping(value = "/content/images/{subFolder}/{filename:.+}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable String subFolder, @PathVariable String filename) {
        return storageService.load(subFolder + File.separator + filename);
    }

    @GetMapping(value = "/content/files/{subFolder}/{filename:.+}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody
    byte[] getFile(@PathVariable String subFolder, @PathVariable String filename) {
        return storageService.load(subFolder + File.separator + filename);
    }
}

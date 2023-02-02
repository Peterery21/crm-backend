package com.kodzotech.fileupload.service;

import com.kodzotech.fileupload.dto.FichierDto;
import com.kodzotech.fileupload.dto.FileUploadDto;
import com.kodzotech.fileupload.dto.MultiFichierDto;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FilesStorageService {

    public void init(String uploadFolder, String subFolder);

    public String save(FileUploadDto fileUploadDto);

    public Long saveDocument(FichierDto fichierDto);

    /**
     * Charger un fichier par son nom
     *
     * @param filename
     * @return
     */
    public byte[] load(String filename);

    /**
     * Supprimer les fichiers d'un répertoire
     *
     * @param uploadFolder
     */
    public void deleteAll(String uploadFolder);

    /**
     * Charger les fichiers d'un répertoire
     *
     * @param uploadFolder
     * @return
     */
    public Stream<Path> loadAll(String uploadFolder);

    /**
     * Enregistrer un lot de fichier
     *
     * @param multiFichierDto
     */
    void saveMultiDocument(MultiFichierDto multiFichierDto);
}

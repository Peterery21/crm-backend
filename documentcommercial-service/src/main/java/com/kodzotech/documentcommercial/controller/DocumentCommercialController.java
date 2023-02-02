package com.kodzotech.documentcommercial.controller;

import com.kodzotech.documentcommercial.dto.*;
import com.kodzotech.documentcommercial.model.CategorieDocument;
import com.kodzotech.documentcommercial.model.EtatDocument;
import com.kodzotech.documentcommercial.model.TypeDocument;
import com.kodzotech.documentcommercial.service.DocumentCommercialService;
import com.kodzotech.documentcommercial.utils.TresosoftConstant;
import com.kodzotech.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentCommercialController {

    private final DocumentCommercialService documentCommercialService;

    @PostMapping("/{categorie}")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@PathVariable String categorie,
                     @RequestBody DocumentCommercialDto documentCommercialDto,
                     HttpServletRequest request) {

        JwtProvider jwtProvider = new JwtProvider();
        documentCommercialDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        documentCommercialDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));
        documentCommercialDto.setUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));

        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        documentCommercialDto.setCategorie(categorieDocument.toString());
        return documentCommercialService.save(documentCommercialDto);
    }

    @GetMapping("/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialResponse> getAllDocumentCommercials(
            @PathVariable Integer page,
            @PathVariable Integer size,
            @RequestParam("categorie") CategorieDocument categorie,
            @RequestParam("type") TypeDocument type,
            @RequestParam("etat") EtatDocument etat) {
        return documentCommercialService.getAllDocumentCommercials(categorie, type, etat, page, size);
    }

    /**
     * Renvoi la liste paginée des documents par categorie et type
     *
     * @param categorie
     * @param page
     * @param size
     * @param type
     * @return
     */
    @GetMapping("/{categorie}/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialResponse> getAllDocumentCommercials(@PathVariable String categorie,
                                                                      @PathVariable Integer page,
                                                                      @PathVariable Integer size,
                                                                      @RequestParam String type) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getAllDocumentCommercials(categorieDocument,
                Enum.valueOf(TypeDocument.class, type), page, size);
    }

    /**
     * Renvoi la liste paginée des documents par categorie, type et etat
     *
     * @param categorie
     * @param page
     * @param size
     * @param type
     * @param etat
     * @return
     */
    @GetMapping("/{categorie}/{type}/{page}/{size}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialResponse> getAllDocumentCommercialsByEtat(@PathVariable String categorie,
                                                                            @PathVariable Integer page,
                                                                            @PathVariable Integer size,
                                                                            @PathVariable String type,
                                                                            @RequestParam String etat) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getAllDocumentCommercials(categorieDocument,
                Enum.valueOf(TypeDocument.class, type),
                Enum.valueOf(EtatDocument.class, etat),
                page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentCommercialDto getDocument(@PathVariable Long id) {
        return documentCommercialService.getDocumentCommercial(id);
    }

    @GetMapping("/info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentCommercialResponse getDocumentFullInfo(@PathVariable Long id) {
        return documentCommercialService.getDocumentCommercialInfo(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Long update(@PathVariable Long id,
                       @RequestBody DocumentCommercialDto documentCommercialDto,
                       HttpServletRequest request) {

        JwtProvider jwtProvider = new JwtProvider();
        documentCommercialDto.setSocieteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID));
        documentCommercialDto.setEntiteId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.ENTITE_ID));
        documentCommercialDto.setUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));
        documentCommercialDto.setUpdateUtilisateurId(jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID));
        documentCommercialDto.setId(id);
        return documentCommercialService.save(documentCommercialDto);
    }

    @PostMapping("/etat/update")
    @ResponseStatus(HttpStatus.OK)
    public void modifierEtat(@RequestBody EtatDto etatDto,
                             HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long utilisateurId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID);

        EtatDocument etatDocument = Enum.valueOf(EtatDocument.class, etatDto.getEtat());
        documentCommercialService.modiferEtatDocument(etatDto.getId(), etatDocument, utilisateurId);
    }

    @PostMapping("/convertir")
    @ResponseStatus(HttpStatus.CREATED)
    public Long convertirDocument(@RequestBody TypeDto typeDto,
                                  HttpServletRequest request) {
        JwtProvider jwtProvider = new JwtProvider();
        Long utilisateurId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.UTILISATEUR_ID);
        return documentCommercialService.convertirDocument(typeDto.getId(), typeDto.getType(), utilisateurId);
    }

    @GetMapping("/{categorie}/etats/count")
    @ResponseStatus(HttpStatus.OK)
    public List<EtatCountDto> getEtatCountList(@PathVariable String categorie,
                                               @RequestParam("type") String type) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getEtatCountList(categorieDocument, type);
    }

    @GetMapping("/client/{categorie}/type/count")
    @ResponseStatus(HttpStatus.OK)
    public List<EtatCountDto> getEtatCountList(@PathVariable String categorie,
                                               @RequestParam Long compteId) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getClientTypeCountList(categorieDocument, compteId);
    }

    @GetMapping("/{categorie}/{type}/total")
    @ResponseStatus(HttpStatus.OK)
    public Long getNbreDocument(@PathVariable String categorie, @PathVariable String type,
                                @RequestParam(value = "etat", required = false) String etat) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getNbreDocument(categorieDocument, type, etat);
    }

    @GetMapping("/{categorie}/etats")
    @ResponseStatus(HttpStatus.OK)
    public List<EtatDocument> getEtatsDocument(@PathVariable String categorie) {
        return documentCommercialService.getEtatsActionDocument(categorie);
    }

    @GetMapping("/{categorie}/types")
    @ResponseStatus(HttpStatus.OK)
    public List<TypeDocument> getTypeDocumentAction(@PathVariable String categorie, @RequestParam String type) {
        TypeDocument typeDocument = Enum.valueOf(TypeDocument.class, type);
        CategorieDocument categorieDocument = Enum.valueOf(CategorieDocument.class, categorie);
        return documentCommercialService.getTypeDocumentAction(categorieDocument, typeDocument);
    }

    @GetMapping("/{categorie}/all_types")
    @ResponseStatus(HttpStatus.OK)
    public List<TypeDocument> getAllTypeDocument(@PathVariable String categorie) {
        CategorieDocument categorieDocument = Enum.valueOf(CategorieDocument.class, categorie);
        return documentCommercialService.getTypeDocumentAction(categorieDocument, null);
    }

    @GetMapping("/associe/{documentId}/{documentInitialId}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialResponse> getDocumentAssocie(@PathVariable Long documentId,
                                                               @PathVariable Long documentInitialId) {
        return documentCommercialService.getDocumentLie(documentInitialId, documentId);
    }


    @GetMapping("/byId")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialDto> getDocumentsById(@RequestParam List<Long> ids) {
        return documentCommercialService.getDocumentsById(ids);
    }

    /**
     * Renvoi la liste des documents par categorie, type et par client
     *
     * @param categorie
     * @param compteId
     * @param type
     * @return
     */
    @GetMapping("/client/{categorie}/{compteId}")
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentCommercialResponse> getAllDocumentCommercials(@PathVariable Long compteId,
                                                                      @PathVariable String categorie,
                                                                      @RequestParam String type) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getAllClientDocumentCommercials(categorieDocument,
                Enum.valueOf(TypeDocument.class, type), compteId);
    }

    @GetMapping("/client/{categorie}/{type}/{compteId}/total")
    @ResponseStatus(HttpStatus.OK)
    public Long getClientNbreDocument(@PathVariable String categorie, @PathVariable String type,
                                      @PathVariable Long compteId) {
        CategorieDocument categorieDocument = documentCommercialService
                .getCategorieDocumentFromDto(categorie);
        return documentCommercialService.getClientNbreDocument(categorieDocument, type, compteId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void supprimerDocument(@PathVariable Long id) {
        documentCommercialService.delete(id);
    }
}

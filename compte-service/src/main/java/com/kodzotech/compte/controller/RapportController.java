package com.kodzotech.compte.controller;

import com.kodzotech.compte.service.CompteRapportService;
import com.kodzotech.compte.service.ContactRapportService;
import com.kodzotech.compte.utils.TresosoftConstant;
import com.kodzotech.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rapport")
@RequiredArgsConstructor
public class RapportController {

    private final CompteRapportService compteRapportService;
    private final ContactRapportService contactRapportService;

    @GetMapping("/compte/liste")
    public ResponseEntity getCompteList(HttpServletRequest request) {
        String lang = request.getHeader("lang");
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        try {
            byte[] rapport = compteRapportService.imprimerCompteList(lang, societeId);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "document.pdf");
            return new ResponseEntity<byte[]>
                    (rapport, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contact/liste")
    public ResponseEntity getContactList(HttpServletRequest request) {
        String lang = request.getHeader("lang");
        JwtProvider jwtProvider = new JwtProvider();
        Long societeId = jwtProvider.getLongDataFromJwt(request, TresosoftConstant.SOCIETE_ID);
        try {
            byte[] rapport = contactRapportService.imprimerList(lang, societeId);
            HttpHeaders headers = new HttpHeaders();
            //set the PDF format
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "document.pdf");
            return new ResponseEntity<byte[]>
                    (rapport, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

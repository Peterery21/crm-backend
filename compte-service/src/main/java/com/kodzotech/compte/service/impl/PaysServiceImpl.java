package com.kodzotech.compte.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kodzotech.compte.dto.PaysDto;
import com.kodzotech.compte.exception.PaysException;
import com.kodzotech.compte.service.PaysService;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PaysServiceImpl implements PaysService {

    @Override
    public List<PaysDto> getPaysFrList() {
        String countriesJson = resourceToFile("countries_FR.json");
        Map<String, String> countryMap = new Gson().fromJson(countriesJson, Map.class);
        List<PaysDto> paysDtos = countryMap.keySet().stream()
                .map(s -> new PaysDto(s, countryMap.get(s)))
                .collect(Collectors.toList());
        return paysDtos;
    }

    @Override
    public List<PaysDto> getPaysEnList() {
        String countriesJson = resourceToFile("countries_EN.json");
        Type listUnitType = new TypeToken<ArrayList<PaysDto>>() {
        }.getType();
        List<PaysDto> paysDtos = new Gson().fromJson(countriesJson, listUnitType);
        return paysDtos;
    }

    @Override
    public PaysDto getPays(String code) {
        String countriesJson = resourceToFile("countries_FR.json");
        Map<String, String> countryMap = new Gson().fromJson(countriesJson, Map.class);
        if (countryMap.get(code) == null) {
            throw new PaysException("erreur.pays.code.incorrect");
        }
        return new PaysDto(code, countryMap.get(code));
    }

    String resourceToFile(String resource) {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(resource);
            return IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

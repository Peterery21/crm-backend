package com.kodzotech.compte.utils;

import com.kodzotech.compte.dto.PaysDto;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        //getPaysFRList();
        getPaysENList();
    }

    public static void getPaysFRList() {
        String countriesJson = resourceToFile("countries_FR.json");
        Map<String, String> countryMap = new Gson().fromJson(countriesJson, Map.class);
        List<PaysDto> paysDtos = countryMap.keySet().stream()
                .map(s -> new PaysDto(s, countryMap.get(s)))
                .collect(Collectors.toList());
    }

    public static void getPaysENList() {
        String countriesJson = resourceToFile("countries_EN.json");
        Type listUnitType = new TypeToken<ArrayList<PaysDto>>() {
        }.getType();
        List<PaysDto> paysDtos = new Gson().fromJson(countriesJson, listUnitType);
        paysDtos.forEach(paysDto -> System.out.println(paysDto.toString()));
    }

    static String resourceToFile(String resource) {
        try {
            return FileUtils.readFileToString(new ClassPathResource(resource).getFile(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

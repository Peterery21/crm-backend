package com.kodzotech.transaction.utils;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        Currency currency = Currency.getInstance("EUR");
//        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
//        numberFormat.setCurrency(currency);
//        String myCurrency = numberFormat.format(123.5);
//        System.out.println(myCurrency);

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");

        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);
        System.out.println(nf.format(12345.124).trim());
    }

    void countryLoad() {
        //        String isoCodeJson = resourceToFile("countries.json");
//        Type listType = new TypeToken<ArrayList<IsoCode>>() {
//        }.getType();
//        List<IsoCode> isoCodeList = new Gson().fromJson(isoCodeJson, listType);

        String unitCodeJson = resourceToFile("codes-all_json.json");
        Type listUnitType = new TypeToken<ArrayList<CodeJson>>() {
        }.getType();
        List<CodeJson> unitCodeList = new Gson().fromJson(unitCodeJson, listUnitType);

        List<Countries> countriesList = new ArrayList<>();

        unitCodeList.forEach(unitCode -> {
                    int minorUnit = 0;
                    try {
                        if (unitCode != null) {
                            minorUnit = Integer.parseInt(unitCode.getMinorUnit());
                        }
                    } catch (Exception e) {
                    }
                    countriesList.add(Countries.builder()
                            .code(unitCode.getAlphabeticCode())
                            .libelle(unitCode.getCurrency())
                            .symbole(unitCode.getAlphabeticCode())
                            .unite(minorUnit)
                            .pays(unitCode.getEntity())
                            .build());
                }
        );

//        isoCodeList.forEach((isoCode -> {
//            CodeJson unitCode = unitCodeList.stream().filter(unit -> unit.getNumericCode() == isoCode.getIsoNumeric())
//                    .findFirst().orElse(null);
//            int minorUnit = 0;
//            try {
//                if (unitCode != null) {
//                    minorUnit = Integer.parseInt(unitCode.getMinorUnit());
//                }
//            } catch (Exception e) {
//            }
//            countriesList.add(Countries.builder()
//                    .code(isoCode.getCurrency().getCode())
//                    .libelle(isoCode.getCurrency().getName())
//                    .symbole(isoCode.getCurrency().getSymbol())
//                    .unite(minorUnit)
//                    .pays(isoCode.getName())
//                    .build()
//            );
//        }));
        countriesList.forEach(countries -> System.out.println(countries.toString()));
        String finalJson = new Gson().toJson(countriesList);

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("currency.json"));
            writer.write(finalJson);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

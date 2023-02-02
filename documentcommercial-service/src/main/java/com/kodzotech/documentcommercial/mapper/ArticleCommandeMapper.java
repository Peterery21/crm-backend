package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.TaxeCommandeDto;
import com.kodzotech.documentcommercial.client.TransactionClient;
import com.kodzotech.documentcommercial.dto.ArticleCommandeResponse;
import com.kodzotech.documentcommercial.model.ArticleCommande;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class ArticleCommandeMapper {

    @Autowired
    private TransactionClient transactionClient;

    @Mapping(target = "taxe", expression = "java(getTaxe(taxes, articleCommande.getTaxeId()))")
    public abstract ArticleCommandeResponse articleEntityToDto(ArticleCommande articleCommande,
                                                               List<TaxeCommandeDto> taxes);

    public List<ArticleCommandeResponse> articleEntitiesToDto(List<ArticleCommande> articleCommandes) {
        List<Long> taxeIdList = articleCommandes.stream().filter(t -> t.getTaxeId() != null).map(t -> t.getTaxeId()).distinct().collect(Collectors.toList());
        List<TaxeCommandeDto> taxeCommandeDtos = taxeIdList.size() > 0 ? transactionClient.getTaxesById(taxeIdList) : new ArrayList<>();
        return articleCommandes.stream()
                .map(articleCommande -> articleEntityToDto(articleCommande, taxeCommandeDtos))
                .collect(Collectors.toList());
    }

    TaxeCommandeDto getTaxe(List<TaxeCommandeDto> list, Long id) {
        return list.stream()
                .filter(object -> object.getId() != null &&
                        object.getId().equals(id))
                .findFirst().orElse(null);
    }
}

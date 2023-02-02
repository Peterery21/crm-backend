package com.kodzotech.entite.service;

import com.kodzotech.entite.dto.EntiteChart;
import com.kodzotech.entite.dto.EntiteResponse;

import java.util.List;

public interface EntiteChartService {

    /**
     * Renvoie la liste des entités d'une societe par niveau
     *
     * @param societeId
     * @return
     */
    List<EntiteResponse> getAllEntitesBySociete(Long societeId);

    /**
     * Récupérer les enfants d'une entité de façon récursive
     *
     * @param entiteResponse
     * @param entiteResponses
     * @return
     */
    List<EntiteResponse> getEntiteChild(EntiteResponse entiteResponse, List<EntiteResponse> entiteResponses);

    /**
     * renvoie l'organigramme des entités
     *
     * @param societeId
     * @return
     */
    List<EntiteChart> getEntiteChart(Long societeId);

    /**
     * Vérifie si une entité à des fils
     *
     * @param entiteId
     * @return
     */
    Boolean hasChild(Long entiteId);

    /**
     * Récupérer la liste des id des entites fils + le parent
     *
     * @param entiteId
     * @return
     */
    List<Long> getChildsId(Long entiteId);

    /**
     * Récupérer les enfants chart d'une entité de façon récursive
     *
     * @param entiteChart
     * @param entiteResponses
     * @return
     */
    EntiteChart getChartChild(EntiteChart entiteChart, List<EntiteResponse> entiteResponses);

    /**
     * Récupérer les enfants d'un parent
     *
     * @param parentId
     * @param entiteResponses
     * @return
     */
    List<EntiteResponse> getParentChild(Long parentId, List<EntiteResponse> entiteResponses);
}

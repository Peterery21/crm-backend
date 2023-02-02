package com.kodzotech.documentcommercial.service.impl;

import com.kodzotech.documentcommercial.dto.ArticleDto;
import com.kodzotech.documentcommercial.dto.ArticleResponse;
import com.kodzotech.documentcommercial.dto.FileUploadDto;
import com.kodzotech.documentcommercial.exception.ArticleException;
import com.kodzotech.documentcommercial.exception.CategorieArticleException;
import com.kodzotech.documentcommercial.repository.ArticleRepository;
import com.kodzotech.documentcommercial.client.FileUploadClient;
import com.kodzotech.documentcommercial.mapper.ArticleMapper;
import com.kodzotech.documentcommercial.model.Article;
import com.kodzotech.documentcommercial.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {


    private final String ARTICLE_FOLDER = "article";

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final FileUploadClient fileUploadClient;

    @Override
    @Transactional
    public void save(ArticleDto articleDto) {
        Article article = articleMapper.dtoToEntity(articleDto);
        validerArticle(article);

        //Store files
        if (articleDto.getImagePrincipaleFile() != null) {
            try {
                articleDto.setImagePrincipale(fileUploadClient
                        .saveFile(FileUploadDto
                                .builder()
                                .subFolder(ARTICLE_FOLDER)
                                .file(articleDto.getImagePrincipaleFile())
                                .build()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (articleDto.getImages() != null && articleDto.getImagesToRemove() != null) {
            articleDto.getImages().removeAll(articleDto.getImagesToRemove());
        }
        if (articleDto.getImagesFile() != null) {
            if (articleDto.getImages() == null) {
                articleDto.setImages(new ArrayList<>());
            }
            for (MultipartFile file : articleDto.getImagesFile()) {
                articleDto.getImages().add(fileUploadClient
                        .saveFile(FileUploadDto
                                .builder()
                                .subFolder(ARTICLE_FOLDER)
                                .file(file)
                                .build()));
            }
        }
        article.setImagePrincipale(articleDto.getImagePrincipale());
        article.setImages(articleDto.getImages());
        if (article.getId() != null) {
            Article articleOriginal = articleRepository
                    .findById(article.getId()).get();
            articleOriginal = articleMapper.dtoToEntity(articleOriginal, article);
            //Save article
            articleRepository.save(articleOriginal);
        } else {
            //Save article
            articleRepository.save(article);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void validerArticle(Article article) {
        if (article.getCategorieArticle() == null) {
            throw new ArticleException("erreur.article.categorie.non.valide");
        }
        if (article.getDesignation() == null || article.getDesignation().isEmpty()) {
            throw new ArticleException("erreur.article.designation.non.valide");
        }
        if (article.getType() == null) {
            throw new ArticleException("erreur.article.type.non.valide");
        }
        if (article.getId() != null) {
            // Mode modification
            //Rechercher la catégorie de la base
            Article articleOriginal = articleRepository
                    .findById(article.getId())
                    .orElseThrow(() ->
                            new ArticleException("erreur.article.id.non.trouve"));

            //Vérifier si libellé en double
            Article articleTemp = articleRepository
                    .findByDesignation(article.getDesignation())
                    .orElse(null);
            if (articleTemp != null) {
                if (articleTemp.getId() != articleOriginal.getId()) {
                    throw new ArticleException("erreur.article.designation.doublon");
                }
            }
        } else {
            // Mode ajout - vérification libellé
            Article articleTemp = articleRepository
                    .findByDesignation(article.getDesignation())
                    .orElse(null);
            if (articleTemp != null) {
                throw new ArticleException("erreur.article.designation.doublon");
            }
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleDto getArticle(Long id) {
        return articleMapper.entityToDto(articleRepository
                .findById(id)
                .orElseThrow(() -> new ArticleException("erreur.article.id.non.trouve")));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleDto> getAllArticle() {
        List<Article> articleList = articleRepository.findAll();
        return articleList.stream().map(articleMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(
                        () -> new CategorieArticleException("erreur.article.id.non.trouve"));
        // Controle à faire pour plus tard si article associé à un autre objet
        articleRepository.delete(article);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedCategorie(Long id) {
        return articleRepository.existsByCategorieArticleId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedMarque(Long id) {
        return articleRepository.existsByMarqueId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkUsedUnite(Long id) {
        return articleRepository.existsByUniteId(id);
    }

    @Override
    public List<ArticleResponse> getAllArticle(Integer page, Integer size) {
        Pageable pageable =
                PageRequest.of(page, size, Sort.by("designation").ascending());
        List<Article> articleList = articleRepository.findAll(pageable).toList();
        return articleMapper.entityToResponse(articleList);
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNbreArticle() {
        return articleRepository.count();
    }

    @Override
    public List<ArticleDto> getAllAchatArticle(Long societeId) {
        List<Article> articleList = articleRepository.findAllBySocieteIdAndDisponibleAchatTrue(societeId);
        return articleList.stream().map(articleMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getAllVenteArticle(Long societeId) {
        List<Article> articleList = articleRepository.findAllBySocieteIdAndDisponibleVenteTrue(societeId);
        return articleList.stream().map(articleMapper::entityToDto)
                .collect(Collectors.toList());
    }
}

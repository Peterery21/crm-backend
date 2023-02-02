package com.kodzotech.documentcommercial.mapper;

import com.kodzotech.documentcommercial.dto.DocumentCommercialDto;
import com.kodzotech.documentcommercial.model.DocumentCommercial;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentCommercialMapperTest {

    @Test
    void map() {
        //given
        DocumentCommercialDto documentCommercialDto = DocumentCommercialDto
                .builder()
                .objet("test")
                .build();

        //when
        DocumentCommercial documentCommercial = DocumentCommercialMapper.INSTANCE.dtoToEntity(documentCommercialDto);

        //then
        assertThat(documentCommercialDto).isNotNull();
        assertThat(documentCommercialDto.getObjet()).isEqualTo("test");
    }
}
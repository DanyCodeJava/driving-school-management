package danycode.dsm.mapper;

import danycode.dsm.dto.PageDto;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
@UtilityClass
public class PageMapper {
    public static <T> PageDto<T> mapToPageDto(Page<T> page){
       return PageDto.<T>builder()
                .content(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();

    }
}

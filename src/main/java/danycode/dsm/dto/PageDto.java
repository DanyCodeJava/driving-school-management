package danycode.dsm.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class PageDto<T> {

    List<T> content;
    long totalElements;
    int totalPages;
}

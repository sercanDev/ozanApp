package com.example.ozanapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestDTO {
    private int page;
    private int size;

    public static PageRequest buildRequest(PageRequestDTO request) {
        PageRequest pageRequest = PageRequest.of(0, 25);
        if (request != null) {
            pageRequest = PageRequest.of(request.getPage(), request.getSize());
        }

        return pageRequest;
    }

    public PageRequest buildAsPageRequest() {
        return buildRequest(this);
    }
}

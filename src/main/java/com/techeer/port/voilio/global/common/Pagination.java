package com.techeer.port.voilio.global.common;

import java.util.Arrays;
import java.util.List;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

public class Pagination<T extends RepresentationModel> extends PagedModel<T> {
  private final int page;
  private final int size;
  private final long totalElements;
  private final int totalPages;

  public Pagination(
      List<T> content, int page, int size, long totalElements, int totalPages, Link links) {
    super(content, new PageMetadata(size, page, totalElements, totalPages), Arrays.asList(links));
    this.page = page;
    this.size = size;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
  }
}

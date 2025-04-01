package daj.common.types;

import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class PageAndFilterRequestDto {

  final public PageRequest page;
  final public String overAllQuery;

  public PageAndFilterRequestDto(Map<String, String[]> paramsReq) {
    try {
      final Map<String, String> allRequestParams = this._reduceParams(paramsReq);
      int page = Integer.parseInt(allRequestParams.get("page"));
      int itemsPerPage = Integer.parseInt(allRequestParams.get("itemsPerPage"));
      String sortBy = allRequestParams.get("sortBy");
      String sortDirection = allRequestParams.get("sortDirection");
      this.page = this._buildPageRequest(page, itemsPerPage, sortBy, sortDirection);
      this.overAllQuery = allRequestParams.get("overAll");
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid request parameters");
    }
  }

  public PageAndFilterRequestDto(
    int page,
    int itemsPerPage,
    String sortBy,
    String sortDirection,
    String overAllQuery 
  ) {
    try {
      this.page = this._buildPageRequest(page, itemsPerPage, sortBy, sortDirection);
      this.overAllQuery = overAllQuery;
    } catch (Exception e) {
      throw new IllegalArgumentException("Invalid request parameters");
    }
  }

  private Map<String, String> _reduceParams(Map<String, String[]> paramsReq) {
    return paramsReq.entrySet().stream().collect(
      java.util.stream.Collectors.toMap(
        (entry) -> { return entry.getKey(); },
        (entry) -> { return entry.getValue().length > 0 ? entry.getValue()[0] : ""; }
      )
    );
  }

  private PageRequest _buildPageRequest(int page, int itemsPerPage, String sortBy, String sortDirection) {
    final Direction direction = Sort.Direction.fromString(sortDirection);
    final Sort order = Sort.by(direction, sortBy);
    final PageRequest pageRequest = PageRequest.of(page, itemsPerPage, order);
    return pageRequest;
  }

  private Map<String, String> _getRestValues(Map<String, String> filters) {
    filters.remove("page");
    filters.remove("itemsPerPage");
    filters.remove("sortBy");
    filters.remove("sortDirection");
    filters.forEach((key, value) -> {
      if (value == null || value.isBlank() || value.length() >= 35 || this._hasSpecialChars(value)) {
        filters.remove(key);
      }
    });
    return filters;
  }

  private boolean _hasSpecialChars(String value) {
    return !value.matches("[a-zA-Z0-9]+");
  }

  @Override
  public String toString() {
    return "PageAndFilterRequest [itemsPerPage="
      + this.page.getPageSize()
      + ", page=" + this.page.getPageNumber()
      + ", sort=" + this.page.getSort().toString()
      + "]";
  }
  
}

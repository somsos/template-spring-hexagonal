package daj.common.types;

import java.util.List;

public class AppPage<E> {
  private final List<E> content;
  private final int totalElements;

  // page number
  private final int pageNumber;

  private final int itemsPerPage;

  public AppPage(List<E> content, int totalElements, int pageNumber, int itemsPerPage) {
    this.content = content;
    this.totalElements = totalElements;
    this.pageNumber = pageNumber;
    this.itemsPerPage = itemsPerPage;
  }

  public int getTotalPages() {
    // CAREFUL: to get precise result divide just between doubles
    double itemsPerPageD = itemsPerPage;
    double totalElementsD = (double)totalElements;
    
    double div = totalElementsD / itemsPerPageD;
    int total = (int) Math.ceil(div);
    return total;
  }

  public int getCurrentSize() {
    return content.size();
  }

  public List<E> getContent() {
    return content;
  }

  public int getTotalElements() {
    return totalElements;
  }

  public int getPageNumber() {
    return totalElements;
  }

  public String toString() {
    final String st = "Page: { totalElements: %s, pageNumber: %s, TotalPages: %s, currentSize: %s}";
    return String.format(st, totalElements, pageNumber, getTotalPages(), getCurrentSize());
  }


}

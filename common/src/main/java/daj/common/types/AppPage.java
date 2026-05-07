package daj.common.types;

import java.util.List;

import lombok.Getter;

@Getter
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

  public String toString() {
    final String st = "Page: { totalElements: %s, pageNumber: %s, TotalPages: %s, itemsPerPage: %s, currentSize: %s}";
    return String.format(st, totalElements, pageNumber, getTotalPages(), getItemsPerPage(), getCurrentSize());
  }


}

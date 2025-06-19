package daj.adapter.product.inWeb.reqAndResp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductDetailsResponse {

  private Integer id;

  private String name;

  private Float price;
  
  private Integer amount;

  private List<Integer> images;

}

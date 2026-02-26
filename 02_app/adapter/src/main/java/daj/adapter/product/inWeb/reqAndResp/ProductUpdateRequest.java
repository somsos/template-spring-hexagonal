package daj.adapter.product.inWeb.reqAndResp;

import static daj.product.visible.config.IProductConstants.DATA_AMOUNT_RANGE_MAX;
import static daj.product.visible.config.IProductConstants.DATA_AMOUNT_RANGE_MIN;
import static daj.product.visible.config.IProductConstants.DATA_DESCRIPTION_LENGTH_MAX;
import static daj.product.visible.config.IProductConstants.DATA_DESCRIPTION_LENGTH_MIN;
import static daj.product.visible.config.IProductConstants.DATA_NAME_LENGTH_MAX;
import static daj.product.visible.config.IProductConstants.DATA_NAME_LENGTH_MIN;
import static daj.product.visible.config.IProductConstants.DATA_PRICE_MAX;
import static daj.product.visible.config.IProductConstants.DATA_PRICE_MIN;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductUpdateRequest {
  
  @Nullable
  @Length(min = DATA_NAME_LENGTH_MIN, max = DATA_NAME_LENGTH_MAX)
  final private String name;

  @Nullable
  @Range(min = DATA_PRICE_MIN, max = DATA_PRICE_MAX)
  final private Float price;

  @Nullable
  @Range(min = DATA_AMOUNT_RANGE_MIN, max = DATA_AMOUNT_RANGE_MAX)
  final private Integer amount;

  @Nullable
  @Length(min = DATA_DESCRIPTION_LENGTH_MIN, max = DATA_DESCRIPTION_LENGTH_MAX)
  private String description;

}

package daj.product.visible.config;

public interface IProductConstants {
  
  int DATA_AMOUNT_RANGE_MIN = 1;
  int DATA_AMOUNT_RANGE_MAX = 10000;

  int DATA_DESCRIPTION_LENGTH_MIN = 4;
  int DATA_DESCRIPTION_LENGTH_MAX = 64;

  int DATA_PRICE_MIN = 10;
  int DATA_PRICE_MAX = 100000;

  int DATA_NAME_LENGTH_MIN = 4;
  int DATA_NAME_LENGTH_MAX = 64;


  

  String POINT_PRODUCTS = "/products";
  String POINT_PRODUCTS_ID = POINT_PRODUCTS + "/{id}";
  String POINT_PRODUCTS_BY_PAGE = POINT_PRODUCTS + "/page";
  String POINT_PRODUCTS_IMAGE = POINT_PRODUCTS_ID + "/image";
  String POINT_PRODUCTS_IMAGE_ID = POINT_PRODUCTS + "/image/{id}";

  


  String ERROR_IMAGE_NOT_FOUND = "Image not found";
  String NOT_FOUND = "Product not found";
  String ERROR_OWNER_NOT_FOUND = "Owner product not found";

}

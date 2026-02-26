package daj.adapter.product.outDB.entity;

import java.util.List;
import java.util.Date;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class ProductImageEntityTest {

  @Test
  void testOrderImagesNewerFirst() {
    final List<ProductImageEntity> images = this.generateImages(5);
    ProductImageEntity.orderImagesNewerFirst(images);
    assertEquals(Integer.valueOf(4), images.get(0).getId());
  }

  @Test
  void testOrderProductsImages() {

  }


  private List<ProductImageEntity> generateImages(int size) {
    final var myImages = new ArrayList<ProductImageEntity>();
    for (int i = 0; i < size; i++) {
      final var img = new ProductImageEntity();
      img.setId(i);
      final Date myDate = this.addHoursToJavaUtilDate(new Date(), i);
      //img.setCreatedAt(new java.sql.Date(myDate.getTime()));
      myImages.add(img);
    }
    return myImages;
  }

  public Date addHoursToJavaUtilDate(Date date, int hours) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.HOUR_OF_DAY, hours);
    return calendar.getTime();
}

}

package daj.adapter.product.outDB.entity;

import java.util.List;
import java.util.Collections;
import java.util.Comparator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_images")
@SQLDelete(sql = "UPDATE product_images SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductImageEntity {

  @Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(length = 64)
  private String name;

  @Column(length = 32)
  private String type;

  @Column(unique = false, nullable = false, length = 100000)
  private byte[] image;

  @ManyToOne
  @JoinColumn(name = "id_product", nullable = false)
  private ProductEntity product;

  @Column(
    name="created_at",
    nullable = false,
    updatable = false
  )
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private java.sql.Timestamp createdAt;

  @Column(name="deleted_at", nullable = true, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private java.sql.Timestamp deletedAt;

  public static void orderImagesNewerFirst(List<ProductImageEntity> images) {
      Collections.sort(images, new Comparator<ProductImageEntity>() {
        @Override
        public int compare(ProductImageEntity o1, ProductImageEntity o2) {
          return o2.getCreatedAt().compareTo(o1.getCreatedAt());
        }
      });
  }

  public static void orderProductsImages(List<ProductEntity> products) {
    products.forEach(p -> orderImagesNewerFirst(p.getImages()));
  }
  
}

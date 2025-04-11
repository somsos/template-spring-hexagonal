package daj.adapter.user.outDB.entity;

import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.Lob; Do not use
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table( name = "users_picture" )
@Entity
@SQLDelete(sql = "UPDATE users_picture SET deleted_at = now() WHERE id=?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPictureEntity implements Serializable {

  @Id()
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name="id_picture")
  private Integer idPicture;

  //@PrimaryKeyJoinColumn(name="id_user", referencedColumnName="id")
  @JoinColumn(name="id_user", referencedColumnName = "id", nullable = false)
  private Integer idUser;

  @Column(length = 32, nullable = false)
  private String type;

  //@Lob // this creates a reference to the file in the table to the file, but it's not recomendable, because when one wants to work with backups the process of the backup is more complex
  @Column(nullable = false)
  private byte[] file;

  @Column(name="created_at", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @CreationTimestamp
  private Date createdAt;

  @Column(name="deleted_at", nullable = true)
  @Temporal(TemporalType.TIMESTAMP)
  private Date deletedAt;
  
}

package ru.netology.mycloudstorage.modele;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "files")
public class FileUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "filename", nullable = false, length = 200)
    private String filename;

    @Column(name = "size", nullable = false)
    private int size;
    //       @Column(name = "content")
    //       private byte[] content;

    @Column(name = "deleted")
    @JsonIgnore
    private int deleted;

    //      @ManyToOne
    //      @JoinTable(name = "cloudStorage.users",
    //              joinColumns = {@JoinColumn(name = "file_id", referencedColumnName = "file_id")})
    //      private User owner;

    @Column(name = "path")
    @JsonIgnore
    private String path;

}

package com.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true)
    private String title;

    private String subject;
    private String publisher;
    private String language;
    private String thumbnail;
    private String detail;
    private String author;
    private int amount;
    private int price;
    private int borrowPrice;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private Date createdAt;
    private Date updatedAt;
    private Date publishedAt;

    @ManyToOne(
            //cascade = CascadeType.
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "categoryId"
    )
    private Category category;

    public enum BookStatus{
        OUT_OF_STOCK,
        AVAILABLE,
        UNAVAILABLE,
        UPDATING,
        HOT
    }

}

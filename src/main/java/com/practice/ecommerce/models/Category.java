package com.practice.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            /*
            * AUTO: Delegate ID generation to JPA, chooses the primary ID generation strategy as per the underlying DB used
            * IDENTITY : Supported by MySQL, SQLServer, POSTGres. Uses an identity column to generate ID values. Not supported by all DBs
            * SEQUENCE: Uses DB sequence to generate IDs. Supported by Oracle, PostGres
            * TABLE: Uses table to maintain sequence. Can be used in turn of SEQUENCE in case DB doesnt support it.
            * */
    int categoryID;

    @NotBlank
    @Size(min = 5,message = "Name should contain atleast 5 characters")
    @Column(name = "name")
    String categoryName;

}

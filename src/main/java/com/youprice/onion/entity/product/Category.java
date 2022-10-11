package com.youprice.onion.entity.product;


import com.youprice.onion.dto.product.CategoryAddDTO;
import com.youprice.onion.dto.product.CategoryUpdateDTO;
import lombok.Getter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Category {

    @Id  @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private  Long id; //카테고리번호 PK

    private String categoryName; //카테고리이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id") //부모카테고리번호
    private Category category;

    @OneToMany(mappedBy = "category") //자식카테고리번호
    private  List<Category> categoryList = new ArrayList<>();

    @OneToMany(mappedBy = "category")//상품카테고리번호
    private List<ProductCategory> productCategoryList = new ArrayList<>();



    public  Category TopcategoryAdd(CategoryAddDTO categoryAddDTO, String topcategoryName) {
        this.categoryName =topcategoryName; //상위카테고리이름

        return this;

    }

    public  Category SubcategoryAdd(CategoryAddDTO categoryAddDTO) {
          this.categoryName = categoryAddDTO.getCategoryName(); //하위카테고리이름
          this.category =categoryAddDTO.getCategory();

        return this;

    }

    public  Category categoryUpdate(CategoryUpdateDTO categoryUpdatedto) {
        this.id = categoryUpdatedto.getId();
        this.categoryName =categoryUpdatedto.getCategoryName();
        this.category = categoryUpdatedto.getCategory();

        return this;

    }





}

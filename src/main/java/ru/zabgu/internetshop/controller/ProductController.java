package ru.zabgu.internetshop.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.zabgu.internetshop.domain.Product;
import ru.zabgu.internetshop.domain.Views;
import ru.zabgu.internetshop.repo.ProductRepo;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/product")
@CrossOrigin("*")
public class ProductController {
    private final ProductRepo productRepo;

    @Autowired

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Product> list() {
        return productRepo.findAll();
    }
    @GetMapping("/{id}")
    @JsonView(Views.FullProduct.class)
    public Product getOne(@PathVariable("id") Product product){
        return product;
    }


    @PostMapping
    public Product create(@RequestBody Product product) {
        product.setCreationDate(LocalDateTime.now());
        return productRepo.save(product);
    }
    @PutMapping("/{id}")
    public Product update(@PathVariable("id") Product productFromDb,
                          @RequestBody Product product) {
        BeanUtils.copyProperties(product, productFromDb, "id");

        return productRepo.save(productFromDb);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Product product){
        productRepo.delete(product);
    }
}


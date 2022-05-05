package ru.zabgu.internetshop.repo;


import ru.zabgu.internetshop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long>{}


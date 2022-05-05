package ru.zabgu.internetshop.controller;

import org.springframework.web.bind.annotation.*;
import ru.zabgu.internetshop.controller.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    private int counter =4;
    private final List<Map<String, String>> products = new ArrayList<>() {{
        add(new HashMap<>() {{
            put("id", "1");
            put("text", "First product");
        }});
        add(new HashMap<>() {{
            put("id", "2");
            put("text", "Second product");
        }});
        add(new HashMap<>() {{
            put("id", "3");
            put("text", "Third product");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> list() {
        return products;
    }
    @GetMapping("/{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getId(id);
    }

    private Map<String, String> getId(String id) {
        return products.stream()
                .filter(product -> product.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String>product) {
        product.put("id", String.valueOf(counter++));

        products.add(product);

        return product;
    }
    @PutMapping
    public Map<String, String> update(@RequestParam("id") String id, @RequestParam("productIzm") String product) {
        Map<String, String> productFromDb = getId("id");
        products.add(new HashMap<>() {{
            put("id", id);
            put("text", product);
        }});

        return productFromDb;
    }
    @DeleteMapping
    public void delete(@RequestParam("id") String id){
        Map<String, String>product=getId(id);

        products.remove(product);
    }
}


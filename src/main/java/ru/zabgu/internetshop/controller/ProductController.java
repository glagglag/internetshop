package ru.zabgu.internetshop.controller;

import org.springframework.web.bind.annotation.*;
import ru.zabgu.internetshop.controller.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("product")


public class ProductController {
    private int counter =4;
    private List<Map<String, String>> products = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{ put("id", "1"); put("text", "First product"); }});
        add(new HashMap<String, String>() {{ put("id", "2"); put("text", "Second product"); }});
        add(new HashMap<String, String>() {{ put("id", "3"); put("text", "Third product"); }});
    }};

    @GetMapping("/all")
    public List<Map<String, String>> list() {
        return products;
    }
    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getId(id);
    }

    private Map<String, String> getId(String id) {
        return products.stream()
                .filter(product -> product.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping("/add")
    public Map<String, String> create(@RequestBody Map<String, String>product) {
        product.put("id", String.valueOf(counter++));

        products.add(product);

        return product;
    }
    @PutMapping("{id}")
    public Map<String, String> update(@RequestBody String id, @RequestBody Map<String, String>product) {
        Map<String, String> productFromDb = getId(product.get("id"));

        productFromDb.putAll(product);
        productFromDb.put("id", id);

        return productFromDb;
    }
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        Map<String, String>product=getId(id);

        products.remove(product);
    }
}


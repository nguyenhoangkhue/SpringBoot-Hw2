package com.example.springboothw2.controller;

import com.example.springboothw2.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
public class ProductController {
    private final List<Product> products;
    public ProductController() {
        products = new ArrayList<>();
        products.add(new Product(1, "IP11", "The cheapest IP", 1000,"Apple"));
        products.add(new Product(2, "IP12", "Best choice in its price range", 1500,"Apple"));
        products.add(new Product(3, "IP13", "Good one", 2000,"Apple"));
        products.add(new Product(4, "IP14", "Best choice in its price range", 4000,"Apple"));
        products.add(new Product(5, "IP15", "Good one", 5000,"Apple"));
        products.add(new Product(6, "S22", "Good one", 1500,"SAMSUNG"));
        products.add(new Product(7, "S23", "Good one", 2500,"SAMSUNG"));
        products.add(new Product(8, "S24", "Good one", 3500,"SAMSUNG"));
    }

    @GetMapping("/products")// http://localhost:8083/products
    public List<Product> getBooks() {
        return products;
    }
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){
        for(Product product:products){
            if(product.getId()==id){
                return new ResponseEntity<>(product, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("/products/name-start/{prefix}")
    public ResponseEntity<List<Product>>getProductByPrefix(@PathVariable String prefix){
        List<Product>result=new ArrayList<>();
        for (Product product : products) {
            if(product.getName().toLowerCase().contains(prefix.toLowerCase())){
                result.add(product);
            }
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("products/price/{min}/{max}")
    public ResponseEntity<List<Product>>getProductByPrice(@PathVariable int min,@PathVariable int max){
        List<Product>result=new ArrayList<>();
        for (Product product : products) {
            if(product.getPrice()<=max&&product.getPrice()>=min){
                result.add(product);
            }
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/products/brand/{brand}")
    public ResponseEntity<List<Product>> getProductByBrand(@PathVariable String brand){
        List<Product>result=new ArrayList<>();
        for(Product product:products){
            if(product.getBrand().toLowerCase().equals(brand.toLowerCase())){
                result.add(product);
            }
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/products/brand/{brand}/max-price")
    public ResponseEntity<Product> getMaxPriceProductOfBrand(@PathVariable String brand){
        products.sort(new Comparator<Product>(){
            @Override
            public int compare(Product o1,Product o2){
                return o2.getPrice()-o1.getPrice();
            }
        });
        for(Product product:products){
            if(product.getBrand().toLowerCase().equals(brand.toLowerCase())){
                return new ResponseEntity<>(product,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
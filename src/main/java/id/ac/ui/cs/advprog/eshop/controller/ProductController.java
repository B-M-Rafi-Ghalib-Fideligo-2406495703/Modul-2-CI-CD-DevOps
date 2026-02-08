package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    // WEB ENDPOINTS

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

    @GetMapping("/{productId}/edit")
    public String editProductPage(@PathVariable String productId, Model model) {
        Product product = service.getProductById(productId);
        model.addAttribute("product", product);
        return "editProduct";
    }

    // API ENDPOINTS

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        service.create(product);
        return "redirect:list";
    }

    @PostMapping("/{productId}/edit")
    public String editProductPost(@ModelAttribute Product product, Model model, @PathVariable String productId) {
        product.setProductId(productId);
        service.edit(product);
        return "redirect:../list";
    }

    @DeleteMapping("/{productId}/delete")
    public String deleteProduct(@PathVariable String productId, Model model) {
        service.delete(productId);
        return "redirect:../list";
    }
}

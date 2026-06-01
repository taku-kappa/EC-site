package backend.controller;

import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductResponse;
import backend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 商品コントローラー
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /**
     * 商品一覧取得
     *
     * GET /products
     *
     * @param categoryId カテゴリID
     * @return 商品一覧
     */
    @GetMapping
    public List<ProductResponse> getProducts(
            @RequestParam(required = false)
            Long categoryId) {

        return productService.getProducts(categoryId);
    }

    /**
     * 商品詳細取得
     *
     * @param id 商品ID
     * @return 商品詳細
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.getProductDetail(id)
        );
    }
}
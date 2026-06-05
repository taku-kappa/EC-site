package backend.controller;

import backend.dto.request.ProductCreateRequest;
import backend.dto.request.ProductStockUpdateRequest;
import backend.dto.request.ProductUpdateRequest;
import backend.dto.response.ApiResponse;
import backend.dto.response.ProductCreateResponse;
import backend.dto.response.ProductDeleteResponse;
import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductListResponse;
import backend.dto.response.ProductStockUpdateResponse;
import backend.dto.response.ProductUpdateResponse;
import backend.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<ProductListResponse> getProducts(
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

    /**
     * 商品新規登録
     *
     * @param request 商品登録情報
     * @return 登録結果
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductCreateResponse>>
    createProduct(
            @Valid @RequestBody ProductCreateRequest request) {

        ProductCreateResponse response =
                productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(response));
    }

    /**
     * 商品情報更新
     *
     * @param id 商品ID
     * @param request 更新内容
     * @return 更新結果
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductUpdateResponse>>
    updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody ProductUpdateRequest request) {

        ProductUpdateResponse response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    /**
     * 商品削除
     *
     * @param id 商品ID
     * @return 削除結果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDeleteResponse>>
    deleteProduct(
            @PathVariable Long id) {

        ProductDeleteResponse response =
                productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }

    /**
     * 商品在庫更新
     *
     * @param id 商品ID
     * @param request 更新情報
     * @return 更新結果
     */
    @PutMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductStockUpdateResponse>>
    updateProductStock(
            @PathVariable Long id,
            @Valid
            @RequestBody ProductStockUpdateRequest request) {

        ProductStockUpdateResponse response =
                productService.updateProductStock(
                        id,
                        request
                );

        return ResponseEntity.ok(
                ApiResponse.success(response)
        );
    }
}
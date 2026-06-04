package backend.service;

import backend.dto.request.ProductCreateRequest;
import backend.dto.request.ProductUpdateRequest;
import backend.dto.response.ProductCreateResponse;
import backend.dto.response.ProductDeleteResponse;
import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductListResponse;
import backend.dto.response.ProductUpdateResponse;
import backend.entity.Product;
import backend.entity.ProductStock;
import backend.mapper.ProductMapper;
import backend.mapper.ProductStockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品サービス
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductStockMapper productStockMapper;

    /**
     * 商品一覧取得
     *
     * @param categoryId カテゴリID
     * @return 商品一覧
     */
    public List<ProductListResponse> getProducts(Long categoryId) {

        return productMapper.findProducts(categoryId);
    }

    /**
     * 商品詳細取得
     *
     * @param id 商品ID
     * @return 商品詳細
     */
    public ProductDetailResponse getProductDetail(Long id) {

        ProductDetailResponse product =
                productMapper.findDetailById(id);

        if (product == null) {
            throw new RuntimeException("商品が存在しません");
        }

        return product;
    }

    /**
     * 商品新規登録
     *
     * products と product_stocks を
     * 同一トランザクションで登録する
     *
     * @param request 商品登録情報
     * @return 登録結果
     */
    @Transactional
    public ProductCreateResponse createProduct(
            ProductCreateRequest request) {

        Product product = new Product();

        // Productエンティティにリクエストの情報を格納
        product.setCategoryId(request.getCategoryId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        // productsテーブルに商品情報登録
        productMapper.insert(product);

        ProductStock productStock = new ProductStock();

        // ProductStockエンティティに情報を格納
        productStock.setProductId(product.getId());
        productStock.setStockQuantity(request.getStockQuantity());

        // product_stocksテーブルに商品在庫情報を格納
        productStockMapper.insert(productStock);

        return new ProductCreateResponse(
                product.getId(),
                "商品を登録しました"
        );
    }

    /**
     * 商品情報更新
     *
     * 楽観ロック(version)を利用して更新を行う
     *
     * @param id 商品ID
     * @param request 更新情報
     * @return 更新結果
     */
    @Transactional
    public ProductUpdateResponse updateProduct(
            Long id,
            ProductUpdateRequest request) {

        Product product = productMapper.findById(id);

        if (product == null) {
            throw new RuntimeException("商品が存在しません");
        }

        product.setCategoryId(request.getCategoryId());
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());

        // リクエストのversionを設定
        product.setVersion(request.getVersion());

        int updateCount = productMapper.update(product);

        if (updateCount == 0) {
            throw new RuntimeException(
                    "他ユーザーによって更新されています。再取得してください。"
            );
        }

        return new ProductUpdateResponse(
                id,
                "商品情報を更新しました"
        );
    }

    /**
     * 商品削除
     *
     * 論理削除を実施する
     *
     * @param id 商品ID
     * @return 削除結果
     */
    @Transactional
    public ProductDeleteResponse deleteProduct(Long id) {

        Product product = productMapper.findById(id);

        if (product == null) {
            throw new RuntimeException("商品が存在しません");
        }

        int deleteCount = productMapper.deleteById(id);

        if (deleteCount == 0) {
            throw new RuntimeException("商品削除に失敗しました");
        }

        return new ProductDeleteResponse(
                id,
                "商品を削除しました"
        );
    }
}
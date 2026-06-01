package backend.service;

import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductResponse;
import backend.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品サービス
 */
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    /**
     * 商品一覧取得
     *
     * @param categoryId カテゴリID
     * @return 商品一覧
     */
    public List<ProductResponse> getProducts(Long categoryId) {

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
}
package backend.mapper;

import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import backend.entity.Product;

import java.util.List;

/**
 * 商品Mapper
 */
@Mapper
public interface ProductMapper {

    /**
     * 商品一覧取得
     *
     * @param categoryId カテゴリID
     * @return 商品一覧
     */
    List<ProductListResponse> findProducts(
            @Param("categoryId") Long categoryId
    );

    /**
     * 商品詳細取得
     *
     * @param id 商品ID
     * @return 商品詳細
     */
    ProductDetailResponse findDetailById(Long id);

    /**
     * 商品登録
     *
     * @param product 商品
     * @return 登録件数
     */
    int insert(Product product);

    /**
     * 商品取得
     *
     * @param id 商品ID
     * @return 商品
     */
    Product findById(Long id);

    /**
     * 商品更新
     *
     * @param product 商品
     * @return 更新件数
     */
    int update(Product product);
}
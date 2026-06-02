package backend.mapper;

import backend.dto.response.ProductDetailResponse;
import backend.dto.response.ProductResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

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
    List<ProductResponse> findProducts(
            @Param("categoryId") Long categoryId
    );

    /**
     * 商品詳細取得
     *
     * @param id 商品ID
     * @return 商品詳細
     */
    ProductDetailResponse findDetailById(Long id);
}
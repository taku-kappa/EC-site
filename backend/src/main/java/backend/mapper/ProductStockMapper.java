package backend.mapper;

import backend.entity.ProductStock;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品在庫Mapper
 */
@Mapper
public interface ProductStockMapper {

    /**
     * 商品在庫登録
     *
     * @param productStock 商品在庫
     * @return 登録件数
     */
    int insert(ProductStock productStock);
}
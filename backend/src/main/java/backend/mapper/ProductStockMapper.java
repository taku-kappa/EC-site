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

    /**
     * 商品在庫取得
     *
     * @param productId 商品ID
     * @return 商品在庫
     */
    ProductStock findByProductId(Long productId);

    /**
     * 商品在庫更新
     *
     * @param productStock 商品在庫
     * @return 更新件数
     */
    int update(ProductStock productStock);

    /**
     * 商品在庫取得（悲観ロック）
     *
     * 注文確定時に他トランザクションからの更新を防ぐため、
     * SELECT ... FOR UPDATE を使用して取得します。
     *
     * @param productId 商品ID
     * @return 商品在庫
     */
    ProductStock findByProductIdForUpdate(Long productId);
}
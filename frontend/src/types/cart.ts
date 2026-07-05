/**
 * カート追加リクエスト
 */
export interface AddCartRequest {

    /**
     * 商品ID
     */
    productId: number;

    /**
     * 数量
     */
    quantity: number;

}
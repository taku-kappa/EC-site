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

/**
 * カート商品
 */
export interface CartItem {

    cartItemId: number;

    productId: number;

    productName: string;

    imageUrl: string | null;

    price: number;

    quantity: number;

    subtotal: number;

}

/**
 * カートレスポンス
 */
export interface CartResponse {

    cartId: number;

    items: CartItem[];

    totalPrice: number;

}
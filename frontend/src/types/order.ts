/**
 * 注文確定レスポンス
 */
export interface OrderResponse {

    orderId: number;

    totalPrice: number;

    status: string;

}

/**
 * 注文履歴一覧
 */
export interface OrderHistoryResponse {

    orderId: number;

    totalPrice: number;

    status: string;

    orderedAt: string;

}

/**
 * 注文商品
 */
export interface OrderItemResponse {

    productId: number;

    productName: string;

    price: number;

    quantity: number;

    subtotal: number;

}

/**
 * 注文履歴詳細
 */
export interface OrderHistoryDetailResponse {

    orderId: number;

    totalPrice: number;

    status: string;

    orderedAt: string;

    items: OrderItemResponse[];

}
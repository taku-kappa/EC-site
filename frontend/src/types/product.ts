/**
 * 商品情報
 */
export interface Product {

    // 商品ID
    id: number;

    // 商品名
    name: string;

    //商品説明
    description: string;

    // 価格
    price: number;

    // 商品画像
    imageUrl: string | null;

    // カテゴリID
    categoryId: number;

    // 在庫数
    stockQuantity: number;

}
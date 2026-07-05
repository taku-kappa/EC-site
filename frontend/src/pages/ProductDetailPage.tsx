import { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { getProductById } from "../api/productApi";

import type { Product } from "../types/product";

import { addCart } from "../api/cartApi";



/**
 * 商品詳細画面
 */
function ProductDetailPage() {

    /**
     * URLの商品ID取得
     */
    const { id } = useParams();

    /**
     * 商品情報
     */
    const [product, setProduct] = useState<Product>();

    /**
     * 購入数量
     */
    const [quantity, setQuantity] = useState(1);

    /**
     * 画面遷移用
     */
    const navigate = useNavigate();

    /**
     * 商品取得
     */
    const loadProduct = async () => {

        try {

            if (!id) {

                return;

            }

            const response = await getProductById(
                Number(id)
            );

            setProduct(response);

        } catch (error) {

            console.error(error);

            alert("商品取得に失敗しました。");

        }

    };

    /**
     * カート追加
     */
    const handleAddCart = async () => {

        if (!product) {

            return;

        }

        try {

            await addCart({

                productId: product.id,

                quantity: quantity

            });

            alert("カートへ追加しました。");

        } catch (error) {

            console.error(error);

            alert("カート追加に失敗しました。");

        }

    };

    /**
     * 初回表示
     */
    useEffect(() => {

        loadProduct();

    }, []);

    /**
     * 読み込み中
     */
    if (!product) {

        return <p>読み込み中...</p>;

    }

    return (

        <div>

            <h1>商品詳細</h1>

            <h2>{product.name}</h2>

            <p>{product.description}</p>

            <p>価格：{product.price}円</p>

            <p>在庫：{product.stockQuantity}</p>

            {

                product.imageUrl && (

                    <img
                        src={product.imageUrl}
                        width="200"
                        alt={product.name}
                    />

                )

            }

            <div>
                <label>数量</label>

                <br />

                <input
                    type="number"
                    min={1}
                    value={quantity}
                    onChange={(e) => {

                        const value = Number(e.target.value);

                        if (value >= 1) {
                            setQuantity(value);
                        }

                    }}
                />
            </div>

            <br />

            <br />

            <button
                onClick={handleAddCart}
            >
                カートへ追加
            </button>

            <br />
            <br />

            <button
                onClick={() => navigate("/cart")}
            >
                カートを見る
            </button>

        </div>

    );

}

export default ProductDetailPage;
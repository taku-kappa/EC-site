import { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { AxiosError } from "axios";

import { getProductById } from "../api/productApi";
import { addCart } from "../api/cartApi";

import type { Product } from "../types/product";
import type { ErrorResponse } from "../types/errorResponse";

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
     * エラーメッセージ
     */
    const [errorMessage, setErrorMessage] = useState("");

    /**
     * 画面遷移用
     */
    const navigate = useNavigate();

    /**
     * 商品取得
     */
    const loadProduct = async () => {

        /**
         * 前回のエラーメッセージをクリア
         */
        setErrorMessage("");

        try {

            if (!id) {

                return;

            }

            const response =
                await getProductById(Number(id));

            setProduct(response);

        } catch (error) {

            const axiosError =
                error as AxiosError<ErrorResponse>;

            /**
             * 通信エラー
             */
            if (!axiosError.response) {

                setErrorMessage(
                    "通信エラーが発生しました。"
                );

                return;

            }

            switch (axiosError.response.status) {

                case 404:

                    setErrorMessage(
                        axiosError.response.data.message
                    );

                    break;

                case 500:

                    setErrorMessage(
                        "システムエラーが発生しました。"
                    );

                    break;

                default:

                    setErrorMessage(
                        "予期しないエラーが発生しました。"
                    );

            }

        }

    };

    /**
     * カート追加
     */
    const handleAddCart = async () => {

        if (!product) {

            return;

        }

        /**
         * 前回のエラーメッセージをクリア
         */
        setErrorMessage("");

        try {

            await addCart({

                productId: product.id,

                quantity: quantity

            });

            alert("カートへ追加しました。");

        } catch (error) {

            const axiosError =
                error as AxiosError<ErrorResponse>;

            /**
             * 通信エラー
             */
            if (!axiosError.response) {

                setErrorMessage(
                    "通信エラーが発生しました。"
                );

                return;

            }

            switch (axiosError.response.status) {

                case 400:

                    setErrorMessage(
                        axiosError.response.data.message
                    );

                    break;

                case 500:

                    setErrorMessage(
                        "システムエラーが発生しました。"
                    );

                    break;

                default:

                    setErrorMessage(
                        "予期しないエラーが発生しました。"
                    );

            }

        }

    };

    /**
     * 初回表示
     */
    useEffect(() => {

        loadProduct();

    }, []);

    /**
     * 商品未取得時
     */
    if (!product) {

        return (

            <div>

                <h1>商品詳細</h1>

                {
                    errorMessage && (
                        <p>{errorMessage}</p>
                    )
                }

                {
                    !errorMessage && (
                        <p>読み込み中...</p>
                    )
                }

            </div>

        );

    }

    return (

        <div>

            <h1>商品詳細</h1>

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

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
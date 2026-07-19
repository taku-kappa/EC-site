import { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";

import { AxiosError } from "axios";

import { getProducts } from "../api/productApi";

import type { Product } from "../types/product";
import type { ErrorResponse } from "../types/errorResponse";

/**
 * 商品一覧画面
 */
function ProductListPage() {

    /**
     * 画面遷移
     */
    const navigate = useNavigate();

    /**
     * 商品一覧
     */
    const [products, setProducts] = useState<Product[]>([]);

    /**
     * エラーメッセージ
     */
    const [errorMessage, setErrorMessage] = useState("");

    /**
     * 商品一覧取得
     */
    const loadProducts = async () => {

        /**
         * 前回のエラーメッセージをクリア
         */
        setErrorMessage("");

        try {

            const response = await getProducts();

            console.log("商品一覧レスポンス:", response);
            console.log("配列か？", Array.isArray(response));

            setProducts(response);

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
     * 初回表示時
     */
    useEffect(() => {

        loadProducts();

    }, []);

    return (

        <div>

            <h1>商品一覧</h1>

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

            {

                products.map((product) => (

                    <div
                        key={product.id}
                        onClick={() =>
                            navigate(`/products/${product.id}`)
                        }
                        style={{
                            cursor: "pointer",
                            border: "1px solid gray",
                            padding: "10px",
                            marginBottom: "10px"
                        }}
                    >

                        <h3>{product.name}</h3>

                        <p>価格：{product.price}円</p>

                        {

                            product.imageUrl && (

                                <img
                                    src={product.imageUrl}
                                    width="150"
                                    alt={product.name}
                                />

                            )

                        }

                    </div>

                ))

            }

        </div>

    );

}

export default ProductListPage;
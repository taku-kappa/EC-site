import { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";

import { AxiosError } from "axios";

import { getCart } from "../api/cartApi";
import { createOrder } from "../api/orderApi";

import type { CartResponse } from "../types/cart";
import type { ErrorResponse } from "../types/errorResponse";

/**
 * 注文確認画面
 */
function OrderConfirmPage() {

    /**
     * 画面遷移
     */
    const navigate = useNavigate();

    /**
     * カート情報
     */
    const [cart, setCart] =
        useState<CartResponse | null>(null);

    /**
     * 注文中フラグ
     */
    const [ordering, setOrdering] =
        useState(false);

    /**
     * エラーメッセージ
     */
    const [errorMessage, setErrorMessage] =
        useState("");

    /**
     * カート取得
     */
    const loadCart = async () => {

        setErrorMessage("");

        try {

            const response = await getCart();

            setCart(response);

        } catch (error) {

            const axiosError =
                error as AxiosError<ErrorResponse>;

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
     * 注文確定
     */
    const handleOrder = async () => {

        if (ordering) {

            return;

        }

        const result = window.confirm(
            "注文を確定しますか？"
        );

        if (!result) {

            return;

        }

        setErrorMessage("");

        try {

            setOrdering(true);

            const response = await createOrder();

            alert(
                `注文が完了しました。\n注文番号：${response.orderId}`
            );

            navigate("/orders");

        } catch (error) {

            const axiosError =
                error as AxiosError<ErrorResponse>;

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

        } finally {

            setOrdering(false);

        }

    };

    /**
     * 初回表示
     */
    useEffect(() => {

        void loadCart();

    }, []);

    /**
     * 読み込み中
     */
    if (cart == null) {

        return (

            <div>

                <h1>注文確認</h1>

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

            <h1>注文確認</h1>

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

            <hr />

            <h2>注文商品</h2>

            {

                cart.items.map((item) => (

                    <div
                        key={item.cartItemId}
                        style={{
                            border: "1px solid gray",
                            marginBottom: "10px",
                            padding: "10px"
                        }}
                    >

                        <h3>{item.productName}</h3>

                        <p>価格：{item.price}円</p>

                        <p>数量：{item.quantity}</p>

                        <p>小計：{item.subtotal}円</p>

                    </div>

                ))

            }

            <hr />

            <h2>
                合計金額：{cart.totalPrice}円
            </h2>

            <br />

            <button
                onClick={() => navigate("/cart")}
            >
                カートへ戻る
            </button>

            {" "}

            <button
                onClick={handleOrder}
                disabled={ordering}
            >

                {
                    ordering
                        ? "注文中..."
                        : "注文を確定する"
                }

            </button>

        </div>

    );

}

export default OrderConfirmPage;
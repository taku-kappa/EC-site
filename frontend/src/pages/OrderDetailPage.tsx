import { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { AxiosError } from "axios";

import { getOrderDetail } from "../api/orderApi";

import type { OrderHistoryDetailResponse } from "../types/order";
import type { ErrorResponse } from "../types/errorResponse";

/**
 * 注文履歴詳細画面
 */
function OrderDetailPage() {

    /**
     * 画面遷移
     */
    const navigate = useNavigate();

    /**
     * URLパラメータ
     */
    const { id } = useParams();

    /**
     * 注文詳細
     */
    const [order, setOrder] =
        useState<OrderHistoryDetailResponse | null>(null);

    /**
     * エラーメッセージ
     */
    const [errorMessage, setErrorMessage] =
        useState("");

    /**
     * 注文詳細取得
     */
    const loadOrder = async () => {

        /**
         * 前回のエラーメッセージをクリア
         */
        setErrorMessage("");

        if (!id) {

            return;

        }

        try {

            const response =
                await getOrderDetail(Number(id));

            setOrder(response);

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
     * 初回表示
     */
    useEffect(() => {

        void loadOrder();

    }, []);

    /**
     * 読み込み中
     */
    if (order == null) {

        return (

            <div>

                <h1>注文詳細</h1>

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

            <h1>注文詳細</h1>

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

            <hr />

            <p>

                <strong>注文番号：</strong>

                {order.orderId}

            </p>

            <p>

                <strong>注文日時：</strong>

                {
                    new Date(
                        order.orderedAt
                    ).toLocaleString()
                }

            </p>

            <p>

                <strong>注文ステータス：</strong>

                {order.status}

            </p>

            <p>

                <strong>合計金額：</strong>

                {order.totalPrice}円

            </p>

            <hr />

            <h2>購入商品</h2>

            {

                order.items.map((item) => (

                    <div
                        key={item.productId}
                        style={{
                            border: "1px solid gray",
                            padding: "10px",
                            marginBottom: "10px"
                        }}
                    >

                        <h3>

                            {item.productName}

                        </h3>

                        <p>

                            単価：
                            {item.price}円

                        </p>

                        <p>

                            数量：
                            {item.quantity}

                        </p>

                        <p>

                            小計：
                            {item.subtotal}円

                        </p>

                    </div>

                ))

            }

            <br />

            <button
                onClick={() =>
                    navigate("/orders")
                }
            >

                注文履歴へ戻る

            </button>

        </div>

    );

}

export default OrderDetailPage;
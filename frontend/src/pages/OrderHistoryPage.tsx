import { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";

import { AxiosError } from "axios";

import { getOrderHistory } from "../api/orderApi";

import type { OrderHistoryResponse } from "../types/order";
import type { ErrorResponse } from "../types/errorResponse";

/**
 * 注文履歴一覧画面
 */
function OrderHistoryPage() {

    /**
     * 画面遷移
     */
    const navigate = useNavigate();

    /**
     * 注文履歴
     */
    const [orders, setOrders] =
        useState<OrderHistoryResponse[]>([]);

    /**
     * エラーメッセージ
     */
    const [errorMessage, setErrorMessage] =
        useState("");

    /**
     * 注文履歴取得
     */
    const loadOrders = async () => {

        /**
         * 前回のエラーメッセージをクリア
         */
        setErrorMessage("");

        try {

            const response =
                await getOrderHistory();

            setOrders(response);

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

        void loadOrders();

    }, []);

    return (

        <div>

            <h1>注文履歴</h1>

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

            {

                orders.length === 0 ? (

                    <p>注文履歴はありません。</p>

                ) : (

                    orders.map((order) => (

                        <div
                            key={order.orderId}
                            onClick={() =>
                                navigate(
                                    `/orders/${order.orderId}`
                                )
                            }
                            style={{
                                cursor: "pointer",
                                border: "1px solid gray",
                                marginBottom: "10px",
                                padding: "10px"
                            }}
                        >

                            <h3>

                                注文番号：
                                {order.orderId}

                            </h3>

                            <p>

                                注文日時：
                                {
                                    new Date(
                                        order.orderedAt
                                    ).toLocaleString()
                                }

                            </p>

                            <p>

                                合計金額：
                                {order.totalPrice}円

                            </p>

                            <p>

                                ステータス：
                                {order.status}

                            </p>

                        </div>

                    ))

                )

            }

        </div>

    );

}

export default OrderHistoryPage;
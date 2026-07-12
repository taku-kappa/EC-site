import { useEffect, useState } from "react";

import { useNavigate, useParams } from "react-router-dom";

import { getOrderDetail } from "../api/orderApi";

import type { OrderHistoryDetailResponse } from "../types/order";

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
     * 注文詳細取得
     */
    const loadOrder = async () => {

        if (!id) {

            return;

        }

        try {

            const response =
                await getOrderDetail(Number(id));

            setOrder(response);

        } catch (error) {

            console.error(error);

            alert("注文詳細の取得に失敗しました。");

        }

    };

    /**
     * 初回表示
     */
    useEffect(() => {

        loadOrder();

    }, []);

    /**
     * 読み込み中
     */
    if (order == null) {

        return <p>読み込み中...</p>;

    }

    return (

        <div>

            <h1>注文詳細</h1>

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
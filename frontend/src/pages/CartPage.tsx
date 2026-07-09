import { useEffect, useState } from "react";

import { useNavigate } from "react-router-dom";

import {

    getCart,
    updateCartQuantity,
    deleteCartItem
} from "../api/cartApi";

import type {
    CartItem,
    CartResponse
} from "../types/cart";

/**
 * カート画面
 */
function CartPage() {

    /**
     * カート商品一覧
     */
    const [cartItems, setCartItems] = useState<CartItem[]>([]);

    /**
     * 合計金額
     */
    const [totalPrice, setTotalPrice] = useState(0);

    /**
     * 画面遷移用
     */
    const navigate = useNavigate();

    /**
     * カート一覧取得
     */
    const loadCart = async () => {

        try {

            const response: CartResponse = await getCart();

            setCartItems(response.items);

            setTotalPrice(response.totalPrice);

        } catch (error) {

            console.error(error);

            alert("カート情報の取得に失敗しました。");

        }
    };

    /**
     * 初回表示
     */
    useEffect(() => {

        void loadCart();

    }, []);

    /**
     * 数量変更
     */
    const handleQuantityChange = async (

        cartItemId: number,

        quantity: number

    ) => {

        try {

            await updateCartQuantity(
                cartItemId,
                quantity
            );

            await loadCart();

        } catch (error) {

            console.error(error);

            alert("数量変更に失敗しました。");

        }
    };

    /**
     * 商品削除
     */
    const handleDelete = async (

        cartItemId: number

    ) => {

        try {

            await deleteCartItem(cartItemId);

            await loadCart();

        } catch (error) {

            console.error(error);

            alert("商品削除に失敗しました。");

        }
    };


    return (

    <div>

        <h1>カート</h1>

        {

            cartItems.length === 0 ? (

                <p>カートに商品がありません。</p>

            ) : (

                <>

                    {

                        cartItems.map((item) => (

                            <div
                                key={item.cartItemId}

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
                                    価格：{item.price}円
                                </p>

                                <p>
                                    小計：{item.subtotal}円
                                </p>

                                <label>
                                    数量
                                </label>

                                <br />

                                <input
                                    type="number"

                                    min={1}

                                    value={item.quantity}

                                    onChange={(e) =>

                                        handleQuantityChange(

                                            item.cartItemId,

                                            Number(e.target.value)

                                        )

                                    }
                                />

                                <br />

                                <br />

                                <button
                                    onClick={() =>

                                        handleDelete(

                                            item.cartItemId

                                        )

                                    }
                                >

                                    商品削除

                                </button>

                            </div>

                        ))

                    }

                    <hr />

                    <h2>
                        合計金額：{totalPrice}円
                    </h2>

                    <br />

                    <button
                        onClick={() => navigate("/products")}
                    >
                        買い物を続ける
                    </button>

                    {" "}

                    <button
                        onClick={() => navigate("/order-confirm")}
                    >
                        注文確認へ
                    </button>

                </>

            )

        }

    </div>

);

}

export default CartPage;
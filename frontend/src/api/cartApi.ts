import axios from "axios";

import type { AddCartRequest } from "../types/cart";

/**
 * APIベースURL
 */
const API_BASE_URL = "http://localhost:8081";

/**
 * カート追加
 */
export const addCart = async (

    request: AddCartRequest

): Promise<void> => {

    const token = localStorage.getItem("token");

    await axios.post(

        `${API_BASE_URL}/api/cart/items`,

        request,

        {
            headers: {

                Authorization: `Bearer ${token}`

            }

        }

    );

};
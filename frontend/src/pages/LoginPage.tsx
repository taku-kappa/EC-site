import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AxiosError } from "axios";

import { login } from "../api/userApi";

import type { ErrorResponse } from "../types/errorResponse";

/**
 * ログイン画面
 */
function LoginPage() {

    // メールアドレス
    const [email, setEmail] = useState("");

    // パスワード
    const [password, setPassword] = useState("");

    // エラーメッセージ
    const [errorMessage, setErrorMessage] = useState("");

    // 画面遷移用のオブジェクト
    const navigate = useNavigate();

    /**
     * ログイン処理
     */
    const handleLogin = async () => {

        /**
         * 前回表示したエラーメッセージをクリア
         */
        setErrorMessage("");

        try {

            const response = await login({
                email,
                password
            });

            /**
             * JWT保存
             */
            localStorage.setItem(
                "token",
                response.token
            );

            navigate("/products");

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

    return (

        <div>

            <h1>ログイン</h1>

            <div>

                <label>メールアドレス</label>

                <br />

                <input
                    type="email"
                    value={email}
                    onChange={(e) =>
                        setEmail(e.target.value)
                    }
                />

            </div>

            <br />

            <div>

                <label>パスワード</label>

                <br />

                <input
                    type="password"
                    value={password}
                    onChange={(e) =>
                        setPassword(e.target.value)
                    }
                />

            </div>

            <br />

            {
                errorMessage && (
                    <p>{errorMessage}</p>
                )
            }

            <button onClick={handleLogin}>
                ログイン
            </button>

        </div>

    );
}

export default LoginPage;
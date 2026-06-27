import { useState } from "react";
import { login } from "../api/userApi";

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

    /**
     * ログイン処理
     */
    const handleLogin = async () => {

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

            alert("ログイン成功");

        } catch {

            setErrorMessage(
                "メールアドレスまたはパスワードが正しくありません"
            );
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

            {errorMessage && (
                <p>{errorMessage}</p>
            )}

            <button onClick={handleLogin}>
                ログイン
            </button>
        </div>
    );
}

export default LoginPage;
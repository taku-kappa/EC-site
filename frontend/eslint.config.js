import js from "@eslint/js";
import globals from "globals";
import reactHooks from "eslint-plugin-react-hooks";
import reactRefresh from "eslint-plugin-react-refresh";
import tseslint from "typescript-eslint";
import { defineConfig, globalIgnores } from "eslint/config";

export default defineConfig([

    /**
     * ESLint対象外ディレクトリ
     */
    globalIgnores([
        "dist"
    ]),

    {

        /**
         * TypeScriptファイルを対象
         */
        files: [
            "**/*.{ts,tsx}"
        ],

        /**
         * 利用するルール
         */
        extends: [

            js.configs.recommended,

            ...tseslint.configs.recommended,

            reactHooks.configs.flat.recommended,

            reactRefresh.configs.vite,

        ],

        /**
         * ブラウザ環境
         */
        languageOptions: {

            globals: globals.browser,

        },

        /**
         * ルール設定
         */
        rules: {

            /**
             * API取得後にsetStateする処理は
             * Reactでは一般的なため警告を無効化
             */
            "react-hooks/set-state-in-effect": "off",

        },

    },

]);
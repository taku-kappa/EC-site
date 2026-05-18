import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  // ポート番号を8080へ変更
  server: {
      port: 8080,
  },
})

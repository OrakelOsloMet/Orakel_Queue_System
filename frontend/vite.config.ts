import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

//TODO Look here for proxy setup: https://vitejs.dev/config/server-options.html
// https://vitejs.dev/config/
export default defineConfig({
  server: {
    host: 'localhost',
    port: 3000
  },
  plugins: [react()],
})

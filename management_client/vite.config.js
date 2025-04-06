import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  build: {
    outDir: '../cit-sac-back-end/src/main/resources/static',
    emptyOutDir: true
  }
})

import axios from 'axios'
import Cookies from 'js-cookie' // Importa js-cookie

const axiosInstance = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL // URL de la API
})

// Interceptor de solicitudes
axiosInstance.interceptors.request.use(
  (config) => {
    const token = Cookies.get('token') // token de la cookie (se llama 'token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

export default axiosInstance

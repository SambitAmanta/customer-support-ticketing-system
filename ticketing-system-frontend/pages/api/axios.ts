import axios from "axios";

// Define Axios instance
const axiosInstance = axios.create({
  baseURL: "http://localhost:8080/api", // backend URL
});

// Add JWT token to every request
axiosInstance.interceptors.request.use((config) => {
  if (typeof window !== "undefined") {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
  }
  return config;
});

export default axiosInstance;

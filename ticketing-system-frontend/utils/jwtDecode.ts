export interface DecodedToken {
    sub: string; // Username
    role: string; // User Role (e.g., ADMIN, AGENT, CUSTOMER)
    exp: number; // Expiry time (in seconds)
  }
  
  export const decodeToken = (token: string): DecodedToken | null => {
    try {
      const payload = JSON.parse(atob(token.split(".")[1]));
      return payload;
    } catch (error) {
      console.error("Invalid token:", error);
      return null;
    }
  };
  
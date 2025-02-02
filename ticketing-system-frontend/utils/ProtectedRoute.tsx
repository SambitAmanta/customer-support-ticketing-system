import { useEffect } from "react";
import { useRouter } from "next/router";
import { decodeToken } from "./jwtDecode";

interface ProtectedRouteProps {
  allowedRoles: string[];
  children: JSX.Element;
}

const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  allowedRoles,
  children,
}) => {
  const router = useRouter();

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/login");
      return;
    }

    const decodedToken = decodeToken(token);
    if (!decodedToken || !allowedRoles.includes(decodedToken.role)) {
      router.push("/login");
    }
  }, [allowedRoles, router]);

  return children;
};

export default ProtectedRoute;

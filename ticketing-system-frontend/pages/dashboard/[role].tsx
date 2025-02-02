import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import axios from "../api/axios";
import { Box, Typography, CircularProgress, Alert } from "@mui/material";
import ProtectedRoute from "../../utils/ProtectedRoute";

interface DashboardData {
  openTickets?: number;
  inProgressTickets?: number;
  resolvedTickets?: number;
  closedTickets?: number;
  assignedTickets?: any[];
  averageResolutionTime?: number;
}

const Dashboard: React.FC = () => {
  const [data, setData] = useState<DashboardData | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();
  const { role } = router.query; // Dynamic role from URL

  useEffect(() => {
    if (!role) return; // Wait for role to be available

    const fetchDashboardData = async () => {
      setLoading(true);
      try {
        const endpoint =
          role === "admin"
            ? "/dashboard/admin"
            : role === "agent"
            ? "/dashboard/agent"
            : "/dashboard/customer";

        const response = await axios.get(endpoint);
        setData(response.data);
      } catch (err) {
        setError("Failed to load dashboard data");
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, [role]);

  if (loading) return <CircularProgress />;
  if (error) return <Alert severity="error">{error}</Alert>;

  return (
    <ProtectedRoute allowedRoles={["ADMIN", "AGENT", "CUSTOMER"]}>
      <Box sx={{ padding: 4 }}>
        <Typography variant="h4" gutterBottom>
          {role?.toString().toUpperCase()} Dashboard
        </Typography>

        {role === "admin" && data && (
          <Box>
            <Typography>Open Tickets: {data.openTickets}</Typography>
            <Typography>In Progress Tickets: {data.inProgressTickets}</Typography>
            <Typography>Resolved Tickets: {data.resolvedTickets}</Typography>
            <Typography>Closed Tickets: {data.closedTickets}</Typography>
          </Box>
        )}

        {role === "agent" && data?.assignedTickets && (
          <Box>
            <Typography>Assigned Tickets: {data.assignedTickets.length}</Typography>
            <Typography>
              Average Resolution Time: {data.averageResolutionTime} days
            </Typography>
          </Box>
        )}

        {role === "customer" && data && (
          <Box>
            <Typography>Your Tickets:</Typography>
            {data.map((ticket: any) => (
              <Typography key={ticket.id}>
                {ticket.title} - {ticket.status}
              </Typography>
            ))}
          </Box>
        )}
      </Box>
    </ProtectedRoute>
  );
};

export default Dashboard;

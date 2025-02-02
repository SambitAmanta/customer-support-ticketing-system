import React, { useEffect, useState } from "react";
import axios from "../api/axios";
import { Box, Typography } from "@mui/material";

interface AdminDashboardData {
  openTickets: number;
  inProgressTickets: number;
  resolvedTickets: number;
  closedTickets: number;
}

const AdminDashboard: React.FC = () => {
  const [data, setData] = useState<AdminDashboardData | null>(null);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const response = await axios.get("/dashboard/admin");
        setData(response.data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchDashboardData();
  }, []);

  if (!data) return <div>Loading...</div>;

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Admin Dashboard
      </Typography>
      <Typography>Open Tickets: {data.openTickets}</Typography>
      <Typography>In Progress Tickets: {data.inProgressTickets}</Typography>
      <Typography>Resolved Tickets: {data.resolvedTickets}</Typography>
      <Typography>Closed Tickets: {data.closedTickets}</Typography>
    </Box>
  );
};

export default AdminDashboard;

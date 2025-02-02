import React, { useEffect, useState } from "react";
import { useRouter } from "next/router";
import axios from "../api/axios";
import { Box, Typography, Button, MenuItem, Select } from "@mui/material";

interface Ticket {
  id: number;
  title: string;
  description: string;
  priority: string;
  status: string;
  createdDate: string;
}

const TicketDetails: React.FC = () => {
  const [ticket, setTicket] = useState<Ticket | null>(null);
  const [status, setStatus] = useState<string>("");
  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    const fetchTicket = async () => {
      try {
        const response = await axios.get(`/tickets/${id}`);
        setTicket(response.data);
        setStatus(response.data.status);
      } catch (err) {
        console.error(err);
      }
    };
    if (id) fetchTicket();
  }, [id]);

  const handleStatusChange = async () => {
    try {
      await axios.patch(`/tickets/${id}/status`, { status });
      alert("Ticket status updated!");
      router.push("/tickets");
    } catch (err) {
      console.error(err);
    }
  };

  if (!ticket) return <div>Loading...</div>;

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Ticket Details
      </Typography>
      <Typography>ID: {ticket.id}</Typography>
      <Typography>Title: {ticket.title}</Typography>
      <Typography>Description: {ticket.description}</Typography>
      <Typography>Priority: {ticket.priority}</Typography>
      <Typography>Status: {ticket.status}</Typography>
      <Typography>Created Date: {new Date(ticket.createdDate).toLocaleString()}</Typography>

      <Select
        value={status}
        onChange={(e) => setStatus(e.target.value)}
        sx={{ marginTop: 2 }}
      >
        <MenuItem value="OPEN">Open</MenuItem>
        <MenuItem value="IN_PROGRESS">In Progress</MenuItem>
        <MenuItem value="RESOLVED">Resolved</MenuItem>
        <MenuItem value="CLOSED">Closed</MenuItem>
      </Select>
      <Button
        variant="contained"
        sx={{ marginTop: 2 }}
        onClick={handleStatusChange}
      >
        Update Status
      </Button>
    </Box>
  );
};

export default TicketDetails;

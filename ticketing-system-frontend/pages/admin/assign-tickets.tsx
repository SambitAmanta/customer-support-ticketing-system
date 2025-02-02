import React, { useEffect, useState } from "react";
import axios from "../api/axios";
import { Box, Typography, Select, MenuItem, Button } from "@mui/material";

interface Ticket {
  id: number;
  title: string;
  status: string;
}

interface Agent {
  id: number;
  username: string;
}

const AssignTickets: React.FC = () => {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [agents, setAgents] = useState<Agent[]>([]);
  const [selectedTicket, setSelectedTicket] = useState<number | null>(null);
  const [selectedAgent, setSelectedAgent] = useState<number | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      const [ticketsResponse, agentsResponse] = await Promise.all([
        axios.get("/tickets"), // Fetch all tickets
        axios.get("/users?role=AGENT"), // Fetch all agents
      ]);
      setTickets(ticketsResponse.data);
      setAgents(agentsResponse.data);
    };
    fetchData();
  }, []);

  const handleAssign = async () => {
    try {
      await axios.post(`/tickets/${selectedTicket}/assign`, {
        id: selectedAgent,
      });
      alert("Ticket assigned successfully!");
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Assign Tickets to Agents
      </Typography>
      <Select
        fullWidth
        value={selectedTicket}
        onChange={(e) => setSelectedTicket(Number(e.target.value))}
        sx={{ marginBottom: 2 }}
      >
        {tickets.map((ticket) => (
          <MenuItem key={ticket.id} value={ticket.id}>
            {ticket.title} ({ticket.status})
          </MenuItem>
        ))}
      </Select>
      <Select
        fullWidth
        value={selectedAgent}
        onChange={(e) => setSelectedAgent(Number(e.target.value))}
        sx={{ marginBottom: 2 }}
      >
        {agents.map((agent) => (
          <MenuItem key={agent.id} value={agent.id}>
            {agent.username}
          </MenuItem>
        ))}
      </Select>
      <Button variant="contained" onClick={handleAssign}>
        Assign Ticket
      </Button>
    </Box>
  );
};

export default AssignTickets;

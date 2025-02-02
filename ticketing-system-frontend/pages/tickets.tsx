import React, { useEffect, useState } from "react";
import axios from "./api/axios";
import { List, ListItem, ListItemText, Typography, Box } from "@mui/material";

interface Ticket {
  id: number;
  title: string;
  status: string;
}

const Tickets: React.FC = () => {
  const [tickets, setTickets] = useState<Ticket[]>([]);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const response = await axios.get("/tickets");
        setTickets(response.data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchTickets();
  }, []);

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Tickets
      </Typography>
      <List>
        {tickets.map((ticket) => (
          <ListItem key={ticket.id}>
            <ListItemText primary={ticket.title} secondary={ticket.status} />
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default Tickets;

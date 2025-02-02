import React, { useState } from "react";
import { useRouter } from "next/router";
import axios from "./api/axios";
import { Box, TextField, Button, Typography } from "@mui/material";

const Login: React.FC = () => {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const router = useRouter();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      const response = await axios.post("/auth/login", { username, password });
      const token = response.data.split(" ")[1];
      localStorage.setItem("token", token); // Save JWT token
      const decodedToken = JSON.parse(atob(token.split(".")[1])); // Decode JWT
      router.push(`/dashboard/${decodedToken.role.toLowerCase()}`);
    } catch (err) {
      alert("Invalid username or password!");
    }
  };

  return (
    <Box
      sx={{
        maxWidth: 400,
        margin: "auto",
        marginTop: 10,
        padding: 4,
        boxShadow: 3,
        borderRadius: 2,
      }}
    >
      <Typography variant="h5" gutterBottom>
        Login
      </Typography>
      <form onSubmit={handleLogin}>
        <TextField
          fullWidth
          label="Username"
          margin="normal"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <TextField
          fullWidth
          label="Password"
          margin="normal"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <Button fullWidth variant="contained" type="submit" sx={{ marginTop: 2 }}>
          Login
        </Button>
      </form>
    </Box>
  );
};

export default Login;

import React, { useEffect, useState } from "react";
import axios from "./api/axios";
import { Box, Typography, TextField, Button } from "@mui/material";

interface User {
  id: number;
  username: string;
  role: string;
}

const Profile: React.FC = () => {
  const [user, setUser] = useState<User | null>(null);
  const [password, setPassword] = useState<string>("");

  useEffect(() => {
    const fetchUserProfile = async () => {
      try {
        const response = await axios.get("/auth/profile"); // Assuming this API exists
        setUser(response.data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchUserProfile();
  }, []);

  const handlePasswordChange = async () => {
    try {
      await axios.post("/auth/change-password", { password });
      alert("Password updated!");
    } catch (err) {
      console.error(err);
    }
  };

  if (!user) return <div>Loading...</div>;

  return (
    <Box sx={{ padding: 4 }}>
      <Typography variant="h4" gutterBottom>
        Profile
      </Typography>
      <Typography>Username: {user.username}</Typography>
      <Typography>Role: {user.role}</Typography>

      <TextField
        fullWidth
        label="New Password"
        margin="normal"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />
      <Button
        variant="contained"
        sx={{ marginTop: 2 }}
        onClick={handlePasswordChange}
      >
        Update Password
      </Button>
    </Box>
  );
};

export default Profile;

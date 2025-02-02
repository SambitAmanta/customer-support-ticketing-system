import React from "react";
import { Box, Typography } from "@mui/material";

const Custom404: React.FC = () => {
  return (
    <Box sx={{ textAlign: "center", padding: 4 }}>
      <Typography variant="h3" color="error" gutterBottom>
        404 - Page Not Found
      </Typography>
      <Typography>
        Sorry, the page you are looking for does not exist.
      </Typography>
    </Box>
  );
};

export default Custom404;

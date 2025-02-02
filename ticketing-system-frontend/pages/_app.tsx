import { AppProps } from "next/app";
import { CssBaseline } from "@mui/material"; // For Material-UI
import "../styles/globals.css"; // Global styles

const MyApp = ({ Component, pageProps }: AppProps) => {
  return (
    <>
      <CssBaseline /> {/* Resets browser styling */}
      <Component {...pageProps} />
    </>
  );
};

export default MyApp;

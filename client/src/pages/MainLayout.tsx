import React from "react";
import { Outlet } from "react-router-dom";
import { Box, Container, Typography, Button } from "@mui/material";
import ActionsPanel from "../components/ActionsPanel";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../slices/store";
import { useNavigate } from "react-router-dom";
import { logout } from "../slices/userSlice";
import { colors } from "../theme"; 
import LogoutIcon from "@mui/icons-material/Logout";
import IconButton from "@mui/material/IconButton";
import Tooltip from "@mui/material/Tooltip";

const MainLayout: React.FC = () => {
  const userName = useSelector((state: RootState) => state.user.userName);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const handleLogout = () => {
    dispatch(logout());
    navigate("/");
  };
  console.log("User Name:", userName);

  return (
    <Container maxWidth="lg" sx={{ mt: 4 }}>
      {/* Header */}
      <Box
        display="flex"
        justifyContent="space-between"
        alignItems="center"
        mb={4}
        px={2}
      >
        <Box display="flex" alignItems="center" gap={2} >
          <Tooltip title="התנתקות">
            <IconButton color="error" onClick={handleLogout}>
              <LogoutIcon />
            </IconButton>
          </Tooltip>
          <Typography variant="subtitle1" sx={{ color: colors.dark }}>שלום, {userName}</Typography>

        </Box>
      </Box>
      <Box display="flex" flexDirection={{ xs: "column", md: "row" }} gap={3} mb={3} alignItems="flex-start">
        <Box flex="0 0 220px" order={{ xs: 2, md: 3, mt: 4 }}>
          <ActionsPanel />
        </Box>
        <Box flex="2 1 0" order={{ xs: 1, md: 2 }} minWidth={0} width="100%">
          <Outlet />
        </Box>
      </Box>
    </Container>)
}

export default MainLayout;







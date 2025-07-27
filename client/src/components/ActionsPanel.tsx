import React from "react";
import {
  Paper,
  Typography,
  IconButton,
  Box,
} from "@mui/material";
import SwapHorizIcon from "@mui/icons-material/SwapHoriz";
import InsertChartIcon from "@mui/icons-material/InsertChart";
import TuneIcon from "@mui/icons-material/Tune";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import AddCardIcon from "@mui/icons-material/AddCard";
import HomeIcon from "@mui/icons-material/Home";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { colors } from "../theme"; // ודא שזה הנתיב הנכון

const actions = [
  { icon: <HomeIcon fontSize="large" />, label: "דף הבית", action: "home" },
  { icon: <SwapHorizIcon fontSize="large" />, label: "תנועות בחשבון", action: "transactions" },
  { icon: <AddCardIcon fontSize="large" />, label: "הוספת תנועה", action: "addMovement" },
  { icon: <TuneIcon fontSize="large" />, label: "הגדרת תנועות קבועות בחשבון", action: "settings" },
  { icon: <AccountCircleIcon fontSize="large" />, label: "עדכון פרטים אישיים", action: "updateUser" },
];

const ActionsPanel: React.FC = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const navigate = useNavigate();

  const handleActionClick = (actionType?: string) => {
    if (actionType === "addMovement") {
      navigate(`add`);
    } else if (actionType === "home") {
      if (userId) {
        navigate(`/home/${userId}`);
      }
    } else if (actionType === "transactions") {
      navigate(`table`);
    } else if (actionType === "settings") {
      navigate(`fixed`);
    } else if (actionType === "updateUser") {
      navigate(`update`);
    }
  };

  return (
    <Box dir="rtl" mt={3}>
      <Paper
        elevation={1}
        sx={{
          p: 3,
          borderRadius: 3,
          backgroundColor: "#fff",
        }}
      >
        

        <Box display="flex" flexWrap="wrap" justifyContent="center" gap={3} >
          {actions.map((action, index) => (
            <Box
              key={index}
              display="flex"
              flexDirection="column"
              alignItems="center"
              width={120}
            >
              <IconButton
                size="large"
                onClick={() => handleActionClick(action.action)}
                sx={{
                  border: `2px solid ${colors.accent}`,
                 // backgroundColor: colors.background,
                  color: colors.accent,
                  width: 60,
                  height: 60,
                  mb: 1,
                  borderRadius: "50%",
                  transition: "0.3s",
                  "&:hover": {
                    backgroundColor: colors.accent,
                    color: "#fff",
                  },
                }}
              >
                {action.icon}
              </IconButton>
              <Typography
                variant="body2"
                sx={{ textAlign: "center", color: colors.dark }}
              >
                {action.label}
              </Typography>
            </Box>
          ))}
        </Box>
      </Paper>
    </Box>
  );
};

export default ActionsPanel;

// import React, { useState } from "react";
// import { Paper, Typography, IconButton, Box } from "@mui/material";
// import SwapHorizIcon from "@mui/icons-material/SwapHoriz";
// import InsertChartIcon from "@mui/icons-material/InsertChart";
// import TuneIcon from "@mui/icons-material/Tune";
// import AccountCircleIcon from "@mui/icons-material/AccountCircle";
// import AddCardIcon from "@mui/icons-material/AddCard";
// import { useNavigate } from "react-router-dom";
// import HomeIcon from "@mui/icons-material/Home";
// import { useSelector } from "react-redux";
// import { RootState } from "../slices/store";

// const actions = [
//   { icon: <HomeIcon fontSize="large" />, label: "דף הבית", action: "home" },
//   { icon: <SwapHorizIcon fontSize="large" />, label: "תנועות בחשבון", action: "transactions" },
//   { icon: <AddCardIcon fontSize="large" />, label: "הוספת תנועה", action: "addMovement" },
//   { icon: <TuneIcon fontSize="large" />, label: "הגדרת תנועות בחשבון", action: "settings" },
//   { icon: <AccountCircleIcon fontSize="large" />, label: "עדכון פרטים אישיים", action: "updateUser"  },

// ];

// const ActionsPanel: React.FC = () => {
//   const userId = useSelector((state: RootState) => state.user.userId);

//   const navigate = useNavigate();


//  const handleActionClick = (actionType?: string) => {
//   if (actionType === "addMovement") {
//     navigate(`add`);
//   } else if (actionType === "home") {
//     if (userId) {
//       navigate(`/home/${userId}`);
//     }
//   } else if (actionType === "transactions") {
//           navigate(`table`);
//   }
//   else if (actionType === "settings") {
//   navigate(`fixed`);
// }
// else if (actionType === "updateUser") {
//   navigate(`update`);
//   }

// };



//   return (
//     <>
//       <Paper
//         sx={{
//           padding: 3,
//           borderRadius: 2,
//           boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
//           textAlign: "center",
//         }}
//       >
//         <Typography variant="h6" gutterBottom color="primary">
//           הקישורים המהירים שלי
//         </Typography>

//         <Box display="flex" flexWrap="wrap" justifyContent="center" gap={3}>
//           {actions.map((action, index) => (
//             <Box
//               key={index}
//               display="flex"
//               flexDirection="column"
//               alignItems="center"
//               width={120}
//             >
//               <IconButton
//                 size="large"
//                 color="primary"
//                 sx={{
//                   border: "1px solid #1976d2",
//                   width: 60,
//                   height: 60,
//                   mb: 1,
//                   "&:hover": {
//                     backgroundColor: "#e3f2fd",
//                   },
//                 }}
//                 onClick={() => handleActionClick(action.action)}
//               >
//                 {action.icon}
//               </IconButton>
//               <Typography variant="body2" sx={{ textAlign: "center" }}>
//                 {action.label}
//               </Typography>
//             </Box>
//           ))}
//         </Box>
//       </Paper>


//     </>
//   );
// };

// export default ActionsPanel;
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
  { icon: <TuneIcon fontSize="large" />, label: "הגדרת תנועות בחשבון", action: "settings" },
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
          //border: `1px solid ${colors.accent}`,
        }}
      >
        {/* <Typography
          variant="h6"
          align="center"
          gutterBottom
          sx={{ color: colors.highlight, fontWeight: "bold" }}
        >
          הקישורים המהירים שלי
        </Typography> */}

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

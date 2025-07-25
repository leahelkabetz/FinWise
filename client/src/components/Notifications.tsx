// import React, { useState } from "react";
// import {
//   Box,
//   Paper,
//   Typography,
//   List,
//   ListItem,
//   ListItemAvatar,
//   Avatar,
//   ListItemText,
//   IconButton,
//   Collapse,
// } from "@mui/material";
// import NotificationsIcon from "@mui/icons-material/Notifications";
// import DoneIcon from "@mui/icons-material/CheckCircle";
// import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
// import DevicesIcon from "@mui/icons-material/Devices";

// const Notifications: React.FC = () => {
//   const [open, setOpen] = useState(false);

//   const notifications = [
//     { text: "יתרתך עודכנה בהצלחה", icon: <DoneIcon color="success" /> },
//     { text: "התקבלה העברה בנקאית", icon: <ArrowDownwardIcon color="primary" /> },
//     { text: "התחברת ממכשיר חדש", icon: <DevicesIcon color="warning" /> },
//   ];

//   return (
//     <Box dir="rtl">
//       <Paper
//         sx={{
//           p: 2,
//           backgroundColor: "#f5f7fa",
//           borderRadius: 2,
//           boxShadow: "0 2px 6px rgba(0,0,0,0.1)",
//         }}
//       >
//         <Box
//           display="flex"
//           alignItems="center"
//           justifyContent="space-between"
//           onClick={() => setOpen(!open)}
//           sx={{ cursor: "pointer" }}
//         >
//           <Box display="flex" alignItems="center" gap={1}>
//             <NotificationsIcon color="primary" sx={{direction:"rtl"}} />
//             <Typography variant="subtitle1" color="primary">
//               ההתראות שלך
//             </Typography>
//           </Box>
//         </Box>

//         <Collapse in={open}>
//           <List>
//             {notifications.map((note, index) => (
//               <ListItem key={index} divider>
//                 <ListItemAvatar>
//                   <Avatar sx={{ bgcolor: "white" }}>{note.icon}</Avatar>
//                 </ListItemAvatar>
//                 <ListItemText primary={note.text} />
//               </ListItem>
//             ))}
//           </List>
//         </Collapse>
//       </Paper>
//     </Box>
//   );
// };

// export default Notifications;


import React, { useState } from "react";
import {
  Box,
  Paper,
  Typography,
  List,
  ListItem,
  ListItemAvatar,
  Avatar,
  ListItemText,
  Collapse,
} from "@mui/material";
import NotificationsIcon from "@mui/icons-material/Notifications";
import DoneIcon from "@mui/icons-material/CheckCircle";
import ArrowDownwardIcon from "@mui/icons-material/ArrowDownward";
import DevicesIcon from "@mui/icons-material/Devices";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";
import ExpandLessIcon from "@mui/icons-material/ExpandLess";
import { colors } from "../theme"; // ודא שזה הנתיב הנכון

const Notifications: React.FC = () => {
  const [open, setOpen] = useState(false);

  const notifications = [
    { text: "יתרתך עודכנה בהצלחה", icon: <DoneIcon color="success" /> },
    { text: "התקבלה העברה בנקאית", icon: <ArrowDownwardIcon color="primary" /> },
    { text: "התחברת ממכשיר חדש", icon: <DevicesIcon color="warning" /> },
  ];

  return (
    <Box dir="rtl">
      <Paper
        elevation={0}
        sx={{
          p: 2,
          backgroundColor: "#fff",
          borderRadius: 2,
        }}
      >
        <Box
          display="flex"
          alignItems="center"
          justifyContent="space-between"
          onClick={() => setOpen(!open)}
          sx={{
            cursor: "pointer",
            mb: 1,
          }}
        >
          <Box display="flex" alignItems="center" gap={1}>
            <NotificationsIcon sx={{ color: colors.accent }} />
            <Typography
              variant="subtitle1"
              sx={{ color: colors.accent, fontWeight: "bold" }}
            >
              ההתראות שלך
            </Typography>
          </Box>

          {open ? (
            <ExpandLessIcon sx={{ color: colors.accent }} />
          ) : (
            <ExpandMoreIcon sx={{ color: colors.accent }} />
          )}
        </Box>

        <Collapse in={open}>
          <List dense>
            {notifications.map((note, index) => (
              <ListItem key={index} divider sx={{ alignItems: "flex-start" }}>
                <ListItemAvatar>
                  <Avatar
                    sx={{
                      bgcolor: "#f0f3bd",
                      width: 36,
                      height: 36,
                      color: colors.dark,
                    }}
                  >
                    {note.icon}
                  </Avatar>
                </ListItemAvatar>
                <ListItemText
                  primary={note.text}
                  primaryTypographyProps={{
                    sx: { color: colors.dark, fontSize: "0.875rem" },
                  }}
                />
              </ListItem>
            ))}
          </List>
        </Collapse>
      </Paper>
    </Box>
  );
};

export default Notifications;

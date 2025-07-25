import React, { useEffect, useState } from "react";
import { Box, Typography } from "@mui/material";
import TableTransactions from "../components/TableTransactions";
import Notifications from "../components/Notifications";
import axios from "axios";
import { useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { colors } from "../theme";
const HomePage: React.FC = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const [balance, setBalance] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBalance = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/balance/byuser/${userId}`);
        setBalance(res.data.totalBalance);
      } catch (err) {
        console.error("Failed to fetch balance", err);
      } finally {
        setLoading(false);
      }
    };

    if (userId) {
      fetchBalance();
    }
  }, [userId]);

  const now = new Date();
  const startOfMonth = new Date(now.getFullYear(), now.getMonth(), 1);
  const startDate = startOfMonth.toISOString().slice(0, 10);
  const endDate = now.toISOString().slice(0, 10);

  return (

    <Box sx={{ mt: 0, direction: "rtl", px: 1 }}> {/* px מוקטן */}
    <Box
  sx={{
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    mb: 4,
    mt: 4,
  }}
>
  <Typography
    variant="h4"
    sx={{
      fontWeight: "bold",
      color: colors.highlight,
      textAlign: "center",
      animation: "pulseShadow 2.5s infinite ease-in-out",
    }}
  >
    שליטה כלכלית מתחילה כאן 💡
  </Typography>

  {/* אנימציה מתמשכת ב־CSS */}
  <style>
    {`
      @keyframes pulseShadow {
        0% {
          text-shadow: 0 0 0px ${colors.highlight};
        }
        50% {
          text-shadow: 0 0 12px ${colors.highlight};
        }
        100% {
          text-shadow: 0 0 0px ${colors.highlight};
        }
      }
    `}
  </style>
</Box>

      <Box
        sx={{
          mb: 4,
          mt:4
        }}
      >
        <Typography
          variant="h5"
          sx={{
            color: colors.accent,
            fontWeight: "bold",
            unicodeBidi: "isolate",
            mt: 4
          }}
        >
          {loading
            ? "טוען יתרה..."
            : balance !== null
              ? `יתרה נוכחית: ${balance.toLocaleString("he-IL", {
                style: "currency",
                currency: "ILS",
                currencyDisplay: "narrowSymbol",
              })}`
              : "לא נמצאה יתרה"}
        </Typography>
      </Box>

      {/* תוכן: קישורים + טבלה */}
      <Box
        display="flex"
        flexDirection={{ xs: "column", md: "row", }}
        sx={{ mt: 4 }}
        gap={2} // מעט ריווח
        alignItems="flex-start"
      >
        {/* טבלה */}
        <Box sx={{ flexGrow: 1 }}>
          <TableTransactions
            start={startDate}
            end={endDate}
            title="תנועות אחרונות בחשבון"
          />
        </Box>

        <Box
          sx={{
            width: { xs: "100%", md: 250 }, // לדוגמה, רוחב קבוע רק במסך גדול
            flexShrink: 0,
            mt: -6,
            mr:6
          }}
        >
          <Notifications />
        </Box>
      </Box>
    </Box>

  );
};

export default HomePage;

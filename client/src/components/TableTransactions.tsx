import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Table, TableHead, TableRow, TableCell, TableBody,
  Paper, Typography, Box
} from "@mui/material";
import { styled } from "@mui/material/styles";
import { useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { colors } from "../theme";

interface TransactionDTO {
  transactionId: number;
  date: string;
  description: string;
  amount: number;
  categoryName: string;
  categoryTypeLabel: string;
  isFixed: boolean;
  balance: number;
}


interface TableTransactionsProps {
  start: string;
  end: string;
  title?: string; // כותרת דינאמית
}

const StyledTableRow = styled(TableRow)(({ theme }) => ({
  "&:nth-of-type(odd)": { backgroundColor: theme.palette.action.hover },
  "&:hover": { backgroundColor: theme.palette.action.selected },
}));

const TableTransactions: React.FC<TableTransactionsProps> = ({ start, end, title }) => {
  const [transactions, setTransactions] = useState<TransactionDTO[]>([]);
  const [balance, setBalance] = useState<number | null>(null);
  const userId = useSelector((state: RootState) => state.user.userId);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const res = await axios.get("http://localhost:8080/transaction/get-range", {
          params: { userId, start, end },
        });
        setTransactions(res.data);
      } catch (err) {
        console.error("שגיאה בשליפת תנועות:", err);
      }
    };

    const fetchBalance = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/balance/byuser/${userId}`);
        setBalance(res.data.totalBalance);
      } catch (err) {
        console.error("שגיאה בשליפת יתרה:", err);
      }
    };

    if (typeof userId === "number" && !isNaN(userId)) {
      fetchTransactions();
      fetchBalance();
    }
  }, [userId, start, end]);

  return (
    <Paper
      elevation={3}
      sx={{
        p: 3,
        backgroundColor: "#f9fafc",
        borderRadius: 2,
        direction: "rtl",
        height: "60vh", 
        width: "100%",   
        display: "flex",
        flexDirection: "column",
      }}
    >
      <Typography variant="h5" gutterBottom sx={{ color: colors.dark }}>
        {title}
      </Typography>

      <Box sx={{ flex: 1, overflow: "auto" }}>
        <Table stickyHeader>
          <TableHead>
            <TableRow>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>תאריך</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>קטגוריה</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>סוג</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>תיאור</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>סכום (₪)</TableCell>
              <TableCell align="right" sx={{ fontWeight: "bold" }}>יתרה (₪)</TableCell>
            </TableRow>
          </TableHead>

          <TableBody>
            {transactions.map((t, index) => {
              const isIncome = t.categoryTypeLabel === "הכנסה";
              return (
                <StyledTableRow key={index}>
                  <TableCell align="right">{new Date(t.date).toLocaleDateString("he-IL")}</TableCell>
                  <TableCell align="right">{t.categoryName}</TableCell>
                  <TableCell
                    align="right"
                    sx={{ color: isIncome ? "success.main" : "error.main", fontWeight: "bold" }}
                  >
                    {t.categoryTypeLabel}
                  </TableCell>
                  <TableCell align="right">{t.description}</TableCell>
                  <TableCell align="right">
                    {t.amount.toLocaleString("he-IL", {
                      style: "currency",
                      currency: "ILS",
                      minimumFractionDigits: 2,
                    })}
                  </TableCell>
                  <TableCell
                    align="right"
                    sx={{
                      color: t.balance < 0 ? "error.main" : "success.main",
                      fontWeight: "bold",
                    }}
                  >
                    {t.balance !== undefined
                      ? t.balance.toLocaleString("he-IL", {
                        style: "currency",
                        currency: "ILS",
                        minimumFractionDigits: 2,
                      })
                      : "-"}
                  </TableCell>
                </StyledTableRow>
              );
            })}
          </TableBody>
        </Table>
      </Box>

      {transactions.length === 0 && (
        <Box mt={2}>
          <Typography color="text.secondary">אין תנועות להצגה.</Typography>
        </Box>
      )}

      {balance !== null && (
        <Box mt={2}>
          <Typography
            variant="subtitle1"
            sx={{ fontWeight: "bold", color: colors.dark, textAlign: "left" }}
          >
            סה"כ:{" "}
            {balance.toLocaleString("he-IL", {
              style: "currency",
              currency: "ILS",
              minimumFractionDigits: 2,
            })}
          </Typography>
        </Box>
      )}
    </Paper>

  );
};

export default TableTransactions;

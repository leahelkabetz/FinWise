import React, { useEffect, useState } from "react";
import {
  Paper, Table, TableHead, TableRow, TableCell, TableBody,
  Typography, IconButton, TextField
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import SaveIcon from "@mui/icons-material/Save";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { colors } from "../theme";
import { showMessage } from "../slices/messageSlice"; 

interface TransactionDTO {
  transactionId: number;
  date: string;
  description: string;
  amount: number;
  categoryName: string;
  categoryTypeLabel: string;
  balance: number;
  isFixed: boolean;
}

const RecurringTransactionsSettings: React.FC = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const [transactions, setTransactions] = useState<TransactionDTO[]>([]);
  const [editingId, setEditingId] = useState<number | null>(null);
  const [editedTransaction, setEditedTransaction] = useState<Partial<TransactionDTO>>({});
const dispatch = useDispatch();

  useEffect(() => {
    const fetchRecurring = async () => {
      try {
        const res = await axios.get("http://localhost:8080/transaction/recurring", {
          params: { userId },
        });
        console.log("📦 תנועות קבועות:", res.data);
        setTransactions(res.data);
      } catch (err) {
        console.error("שגיאה בשליפת תנועות קבועות", err);
      }
    };
    if (userId) fetchRecurring();
  }, [userId]);

  const handleEdit = (transaction: TransactionDTO) => {
    setEditingId(transaction.transactionId);
    setEditedTransaction({ ...transaction });
  };

  const handleSave = async () => {
    try {
      await axios.put(`http://localhost:8080/transaction/update/${editingId}`, editedTransaction, {
        params: { userId }
      });
      setEditingId(null);
      const updated = transactions.map(t =>
        t.transactionId === editingId ? { ...t, ...editedTransaction } as TransactionDTO : t
      );
      setTransactions(updated);
      dispatch(showMessage({ message: "העדכון בוצע בהצלחה", severity: "success" }));
      
    } catch (err) {
      console.error("שגיאה בשמירה", err);
      dispatch(showMessage({ message: "שגיאה בעדכון התנועה ", severity: "error" }));
      
    }
  };

  const handleDelete = async (id: number) => {
    try {
      await axios.delete(`http://localhost:8080/transaction/delete/${id}`);
      setTransactions(transactions.filter(t => t.transactionId !== id));
             dispatch(showMessage({ message: "התנועה נמחקה בהצלחה", severity: "success" }));
    } catch (err) {
      console.error("שגיאה במחיקה", err);
      dispatch(showMessage({ message: "שגיאה במחיקת התנועה ", severity: "error" }));

    }
  };

  return (
    <Paper sx={{ p: 3, direction: "rtl", borderRadius: 2,mt:4}}>
    

      <Table>
        <TableHead>
          <TableRow>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>שם קטגוריה</TableCell>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>סוג קטגוריה</TableCell>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>תיאור</TableCell>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>סכום</TableCell>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>תאריך גבייה</TableCell>
            <TableCell align="right" sx={{color:colors.dark, fontWeight:'bold'}}>פעולות</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {transactions.map(t => (
            <TableRow key={t.transactionId}>
              <TableCell align="right">{t.categoryName}</TableCell>
              <TableCell align="right">{t.categoryTypeLabel}</TableCell>
              <TableCell align="right">
                {editingId === t.transactionId ? (
                  <TextField
                    variant="standard"
                    value={editedTransaction.description}
                    onChange={(e) => setEditedTransaction(prev => ({ ...prev, description: e.target.value }))}
                  />
                ) : (
                  t.description
                )}
              </TableCell>
              <TableCell align="right">
                {editingId === t.transactionId ? (
                  <TextField
                    variant="standard"
                    type="number"
                    value={editedTransaction.amount}
                    onChange={(e) => setEditedTransaction(prev => ({ ...prev, amount: +e.target.value }))}
                  />
                ) : (
                  t.amount.toLocaleString("he-IL", { style: "currency", currency: "ILS" })
                )}
              </TableCell>
              <TableCell align="right">
                {editingId === t.transactionId ? (
                  <TextField
                    variant="standard"
                    type="date"
                    value={editedTransaction.date}
                    onChange={(e) => setEditedTransaction(prev => ({ ...prev, date: e.target.value }))}
                  />
                ) : (
                  new Date(t.date).toLocaleDateString("he-IL")
                )}
              </TableCell>
              <TableCell align="right">
                {editingId === t.transactionId ? (
                  <IconButton onClick={handleSave}><SaveIcon /></IconButton>
                ) : (
                  <IconButton onClick={() => handleEdit(t)}><EditIcon /></IconButton>
                )}
                <IconButton color="error" onClick={() => handleDelete(t.transactionId)}>
                  <DeleteIcon />
                </IconButton>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </Paper>
  );
};

export default RecurringTransactionsSettings;

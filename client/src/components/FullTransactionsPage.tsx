import React, { useEffect, useState } from "react";
import {
  Box,
  Typography,
  TextField,
  Button,
} from "@mui/material";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";
import TableTransactions from "../components/TableTransactions";
import axios from "axios";
import { useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { colors } from "../theme"; 

const FullTransactionsPage: React.FC = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const [startDate, setStartDate] = useState("2000-01-01");
  const [endDate, setEndDate] = useState(new Date().toISOString().slice(0, 10));

  const handleExportPdf = async () => {
    try {
      const response = await axios.get("http://localhost:8080/report/pdf", {
        params: {
          userId,
          startDate,
          endDate,
        },
        responseType: "blob",
      });

      const blob = new Blob([response.data], { type: "application/pdf" });
      const url = window.URL.createObjectURL(blob);
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", `דוח_${startDate}_עד_${endDate}.pdf`);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("שגיאה ביצוא הדוח ל־PDF:", error);
    }
  };

  return (
    <Box sx={{ p: 4, direction: "rtl" }}>

      <Box
        sx={{
          display: "flex",
          gap: 2,
          mb: 4,
          flexWrap: "wrap",
          alignItems: "center",
        }}
      >
        {[["מתאריך", startDate, setStartDate], ["עד תאריך", endDate, setEndDate]].map(
          ([label, value, setter], idx) => (
            <TextField
              key={idx}
              type="date"
              label={label as string}
              value={value as string}
              onChange={(e) => (setter as React.Dispatch<React.SetStateAction<string>>)(e.target.value)}
              InputLabelProps={{
                shrink: true,
                sx: {
                  right: 0,
                  left: "unset",
                  color: colors.accent,
                  "&.Mui-focused": { color: colors.accent },
                },
              }}
              variant="standard"
              sx={{
                minWidth: 180,
                "& .MuiInput-underline:before": {
                  borderBottomColor: colors.accent,
                },
                "& .MuiInput-underline:after": {
                  borderBottomColor: colors.accent,
                },
                "& .MuiInput-underline:hover:not(.Mui-disabled):before": {
                  borderBottomColor: colors.accent,
                },
              }}
            />
          )
        )}

        <Button
          variant="contained"
          onClick={handleExportPdf}
          sx={{
            backgroundColor: colors.accent,
            mr:35,
            color: "white",
            "&:hover": {
              backgroundColor: colors.dark || colors.accent,
            },

          }}
          startIcon={<PictureAsPdfIcon sx={{ml:1,mr:0}} /> }
        >
          ייצוא ל־PDF
        </Button>
      </Box>

      <TableTransactions start={startDate} end={endDate} />
    </Box>
  );
};

export default FullTransactionsPage;

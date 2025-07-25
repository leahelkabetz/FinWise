import React, { useEffect, useState } from "react";
import axios from "axios";
import {
  Paper,
  Box,
  Typography,
  TextField,
  Button,
  MenuItem,
} from "@mui/material";
import { useSelector } from "react-redux";
import { RootState } from "../slices/store";
import { useFormik } from "formik";
import * as Yup from "yup";
import { colors } from "../theme";

interface Category {
  categoryId: number;
  name: string;
  type: string;
}

interface TransactionFormData {
  description: string;
  amount: string;
  date: string;
  fixed: boolean;
  categoryId: string;
}

const AddTransactionForm: React.FC = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const [categories, setCategories] = useState<Category[]>([]);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const res = await axios.get("http://localhost:8080/categories/get");
        setCategories(res.data);
      } catch (err) {
        console.error("שגיאה בשליפת קטגוריות:", err);
      }
    };

    fetchCategories();
  }, []);

  const turquoiseTextFieldStyle = {
    variant: "standard" as const,
    fullWidth: true,
    InputLabelProps: {
      sx: {
        color: colors.accent,
        "&.Mui-focused": { color: colors.accent },
        right: 0,
        left: "unset",
      },
      shrink: true,
    },
    sx: {
      "& .MuiInput-underline:before": {
        borderBottomColor: colors.accent,
      },
      "& .MuiInput-underline:after": {
        borderBottomColor: colors.accent,
      },
      "& .MuiInput-underline:hover:not(.Mui-disabled):before": {
        borderBottomColor: colors.accent,
      },
    },
  };

  const formik = useFormik<TransactionFormData>({
    initialValues: {
      description: "",
      amount: "",
      date: new Date().toISOString().slice(0, 10),
      fixed: false,
      categoryId: "",
    },
    validationSchema: Yup.object({
      description: Yup.string().when("fixed", {
        is: false,
        then: (schema) => schema.required("שדה חובה"),
        otherwise: (schema) => schema,
      }),
      amount: Yup.number()
        .typeError("סכום חייב להיות מספר")
        .positive("סכום חייב להיות חיובי")
        .required("שדה חובה"),
      date: Yup.string().required("שדה חובה"),
      fixed: Yup.boolean().required("שדה חובה"),
      categoryId: Yup.string().required("שדה חובה"),
    }),
    onSubmit: async (values, { resetForm }) => {
      try {
        const res = await axios.post(
          "http://localhost:8080/transaction/add",
          {
            ...values,
            description: values.description || "",
          },
          { params: { userId } }
        );
        console.log("נשלח בהצלחה:", res.data);
        resetForm();
      } catch (error) {
        console.error("שגיאה:", error);
      }
    },
  });

  return (
    <Box display="flex" justifyContent="center" alignItems="center" minHeight="100vh" dir="rtl">
      <Paper elevation={0} sx={{ p: 4, width: 500, borderRadius: 3 }}>
        <Typography variant="h5" align="center" gutterBottom sx={{ color: colors.highlight }}>
          הוספת תנועה חדשה
        </Typography>

        <Box
          component="form"
          onSubmit={formik.handleSubmit}
          display="flex"
          flexDirection="column"
          gap={3}
        >
          <TextField
            {...turquoiseTextFieldStyle}
            select
            label="האם קבועה?"
            name="fixed"
            value={formik.values.fixed ? "true" : "false"}
            onChange={(e) => formik.setFieldValue("fixed", e.target.value === "true")}
          >
            <MenuItem value="false">חד פעמית</MenuItem>
            <MenuItem value="true">קבועה</MenuItem>
          </TextField>
          <TextField
            {...turquoiseTextFieldStyle}
            label="תיאור"
            name="description"
            value={formik.values.description}
            onChange={formik.handleChange}
            error={formik.touched.description && Boolean(formik.errors.description)}
            helperText={formik.touched.description && formik.errors.description}
          />

          <TextField
            {...turquoiseTextFieldStyle}
            label="סכום"
            name="amount"
            type="number"
            value={formik.values.amount}
            onChange={formik.handleChange}
            error={formik.touched.amount && Boolean(formik.errors.amount)}
            helperText={formik.touched.amount && formik.errors.amount}
          />

          <TextField
            {...turquoiseTextFieldStyle}
            label="תאריך"
            name="date"
            type="date"
            value={formik.values.date}
            onChange={formik.handleChange}
            error={formik.touched.date && Boolean(formik.errors.date)}
            helperText={formik.touched.date && formik.errors.date}
          />

          

          <TextField
            {...turquoiseTextFieldStyle}
            select
            label="קטגוריה"
            name="categoryId"
            value={formik.values.categoryId}
            onChange={formik.handleChange}
            error={formik.touched.categoryId && Boolean(formik.errors.categoryId)}
            helperText={formik.touched.categoryId && formik.errors.categoryId}
          >
            <MenuItem disabled sx={{ fontWeight: "bold", color: "gray" }}>
              הכנסות
            </MenuItem>
            {categories
              .filter((cat) => cat.type === "INCOME")
              .map((cat) => (
                <MenuItem key={cat.categoryId} value={cat.categoryId}>
                  {cat.name}
                </MenuItem>
              ))}

            <MenuItem disabled sx={{ fontWeight: "bold", color: "gray", mt: 1 }}>
              הוצאות
            </MenuItem>
            {categories
              .filter((cat) => cat.type === "EXPENSE")
              .map((cat) => (
                <MenuItem key={cat.categoryId} value={cat.categoryId}>
                  {cat.name}
                </MenuItem>
              ))}
          </TextField>

          <Button
            type="submit"
            variant="contained"
            fullWidth
            sx={{
              backgroundColor: colors.accent,
              color: "white",
              "&:hover": {
                backgroundColor: colors.dark || colors.accent,
              },
            }}
          >
            הוסף
          </Button>
        </Box>
      </Paper>
    </Box>
  );
};

export default AddTransactionForm;

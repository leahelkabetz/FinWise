import { useDispatch, useSelector } from "react-redux";
import { useEffect, useState } from "react";
import axios from "axios";
import {
  Paper,
  Box,
  Typography,
  TextField,
  Button,
} from "@mui/material";
import { useFormik } from "formik";
import * as Yup from "yup";
import { RootState } from "../slices/store";
import { colors } from "../theme";
import { showMessage } from "../slices/messageSlice"; 

interface User {
  userId: number;
  userName: string;
  email: string;
  phoneNumber: string;
  password: string;
}

const UpdateUser = () => {
  const userId = useSelector((state: RootState) => state.user.userId);
  const dispatch = useDispatch();

  const [initialValues, setInitialValues] = useState<User>({
    userId: 0,
    userName: "",
    email: "",
    phoneNumber: "",
    password: "",
  });

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const res = await axios.get(`http://localhost:8080/user/${userId}`);
        setInitialValues(res.data);
      } catch (err) {
        console.error("שגיאה בשליפת משתמש:", err);
      }
    };

    if (userId) fetchUser();
  }, [userId]);

  const commonTextFieldStyles = {
    variant: "standard" as const,
    fullWidth: true,
    InputLabelProps: {
      sx: {
        color: colors.accent,
        "&.Mui-focused": { color: colors.accent },
        right: 0,
        left: "unset",
      },
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

  const formik = useFormik<User>({
    enableReinitialize: true,
    initialValues,
    validationSchema: Yup.object({
      userName: Yup.string().required("שדה חובה"),
      email: Yup.string().email("אימייל לא תקין").required("שדה חובה"),
      phoneNumber: Yup.string().required("שדה חובה"),
      password: Yup.string().required("שדה חובה"),
    }),
    onSubmit: async (values) => {
      try {
        await axios.put(`http://localhost:8080/user/update/${userId}`, values);
       dispatch(showMessage({ message: "המשתמש עודכן בהצלחה", severity: "success" }));
      } catch (error) {
        alert("שגיאה בעדכון המשתמש");
        console.error(error);
      }
    },
  });

  return (
<Box
  display="flex"
  justifyContent="center"
  alignItems="flex-start" 
  sx={{ mt: 8, p: 4 }} 
  dir="rtl"
>
      <Paper elevation={0} sx={{ p: 4, width: 500, borderRadius: 3, }}>
        <Typography variant="h5" align="center" gutterBottom sx={{ color: colors.highlight }}>
          עריכת פרטי משתמש
        </Typography>

        <Box
          component="form"
          onSubmit={formik.handleSubmit}
          display="flex"
          flexDirection="column"
          gap={3}
        >
          <TextField
            {...commonTextFieldStyles}
            label="שם משתמש"
            name="userName"
            value={formik.values.userName}
            onChange={formik.handleChange}
            error={formik.touched.userName && Boolean(formik.errors.userName)}
            helperText={formik.touched.userName && formik.errors.userName}
          />

          <TextField
            {...commonTextFieldStyles}
            label="אימייל"
            name="email"
            value={formik.values.email}
            onChange={formik.handleChange}
            error={formik.touched.email && Boolean(formik.errors.email)}
            helperText={formik.touched.email && formik.errors.email}
          />

          <TextField
            {...commonTextFieldStyles}
            label="טלפון"
            name="phoneNumber"
            value={formik.values.phoneNumber}
            onChange={formik.handleChange}
            error={formik.touched.phoneNumber && Boolean(formik.errors.phoneNumber)}
            helperText={formik.touched.phoneNumber && formik.errors.phoneNumber}
          />

          <TextField
            {...commonTextFieldStyles}
            label="סיסמה"
            name="password"
            type="password"
            value={formik.values.password}
            onChange={formik.handleChange}
            error={formik.touched.password && Boolean(formik.errors.password)}
            helperText={formik.touched.password && formik.errors.password}
          />

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
            שמור שינויים
          </Button>
        </Box>
      </Paper>
    </Box>
  );
};

export default UpdateUser;

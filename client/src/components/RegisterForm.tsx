import React from "react";
import { Formik, Form } from "formik";
import * as Yup from "yup";
import {
  Button,
  TextField,
  Box,
  Typography,
  Paper,
} from "@mui/material";
import { colors } from "../theme"; 

interface RegisterFormProps {
  onSubmit: (values: {
    email: string;
    userName: string;
    userNumber: string;
    phoneNumber: string;
    password: string;
    confirmPassword: string;
  }) => void;
}

const RegisterForm: React.FC<RegisterFormProps> = ({ onSubmit }) => {
  const validationSchema = Yup.object({
    userName: Yup.string().required("שדה חובה"),
    email: Yup.string().email("כתובת מייל לא חוקית").required("שדה חובה"),
    userNumber: Yup.string().required("שדה חובה"),
    phoneNumber: Yup.string()
      .matches(/^[0-9]{9,10}$/, "מספר טלפון לא חוקי")
      .required("שדה חובה"),
    password: Yup.string().min(6, "לפחות 6 תווים").required("שדה חובה"),
    confirmPassword: Yup.string()
      .oneOf([Yup.ref("password")], "הסיסמאות אינן תואמות")
      .required("שדה חובה"),
  });

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      dir="rtl"

    >
      <Paper elevation={0} sx={{ p: 4, width: 400 }}>
        <Formik
          initialValues={{
            userName: "",
            email: "",
            userNumber: "",
            phoneNumber: "",
            password: "",
            confirmPassword: "",
          }}
          validationSchema={validationSchema}
          onSubmit={(values, { setSubmitting }) => {
            onSubmit(values);
            setSubmitting(false);
          }}
        >
          {({ errors, touched, isSubmitting, handleChange, values }) => (
            <Form>
              <Typography variant="h5" align="center" gutterBottom sx={{ color: colors.dark }}>
                הרשמה למערכת
              </Typography>
              {[
                { label: "שם משתמש", name: "userName", type: "text" },
                { label: "מספר זהות", name: "userNumber", type: "text" },
                { label: "טלפון", name: "phoneNumber", type: "text" },
                { label: "כתובת מייל", name: "email", type: "email" },
                { label: "סיסמה", name: "password", type: "password" },
                { label: "אימות סיסמה", name: "confirmPassword", type: "password" },
              ].map(({ label, name, type }) => (
                <Box mb={3} key={name}>
                  <TextField
                    variant="standard"
                    label={label}
                    name={name}
                    type={type}
                    value={(values as any)[name]}
                    onChange={handleChange}
                    error={Boolean((touched as any)[name] && (errors as any)[name])}
                    helperText={(touched as any)[name] && (errors as any)[name]}
                    fullWidth
                    inputProps={{ dir: "rtl" }}
                    InputLabelProps={{
                      sx: {
                        right: 0,
                        left: "unset",
                        color: colors.accent,
                        "&.Mui-focused": {
                          color: colors.accent,
                        },
                      },
                    }}
                    sx={{
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
                </Box>
              ))}

              {/* כפתור */}
              <Box mt={3}>
                <Button
                  type="submit"
                  variant="contained"
                  fullWidth
                  disabled={isSubmitting}
                  sx={{
                    backgroundColor: colors.dark,
                    "&:hover": {
                      backgroundColor: colors.highlight,
                    },
                    color: "#fff",
                    fontWeight: "bold",
                    borderRadius: 8,
                  }}
                >
                  הרשמה
                </Button>
              </Box>
            </Form>
          )}
        </Formik>
      </Paper>
    </Box>
  );
};

export default RegisterForm;

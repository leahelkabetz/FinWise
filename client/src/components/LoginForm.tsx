import React, { useState } from "react";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import IconButton from "@mui/material/IconButton";
import { Formik, Form } from "formik";
import * as Yup from "yup";
import {
  Button,
  TextField,
  Box,
  Typography,
  Paper,
  FormControlLabel,
  Checkbox,
} from "@mui/material";
import { colors } from "../theme";

interface Props {
  onSubmit: (values: { email: string; password: string }) => void;
  onOpenRegister: () => void;
}

const LoginForm: React.FC<Props> = ({ onSubmit, onOpenRegister }) => {
  const [showPassword, setShowPassword] = useState(false);
  const togglePasswordVisibility = () => setShowPassword((prev) => !prev);

  const validationSchema = Yup.object({
    email: Yup.string().email("כתובת מייל לא חוקית").required("שדה חובה"),
    password: Yup.string().min(6, "לפחות 6 תווים").required("שדה חובה"),
  });

  return (
    <Box
      display="flex"
      justifyContent="center"
      alignItems="center"
      minHeight="100vh"
      dir="rtl"
    >
      <Paper elevation={0} sx={{ p: 4, width: 400 }}>
        <Formik
          initialValues={{ email: "", password: "", agree: false }}
          validationSchema={validationSchema}
          onSubmit={(values, { setSubmitting }) => {
            onSubmit(values);
            setSubmitting(false);
          }}
        >
          {({ errors, touched, isSubmitting, handleChange, values }) => (
            <Form>
              <Typography variant="h5" align="center" gutterBottom>
                התחברות לחשבון
              </Typography>

              <Box mb={3}>
                <TextField
                  variant="standard"
                  label="כתובת מייל"
                  name="email"
                  value={values.email}
                  onChange={handleChange}
                  error={touched.email && Boolean(errors.email)}
                  helperText={touched.email && errors.email}
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

              <Box mb={3}>
                <TextField
                  variant="standard"
                  label="סיסמה"
                  name="password"
                  type={showPassword ? "text" : "password"}
                  value={values.password}
                  onChange={handleChange}
                  error={touched.password && Boolean(errors.password)}
                  helperText={touched.password && errors.password}
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
                  InputProps={{
                    endAdornment: (
                      <IconButton onClick={togglePasswordVisibility}>
                        {showPassword ? <VisibilityOff /> : <Visibility />}
                      </IconButton>
                    ),
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


              <Box mt={3} mb={1}>
                <Button
                  type="submit"
                  variant="contained"
                  sx={{ backgroundColor: colors.accent, "&:hover": { backgroundColor: colors.dark || colors.accent } }}
                  fullWidth
                  disabled={isSubmitting}
                >
                  התחבר
                </Button>
              </Box>

              <Box display="flex" justifyContent="center" mt={2}>
                <Typography variant="body2">
                  אין לך חשבון?{" "}
                  <Button
                    onClick={onOpenRegister}
                    variant="text"
                    sx={{
                      p: 0,
                      m: 0,
                      minWidth: "unset",
                      textDecoration: "underline",
                      color: colors.highlight,
                      fontWeight: "bold",
                      fontSize: "0.875rem",
                      "&:hover": {
                        backgroundColor: "transparent",
                      },
                    }}
                  >
                    הרשמה
                  </Button>
                </Typography>
              </Box>
            </Form>
          )}
        </Formik>
      </Paper>
    </Box>
  );
};

export default LoginForm;

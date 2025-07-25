// import React from "react";
// import { Formik, Form } from "formik";
// import * as Yup from "yup";
// import { Button, TextField, Box } from "@mui/material";

// interface RegisterFormProps {
//   onSubmit: (values: {
//     email: string;
//     userName: string;
//     userNumber: string;
//     phoneNumber: string;
//     password: string;
//     confirmPassword: string;
//   }) => void;
// }

// const RegisterForm: React.FC<RegisterFormProps> = ({ onSubmit }) => {
//   const validationSchema = Yup.object({
//     userName: Yup.string().required("שדה חובה"),
//     email: Yup.string().email("כתובת מייל לא חוקית").required("שדה חובה"),
//     userNumber: Yup.string().required("שדה חובה"),
//     phoneNumber: Yup.string()
//       .matches(/^[0-9]{9,10}$/, "מספר טלפון לא חוקי")
//       .required("שדה חובה"),
//     password: Yup.string().min(6, "לפחות 6 תווים").required("שדה חובה"),
//     confirmPassword: Yup.string()
//       .oneOf([Yup.ref("password")], "הסיסמאות אינן תואמות")
//       .required("שדה חובה"),
//   });

//   return (
//     <Formik
//       initialValues={{
//         userName: "",
//         email: "",
//         userNumber: "",
//         phoneNumber: "",
//         password: "",
//         confirmPassword: "",
//       }}
//       validationSchema={validationSchema}
//       onSubmit={(values, { setSubmitting }) => {
//         onSubmit(values);
//         setSubmitting(false);
//       }}
//     >
//       {({ errors, touched, isSubmitting, handleChange, values }) => (
//         <Form>
//           <Box mb={2}>
//             <Box mb={2}>
//               <TextField
//                 label="User Name"
//                 name="userName"
//                 type="text"
//                 value={values.userName}
//                 onChange={handleChange}
//                 error={touched.userName && Boolean(errors.userName)}
//                 helperText={touched.userName && errors.userName}
//                 fullWidth
//               />
//             </Box>
//             <Box mb={2}>
//               <TextField
//                 label="User Number"
//                 name="userNumber"
//                 type="text"
//                 value={values.userNumber}
//                 onChange={handleChange}
//                 error={touched.userNumber && Boolean(errors.userNumber)}
//                 helperText={touched.userNumber && errors.userNumber}
//                 fullWidth
//               />
//             </Box>
//             <Box mb={2}>
//               <TextField
//                 label="Phone Number"
//                 name="phoneNumber"
//                 type="text"
//                 value={values.phoneNumber}
//                 onChange={handleChange}
//                 error={touched.phoneNumber && Boolean(errors.phoneNumber)}
//                 helperText={touched.phoneNumber && errors.phoneNumber}
//                 fullWidth
//               />
//             </Box>
//             <TextField
//               label="Email"
//               name="email"
//               type="email"
//               value={values.email}
//               onChange={handleChange}
//               error={touched.email && Boolean(errors.email)}
//               helperText={touched.email && errors.email}
//               fullWidth
//             />
//           </Box>

//           <Box mb={2}>
//             <TextField
//               label="Password"
//               name="password"
//               type="password"
//               value={values.password}
//               onChange={handleChange}
//               error={touched.password && Boolean(errors.password)}
//               helperText={touched.password && errors.password}
//               fullWidth
//             />
//           </Box>

//           <Box mb={2}>
//             <TextField
//               label="Confirm Password"
//               name="confirmPassword"
//               type="password"
//               value={values.confirmPassword}
//               onChange={handleChange}
//               error={touched.confirmPassword && Boolean(errors.confirmPassword)}
//               helperText={touched.confirmPassword && errors.confirmPassword}
//               fullWidth
//             />
//           </Box>

//           <Button
//             type="submit"
//             variant="contained"
//             color="primary"
//             fullWidth
//             disabled={isSubmitting}
//           >
//             הרשמה
//           </Button>
//         </Form>
//       )}
//     </Formik>
//   );
// };

// export default RegisterForm;


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
import { colors } from "../theme"; // ודאי שזה הנתיב הנכון

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

              {/* שדות */}
              {[
                { label: "שם משתמש", name: "userName", type: "text" },
                { label: "מספר משתמש", name: "userNumber", type: "text" },
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

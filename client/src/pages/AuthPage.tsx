import React, { useState } from "react";
import { Dialog, DialogTitle, DialogContent, IconButton } from "@mui/material";
import CloseIcon from "@mui/icons-material/Close";
import LoginForm from "../components/LoginForm";
import RegisterForm from "../components/RegisterForm";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { setUserId, setUserName } from "../slices/userSlice";
import { showMessage } from "../slices/messageSlice"; 

const AuthPage: React.FC = () => {
  const [openRegister, setOpenRegister] = useState(false);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const handleLogin = async (values: { email: string; password: string }) => {
    try {
      console.log("🔑 מנסה להתחבר עם:", values);
      const res = await axios.post("http://localhost:8080/user/login", values);
      console.log("✅ Login success:", res.data);

      const userId = res.data.userId;
      const userName = res.data.userName;
      dispatch(setUserId(userId)); 
      dispatch(setUserName(res.data.userName)); 

      localStorage.setItem("userId", userId);
      localStorage.setItem("userName", userName);

      navigate(`/home/${userId}`); 
    } catch (err) {
      alert("Login failed");
    }
  };

  const handleRegister = async (data: { email: string; password: string; confirmPassword: string }) => {
    try {
      const res = await axios.post("http://localhost:8080/user/register", data);
      console.log("Register success:", res.data);
      setOpenRegister(false);
       dispatch(showMessage({ message: "הרשמתך בוצעה בהצלחה", severity: "success" }));
    } catch (err) {
      console.error(err);
      dispatch(showMessage({ message: "ההרשמה נכשלה ", severity: "error" }));
    }
  };

  return (
    <div
      style={{
        maxWidth: 400,
        margin: "0 auto",
        padding: "2rem",
      }}
    >
      <LoginForm onSubmit={handleLogin} onOpenRegister={() => setOpenRegister(true)} />

      <Dialog open={openRegister} onClose={() => setOpenRegister(false)} fullWidth maxWidth="sm">
        <DialogTitle>
          <IconButton
            aria-label="close"
            onClick={() => setOpenRegister(false)}
            sx={{
              position: "absolute",
              right: 8,
              top: 8,
              color: (theme) => theme.palette.grey[500],
            }}
          >
            <CloseIcon />
          </IconButton>
        </DialogTitle>
        <DialogContent>
          <RegisterForm onSubmit={handleRegister} />
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default AuthPage;

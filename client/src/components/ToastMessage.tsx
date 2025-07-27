import React from "react";
import { Snackbar, Alert } from "@mui/material";
import { useSelector, useDispatch } from "react-redux";
import { RootState } from "../slices/store";
import { hideMessage } from "../slices/messageSlice";

const ToastMessage: React.FC = () => {
  const { open, message, severity } = useSelector((state: RootState) => state.message);
  const dispatch = useDispatch();

  const handleClose = () => {
    dispatch(hideMessage());
  };

  return (
    <Snackbar
      open={open}
      autoHideDuration={4000}
      onClose={handleClose}
      anchorOrigin={{ vertical: "top", horizontal: "center" }}
    >
      <Alert severity={severity} onClose={handleClose} sx={{ width: "100%" }}>
        {message}
      </Alert>
    </Snackbar>
  );
};

export default ToastMessage;

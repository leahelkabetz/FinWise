import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface MessageState {
  open: boolean;
  message: string;
  severity: "success" | "error" | "info" | "warning";
}

const initialState: MessageState = {
  open: false,
  message: "",
  severity: "success",
};

const messageSlice = createSlice({
  name: "message",
  initialState,
  reducers: {
    showMessage: (
      state,
      action: PayloadAction<{ message: string; severity: MessageState["severity"] }>
    ) => {
      state.open = true;
      state.message = action.payload.message;
      state.severity = action.payload.severity;
    },
    hideMessage: (state) => {
      state.open = false;
      state.message = "";
    },
  },
});

export const { showMessage, hideMessage } = messageSlice.actions;
export default messageSlice.reducer;

import { configureStore } from "@reduxjs/toolkit";
import userReducer from "./userSlice";
import messageReducer from "../slices/messageSlice";

export const store = configureStore({
  reducer: {
    user: userReducer,
    message: messageReducer,

  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

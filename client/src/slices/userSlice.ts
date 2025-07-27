import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface UserState {
  userId: number | null;
  userName: string;
}

const initialState: UserState = {
  userId: null,
  userName: "",
};

const userSlice = createSlice({
  name: "user",
  initialState,
  reducers: {
    setUserId(state, action: PayloadAction<number>) {
      state.userId = action.payload;
    },
    setUserName(state, action: PayloadAction<string>) {
      state.userName = action.payload;
    },
    clearUser(state) {
      state.userId = null;
      state.userName = "";
    },
    logout(state) {
      state.userId = null;
      state.userName = "";
    },
  },
});

export const { setUserId, setUserName, clearUser, logout } = userSlice.actions;
export default userSlice.reducer;

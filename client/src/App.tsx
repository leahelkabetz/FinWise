
import { Routes, Route } from "react-router-dom";
import AuthPage from "./pages/AuthPage";
import MainLayout from "./pages/MainLayout";
import HomePage from "./pages/HomePage";
import { useDispatch } from "react-redux";
import { useEffect } from "react";
import { setUserId, setUserName } from "./slices/userSlice";
import AddTransactionForm from "./components/AddTransactionForm";
import FullTransactionsPage from "./components/FullTransactionsPage";
import RecurringTransactionsSettings from "./components/RecurringTransactionsSettings";
import UpdateUser from "./components/UpdateUser";
import ToastMessage from "./components/ToastMessage"; // לוודא שזה הנתיב הנכון

const App = () => {
  const dispatch = useDispatch();

  useEffect(() => {
    const savedUserId = localStorage.getItem("userId");
    const savedUserName = localStorage.getItem("userName");

    if (savedUserId && !isNaN(Number(savedUserId))) {
      dispatch(setUserId(Number(savedUserId)));
      console.log("✅ userId נטען מ־localStorage:", savedUserId);
    }

    if (savedUserName) {
      dispatch(setUserName(savedUserName));
      console.log("✅ userName נטען מ־localStorage:", savedUserName);
    }
  }, []);

  return (
    <>
      <Routes>
        <Route path="/" element={<AuthPage />} />
        <Route path="/home/:userId" element={<MainLayout />}>
          <Route index element={<HomePage />} />
          <Route path="add" element={<AddTransactionForm />} />
          <Route path="table" element={<FullTransactionsPage />} />
          <Route path="fixed" element={<RecurringTransactionsSettings />} />
          <Route path="update" element={<UpdateUser />} />
        </Route>
      </Routes>
      <ToastMessage />
    </>

  );
}
export default App;
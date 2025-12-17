
import './App.css'
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import Navbar from "./components/common/Navbar.jsx";
import Footer from "./components/common/Footer.jsx";
import RegisterPage from "./components/auth/RegisterPage.jsx";
import LoginPage from "./components/auth/LoginPage.jsx";
import HomePage from "./components/pages/HomePage.jsx";
import FindFlightsPage from "./components/pages/FindFlightsPage.jsx";
import ProfilePage from "./components/profile/ProfilePage.jsx";
import UpdateProfilePage from "./components/profile/UpdateProfilePage.jsx";
import BookingPage from "./components/pages/BookingPage.jsx";
import BookingDetailsPage from "./components/pages/BookingDetailsPage.jsx";
import {RouteGuard} from "./services/RouteGuard.jsx";
import AdminDashboardPage from "./components/admin/AdminDashboardPage.jsx";
import AdminBookingDetailsPage from "./components/admin/AdminBookingDetailsPage.jsx";
import AdminFlightDetailsPage from "./components/admin/AdminFlightDetailsPage.jsx";
import AddEditAirportPage from "./components/admin/AddEditAirportPage.jsx";
import AddFlightPage from "./components/admin/AddFlightPage.jsx";
import SpecialRegistration from "./components/admin/SpecialRegistration.jsx";

function App() {

  return (
    <BrowserRouter>
        <Navbar/>
        <Routes>
            <Route path="/register" element={<RegisterPage/>}/>
            <Route path="/login" element={<LoginPage/>}/>
            <Route path="/home" element={<HomePage/>}/>
            <Route path="/flights" element={<FindFlightsPage/>}/>

            <Route path="/profile" element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<ProfilePage/>}/>}/>
            <Route path="update-profile" element={<RouteGuard allowedRoles={["CUSTOMER"]} element={<UpdateProfilePage/>}/>}/>

            <Route path="/book-flight/:id" element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingPage/>}/>}/>
            <Route path="/booking/:id" element={<RouteGuard allowedRoles={["CUSTOMER", "ADMIN", "PILOT"]} element={<BookingDetailsPage/>}/>}/>

            <Route path="/admin" element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AdminDashboardPage/>}/>}/>
            <Route path="/admin/booking/:id" element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AdminBookingDetailsPage/>}/>}/>
            <Route path="/admin/flight/:id" element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AdminFlightDetailsPage/>}/>}/>

            <Route path="/add-airport" element={<RouteGuard allowedRoles={["ADMIN"]} element={<AddEditAirportPage/>}/>}/>
            <Route path="/edit-airport/:id" element={<RouteGuard allowedRoles={["ADMIN"]} element={<AddEditAirportPage/>}/>}/>
            <Route path="/add-flight" element={<RouteGuard allowedRoles={["ADMIN", "PILOT"]} element={<AddFlightPage/>}/>}/>
            <Route path="/special-register" element={<RouteGuard allowedRoles={["ADMIN"]} element={<SpecialRegistration/>}/>}/>

            <Route path="*" element={<Navigate to="/home"/>}/>
        </Routes>
        <Footer/>
    </BrowserRouter>
  )
}

export default App

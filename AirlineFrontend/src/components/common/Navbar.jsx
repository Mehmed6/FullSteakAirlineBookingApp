import ApiService from "../../services/ApiService.js";
import {Link, useNavigate} from "react-router-dom";

const Navbar = () => {

    const navigate = useNavigate();
    const isAuthenticated = ApiService.isAuthenticated();
    const isAdmin = ApiService.isAdmin();
    const isPilot = ApiService.isPilot();
    const isCustomer = ApiService.isCustomer();

    const handleLogout = () => {
        const isConfirmed = window.confirm("Are you sure you want to logout?");
        if (isConfirmed) {
            ApiService.logout();
            navigate("/login");
        }
    }

    return (
        <nav className="nb">
            <div className="nb-container">
                <div className="nb-brand">
                    <Link to="/home" className="nb-logo">
                        <span className="logo-airline">Dogan</span>
                        <span className="logo-text">Airlines</span>
                    </Link>
                </div>
                <div className="nb-links">
                    <Link to="/home" className="nav-link">Home</Link>
                    <Link to="/flights" className="nav-link">Find Flights</Link>

                    {isAuthenticated ? (
                        <>
                            {isCustomer && (
                                <Link to="/profile" className="nav-link">Profile</Link>
                            )}
                            {isPilot && (
                                <Link to="/pilot" className="nav-link">Pilot</Link>
                            )}
                            {isAdmin && (
                                <Link to="/admin" className="nav-link">Admin</Link>
                            )}

                            <button className="nav-button" onClick={handleLogout}>Logout</button>
                        </>

                    ) : (
                        <>
                            <Link to="/login" className="nav-link">Login</Link>
                            <Link to="/register" className="nav-button nav-button-primary">Register</Link>
                        </>
                    )}
                </div>
            </div>

        </nav>
    )

}

export default Navbar;
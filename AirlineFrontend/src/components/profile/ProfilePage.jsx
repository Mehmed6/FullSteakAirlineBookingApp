import {useMessage} from "../common/MessageDisplay.jsx";
import {useEffect, useState} from "react";
import ApiService from "../../services/ApiService.js";
import {Link} from "react-router-dom";

const ProfilePage = () => {

    const {ErrorDisplay, showError } = useMessage();
    const [user, setUser] = useState(null);
    const [bookings, setBookings] = useState([]);
    const [activeTab, setActiveTab] = useState("profile");
    const [loading, setLoading] = useState(true);

    useEffect(() => {

        fetchUserProfile();
        fetchUserBookings();

    }, []);

    const fetchUserProfile = async () => {
        try {
            const response = await ApiService.getAccountDetails();
            setUser(response.data);
        } catch (error) {
            showError(error.response?.data?.message || "Failed to fetch profile");
        } finally {
            setLoading(false);
        }
    }

    const fetchUserBookings = async () => {
        try {
            const response = await ApiService.getCurrentUserBookings();

            setBookings(response.data ? response.data : []);
        } catch (error) {
            showError(error.response?.data?.message || "Failed to fetch bookings");
        }
    }

    const formatDate = (dateTime) => {
        return new Date(dateTime).toLocaleDateString([], {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    }

    if (loading) return <div className="loading">Loading Profile...</div>
    if (!user) return <div className="error">User Not Found</div>

    return (
        <div className="profile-page">
            <div className="profile-container">
                <ErrorDisplay/>
                <div className="profile-header">
                    <h2>My Account</h2>
                    <div className="welcome-message">
                        Welcome back, <strong>{user.name}</strong>
                    </div>
                </div>

                <div className="profile-tabs">
                    <button
                        className={activeTab === "profile" ? "active" : ""}
                        onClick={() => setActiveTab("profile")}
                    >
                        Profile
                    </button>

                    <button
                        className={activeTab === "bookings" ? "active" : ""}
                        onClick={() => setActiveTab("bookings")}
                    >
                        My Bookings
                    </button>
                </div>

                <div className="profile-content">
                    {activeTab === "profile" ? (
                        <div className="profile-info">
                            <div className="info-card">
                                <h3>Personal Information</h3>
                                <div className="info-row">
                                    <span className="label">Name:</span>
                                    <span className="value">{user.name}</span>
                                </div>
                                <div className="info-row">
                                    <span className="label">Email:</span>
                                    <span className="value">{user.email}</span>
                                </div>
                                <div className="info-row">
                                    <span className="label">Phone:</span>
                                    <span className="value">{user.phoneNumber}</span>
                                </div>
                                <div className="info-row">
                                    <span className="label">Account Status:</span>
                                    <span className="value">
                                        {user.active ? "Active" : "Inactive"}
                                    </span>
                                </div>
                            </div>

                            <div className="info-card">
                                <h3>Account Security</h3>
                                <div className="info-row">
                                    <span className="label">Email Verified:</span>
                                    <span className="value">
                                        {user.emailVerified ? "Yes" : "No"}
                                    </span>
                                </div>
                                <div className="info-row">
                                    <span className="label">Login Method:</span>
                                    <span className="value">
                                        {user.authMethod === "LOCAL" ? "Email/Password" : user.authMethod}
                                    </span>
                                </div>
                                <Link to="/update-profile" className="update-profile">
                                    Update Profile
                                </Link>
                            </div>
                        </div>

                    ) : (
                        <div className="bookings-list">
                            {bookings.length > 0 ? (
                                bookings.map(booking => (
                                    <div className="booking-card" key={booking.bookingReference}>
                                        <div className="booking-header">
                                            <div className="booking-ref">
                                                Booking #: {booking.bookingReference}
                                            </div>
                                            <div className={`booking-status ${booking.bookingStatus.toLowerCase()}`}>
                                                {booking.bookingStatus}
                                            </div>
                                        </div>

                                        <div className="booking-details">
                                            <div className="flight-info">
                                                <div className="flight-number">
                                                    {booking.flight?.flightNumber || "Flight details not available"}
                                                </div>
                                                <div className="route">
                                                    {booking.flight?.departureAirport?.iataCode} →
                                                    {booking.flight?.arrivalAirport?.iataCode}
                                                </div>
                                                <div className="date">
                                                    {booking.flight ?
                                                        formatDate(booking.flight.departureTime) : "N/A"}
                                                </div>
                                            </div>
                                            <div className="passengers-info">
                                                <div className="passengers-count">
                                                    {booking.passengers.length} Passenger
                                                    {booking.passengers.length > 1 ? "s" : ""}
                                                </div>
                                                <div className="passengers-list">
                                                    {booking.passengers.map((p, i) => (
                                                        <span key={i}>
                                                            {p.firstName} {p.lastName}
                                                            {booking.passengers.length - 1 > i ? ", " : ""}
                                                        </span>
                                                    ))}
                                                </div>
                                            </div>

                                            <div className="booking-actions">
                                                <Link className="view-details" to={`/booking/${booking.id}`}>
                                                    View Details
                                                </Link>
                                            </div>
                                        </div>
                                    </div>
                                ))
                            ) : (
                                <div className="no-bookings">
                                    <p>You don't have any bookings yet</p>
                                    <Link className="book-flight" to="/flights">
                                        Book a Flight
                                    </Link>
                                </div>
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div>
    )
}

export default ProfilePage;
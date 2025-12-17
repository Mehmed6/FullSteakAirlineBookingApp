import {useMessage} from "../common/MessageDisplay.jsx";
import {Link, useNavigate} from "react-router-dom";
import {useState} from "react";
import ApiService from "../../services/ApiService.js";

const LoginPage = () => {

    const {ErrorDisplay, SuccessDisplay, showError, showSuccess} = useMessage();
    const navigate = useNavigate();

    const [formData, setFormData] = useState({
        email: '',
        password: ''
    })

    const handleChange = (e) => {
        setFormData({...formData, [e.target.name]: e.target.value})};

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (!formData.email || !formData.password ) {
            showError("Email & password are required")
            return;
        }

        const loginData = {
            email : formData.email,
            password : formData.password
        };

        try {
            const response = await ApiService.loginUser(loginData);
            if (response.statusCode === 200) {
                showSuccess("Login successful! Redirecting to Home...");
                ApiService.saveToken(response.data.token);
                ApiService.saveRoles(response.data.roles);
                setTimeout(() => {
                    navigate('/home');
                }, 2000);
            } else {
                showError(response.message);
            }
        } catch (error) {
            showError(error.response?.data?.message || error.message);
        }
    };

    return (
        <div className="auth-page">
            <div className="auth-card">
                <ErrorDisplay/>
                <SuccessDisplay/>
                <div className="auth-header">
                    <h2>Welcome to Dogan Airlines</h2>
                    <p>Sign in to book your next flight</p>
                </div>

                <form className="auth-form" onSubmit={handleSubmit}>

                    <div className="form-group">
                        <label htmlFor="">Email</label>
                        <input
                            type="email"
                            name="email"
                            id="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            placeholder="Enter your email address"
                        />
                    </div>

                    <div className="form-group">
                        <label htmlFor="">Password</label>
                        <input
                            type="password"
                            name="password"
                            id="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            placeholder="Enter your password"
                        />
                    </div>

                    <button className="auth-button" type="submit">
                        Sign In
                    </button>

                    <div className="auth-footer">
                        <p>Don't have an account? <Link to="/register"> Register here </Link></p>
                    </div>
                </form>
            </div>
        </div>
    )

};

export default LoginPage
import {useNavigate} from "react-router-dom";
import {useMessage} from "../common/MessageDisplay.jsx";
import {useState} from "react";
import ApiService from "../../services/ApiService.js";

const SpecialRegistration = () => {
    const { ErrorDisplay, SuccessDisplay, showError, showSuccess } = useMessage();
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        fullName: '',
        email: '',
        password: '',
        phoneNumber: '',
        roles: []
    });

    const availableRoles = [
        { value: 'ADMIN', label: 'Admin' },
        { value: 'PILOT', label: 'Pilot' }
    ];

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleRoleToggle = newRole => {
        setFormData(prev => {
            if (prev.roles.includes(newRole))
                return { ...prev, roles: prev.roles.filter(role => role !== newRole) };
            else
                return { ...prev, roles: [...prev.roles, newRole] };
        });
    };

    const handleSubmit = async e => {
        e.preventDefault();
        if (!formData.fullName || !formData.email || !formData.password ||
            !formData.phoneNumber) {
            showError('All fields except roles are required');
            return;
        }

        if (formData.roles.length === 0) {
            showError('Please select at least one role');
            return;
        }

        try {
            const response = await ApiService.registerUser(formData);
            if (response.statusCode === 200) {
                showSuccess('User registered successfully');
                setFormData({
                    fullName: '', email: '', password: '',
                    phoneNumber: '', roles: []
                });

                setTimeout(() => {
                    navigate('/admin');
                }, 2000);
            } else
                showError(response.message || 'Registration failed');
        } catch (error) {
            showError(error.response?.data?.message || error.message || 'Registration failed');
        }
    }

    return (
        <div className="admin-register-page">
            <div className="admin-register-card">
                <div className="admin-register-header">
                    <h2 className="admin-register-title">Admin Custom Register Page</h2>
                    <p className="admin-register-description">Create a new user account with specific roles</p>
                </div>

                <div className="admin-register-content">
                    <form className="admin-register-form" onSubmit={handleSubmit}>
                        <div className="admin-form-group">
                            <label className="admin-label">Full Name</label>
                            <input
                                name="fullName"
                                type="text"
                                value={formData.fullName}
                                onChange={handleChange}
                                required
                                placeholder="User's full name"
                                className="admin-input"
                            />
                        </div>
                        <div className="admin-form-group">
                            <label className="admin-label">Email</label>
                            <input
                                name="email"
                                type="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                                placeholder="User's email"
                                className="admin-input"
                            />
                        </div>
                        <div className="admin-form-group">
                            <label className="admin-label">Password</label>
                            <input
                                name="password"
                                type="password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                                placeholder="Create password"
                                className="admin-input"
                            />
                        </div>
                        <div className="admin-form-group">
                            <label className="admin-label">Phone Number</label>
                            <input
                                name="phoneNumber"
                                type="text"
                                value={formData.phoneNumber}
                                onChange={handleChange}
                                required
                                placeholder="Phone number"
                                className="admin-input"
                            />
                        </div>
                        <div className="admin-form-group">
                            <label className="admin-label">Roles</label>
                            <div className="admin-roles-container">
                                {availableRoles.map(role => (
                                    <div  key={role.value}
                                          className={`admin-role-checkbox ${formData.roles.includes(role.value) ? 'selected' : ''}`}
                                          onClick={() => handleRoleToggle(role.value)}
                                    >
                                        <input
                                            type="checkbox"
                                            checked={formData.roles.includes(role.value)}
                                            readOnly
                                            className="admin-role-input"
                                        />
                                        <span>{role.label}</span>
                                    </div>
                                ))}
                            </div>
                        </div>
                        <ErrorDisplay />
                        <SuccessDisplay />

                        <button type="submit" className="admin-register-button">
                            Register
                        </button>
                    </form>
                </div>
            </div>
        </div>
    )
};

export default SpecialRegistration;
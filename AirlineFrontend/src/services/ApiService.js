import axios from "axios";

export default class ApiService {

    static BASE_URL = "http://localhost:6767/api";

    static saveToken(token) {
        localStorage.setItem("token", token);
    }

    static getToken() {
        return localStorage.getItem("token");
    }

    static saveRoles(roles) {
        localStorage.setItem("roles", JSON.stringify(roles));
    }

    static getRoles() {
        const roles = localStorage.getItem("roles");
        return roles ? JSON.parse(roles) : null;
    }

    static hasRole(role) {
        const roles = this.getRoles();
        return roles ? roles.includes(role) : false;
    }

    static isAdmin() {
        return this.hasRole("ADMIN");
    }

    static isPilot() {
        return this.hasRole("PILOT");
    }

    static isCustomer() {
        return this.hasRole("CUSTOMER");
    }

    static logout() {
        localStorage.removeItem("token");
        localStorage.removeItem("roles");
    }

    static isAuthenticated() {
        return !!this.getToken();
    }

    static getHeader() {
        const token = this.getToken();
        return token ? {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        } : {};
    }

    //Register User
    static async registerUser(body) {
        const response = await axios.post(`${this.BASE_URL}/auth/register`, body);
        return response.data;
    }

    //Login User
    static async loginUser(body) {
        const response = await axios.post(`${this.BASE_URL}/auth/login`, body);
        return response.data;
    }

    /**USERS PROFILE MANAGEMENT SESSION */
    static async getAccountDetails() {
        const response = await axios.get(`${this.BASE_URL}/users/me`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updateMyAccount(body) {
        const response = await axios.put(`${this.BASE_URL}/users`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllPilots() {
        const response = await axios.get(`${this.BASE_URL}/users/pilots`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    /** AIRPORT API METHODS */
    static async createAirport(body) {
        const response = await axios.post(`${this.BASE_URL}/airports`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updateAirport(body) {
        const response = await axios.put(`${this.BASE_URL}/airports`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllAirports() {
        const response = await axios.get(`${this.BASE_URL}/airports`)
        return response.data;
    }

    static async getAirportById(id) {
        const response = await axios.get(`${this.BASE_URL}/airports/${id}`)
        return response.data;
    }

    /** BOOKING API METHODS */
    static async createBooking(body) {
        const response = await axios.post(`${this.BASE_URL}/bookings`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getBookingById(id) {
        const response = await axios.get(`${this.BASE_URL}/bookings/${id}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllBookings() {
        const response = await axios.get(`${this.BASE_URL}/bookings`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getCurrentUserBookings() {
        const response = await axios.get(`${this.BASE_URL}/bookings/me`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async updateBookingStatus(id, status) {
        const response = await axios.put(`${this.BASE_URL}/bookings/${id}`, status, {
            headers: this.getHeader()
        })
        return response.data;
    }

    /** FLIGHT API METHODS */
    static async createFlight(body) {
        const response = await axios.post(`${this.BASE_URL}/flights`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getFlightById(id) {
        const response = await axios.get(`${this.BASE_URL}/flights/${id}`, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async getAllFlights() {
        const response = await axios.get(`${this.BASE_URL}/flights`)
        return response.data;
    }

    static async updateFlight(body) {
        const response = await axios.put(`${this.BASE_URL}/flights`, body, {
            headers: this.getHeader()
        })
        return response.data;
    }

    static async searchFlights(departureIataCode, arrivalIataCode, departureDate) {
        const params = {departureIataCode, arrivalIataCode, departureDate};
        const response = await axios.get(`${this.BASE_URL}/flights/search`, { params });
        return response.data;
    }

    static async getAllCities() {
        const response = await axios.get(`${this.BASE_URL}/flights/cities`)
        return response.data;
    }

    static async getAllCountries() {
        const response = await axios.get(`${this.BASE_URL}/flights/countries`)
        return response.data;
    }

}
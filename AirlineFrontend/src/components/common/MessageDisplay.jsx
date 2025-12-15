import {useEffect, useState} from "react";

const MessageDisplay = ({message, type = 'error', onDismiss}) => {

    useEffect(() => {
        const timer = setTimeout(() => {
            onDismiss();
        }, 4000);

        return () => clearTimeout(timer);

    }, [message, onDismiss]);

    if (!message) return null;

    const isError = type === 'error';
    const displayClass = isError ? 'error-display' : 'success-display';
    const messageClass = isError ? 'error-message' : 'success-message';
    const progressClass = isError ? 'error-progress' : 'success-progress';

    return (
        <div className={`message-display ${displayClass}`}>
            <div className="message-content">
                <span className={`message-text ${messageClass}`}>{message}</span>
                <div className={`message-progress ${progressClass}`}></div>
            </div>
        </div>
    )
}

export const useMessage = () => {
    const [errorMessage, setErrorMessage] = useState();
    const [successMessage, setSuccessMessage] = useState();

    const showError = (message) => {
        setErrorMessage(message);
    }

    const showSuccess = (message) => {
        setSuccessMessage(message);
    }

    const dismissError = () => {
        setErrorMessage(null);
    }

    const dismissSuccess = () => {
        setSuccessMessage(null);
    }

    return {
        ErrorDisplay: () => (
            <MessageDisplay
                message={errorMessage}
                type="error"
                onDismiss={dismissError}
            />
        ),
        SuccessDisplay: () => (
            <MessageDisplay
                message={successMessage}
                type="success"
                onDismiss={dismissSuccess}
            />
        ),
        showError,
        showSuccess,
        dismissError,
        dismissSuccess
    };
}


export default MessageDisplay;
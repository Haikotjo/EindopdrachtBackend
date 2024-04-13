package nl.novi.eindopdrachtbackend.common;

public class ApiResponse {
    private boolean success;  // Vlag om succes of falen van de operatie aan te geven
    private String message;   // Bericht met details over de operatie
    private Object data;      // De data die teruggegeven wordt (kan elk object zijn)

    // Constructor
    public ApiResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Getters en setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

package nl.deltares.services.rest.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liferay.portal.kernel.exception.PortalException;

import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "ErrorMessage")
public class ErrorMessage {

    /** contains the same HTTP Status code returned by the server */
    int status;

    /** application specific error code */
    int code;

    /** message describing the error*/
    String message;

    /** extra information that might useful for developers */
    String developerMessage;

    @XmlElement(name = "status")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @XmlElement(name = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @XmlElement(name = "developerMessage")
    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public ErrorMessage(PortalException ex){
        this.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        this.setMessage("An error occurred while getting the group, company or structure ids from Liferay.");
        this.setDeveloperMessage(ex.getMessage());
    }

    public ErrorMessage(JsonProcessingException ex){
        this.setStatus(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
        this.setMessage("An error occurred while getting the JSON object from the article information.");
        this.setDeveloperMessage(ex.getMessage());
    }

    public ErrorMessage(LiferayRestException ex){
        this.setStatus(ex.getStatus());
        this.setMessage(ex.getMessage());
        this.setDeveloperMessage(ex.getDeveloperMessage());
    }

    public ErrorMessage() {}
}

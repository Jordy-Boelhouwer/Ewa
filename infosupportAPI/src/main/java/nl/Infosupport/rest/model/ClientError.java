/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.Infosupport.rest.model;

import java.io.Serializable;

/**
 *
 * @author Jordy
 */
public class ClientError implements Serializable{
    private String errorMessage;
    
    public ClientError(String errorMessage) {
        setMessage(errorMessage);
    }
    
     public final void setMessage(String message) {
        this.errorMessage = message;
    }


    public String getErrorMessage() {
        return errorMessage;
    }
}

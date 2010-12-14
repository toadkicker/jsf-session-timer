package com.toadkicker.ui;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.*;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.StatusMessage;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Identity;
import org.jboss.seam.web.ServletContexts;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: tbaur
 * Date: Nov 17, 2010
 * Time: 6:30:47 PM
 * To change this template use File | Settings | File Templates.
 */
@AutoCreate
@Name("sessionChecker")
@Scope(ScopeType.SESSION)
public class HttpSessionChecker implements Serializable {
    @Logger
    Log logger;

    @In
    FacesMessages facesMessages;
 
    @In
    Identity identity;

//    @In
//    FacesContext facesContext;

//optionally add server side check to verify session is closed
//used when debugging but generic enough	
//    @WebRemote
//    public boolean isNewSession() {
//        return ServletContexts.instance().getRequest().getSession().isNew();
//    }


    @WebRemote
    public void logout(){
        facesMessages.add(StatusMessage.Severity.WARN, "Your session expired due to inactivity and you were logged out automatically.");
        logger.info("User logged out automatically due to inactivity");
        identity.logout();
    }

//tell us when the app is configured to timeout a session
    @WebRemote
    @Factory(value = "sessionTimeoutSeconds")
    public int getSessionTimeoutSeconds(){
        return ServletContexts.getInstance().getRequest().getSession(true).getMaxInactiveInterval();
    }
}
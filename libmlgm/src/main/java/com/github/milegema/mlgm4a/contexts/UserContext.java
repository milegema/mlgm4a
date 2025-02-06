package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.RoamingUserURN;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;
import com.github.milegema.mlgm4a.security.Token;

public class UserContext extends ContextBase {

    private RootContext parent;

    private EmailAddress email;
    private RemoteURL initialLocation;
    private RemoteURL currentLocation;
    private String displayName;
    private UserID userID; // the local-user-id
    private RoamingUserURN roamingName;
    private Token token;

    public UserContext() {
    }

    public RootContext getParent() {
        return parent;
    }

    public void setParent(RootContext parent) {
        this.parent = parent;
    }

    public UserID getUserID() {
        return userID;
    }

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public void setEmail(EmailAddress email) {
        this.email = email;
    }

    public RoamingUserURN getRoamingName() {
        return roamingName;
    }

    public void setRoamingName(RoamingUserURN roamingName) {
        this.roamingName = roamingName;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public RemoteURL getInitialLocation() {
        return initialLocation;
    }

    public void setInitialLocation(RemoteURL initialLocation) {
        this.initialLocation = initialLocation;
    }

    public RemoteURL getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(RemoteURL currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }
}

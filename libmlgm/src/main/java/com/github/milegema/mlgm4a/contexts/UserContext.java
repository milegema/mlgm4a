package com.github.milegema.mlgm4a.contexts;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.UserID;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

public class UserContext extends ContextBase {

    private RootContext parent;

    private EmailAddress email;
    private RemoteURL remote;
    private String displayName;
    private UserID userID; // the local-user-id

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

    public RemoteURL getRemote() {
        return remote;
    }

    public void setRemote(RemoteURL remote) {
        this.remote = remote;
    }

    @Override
    public BlockContext getParentContext() {
        return this.parent;
    }
}

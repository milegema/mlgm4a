package com.github.milegema.mlgm4a.libmlgm;

import com.github.milegema.mlgm4a.data.ids.EmailAddress;
import com.github.milegema.mlgm4a.data.ids.RoamingUserURN;
import com.github.milegema.mlgm4a.logs.Logs;
import com.github.milegema.mlgm4a.network.inforefs.RemoteURL;

import org.junit.Assert;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class RoamingUserTest {
    @Test
    public void useRoamingUserURN() throws MalformedURLException {
        RemoteURL url = new RemoteURL("http://example.com:2333/a/b/c");
        EmailAddress email = new EmailAddress("u1@email.domain.name");
        RoamingUserURN urn = new RoamingUserURN(url, email);
        Logs.debug("" + urn);
    }
}

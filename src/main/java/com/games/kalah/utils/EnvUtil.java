package com.games.kalah.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriBuilder;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

@Component
public class EnvUtil {

    private static final String PROTOCOL = "http";

    private String port;
    private String hostname;

    @Autowired
    private Environment environment;

    /**
     * Get port.
     *
     * @return
     */
    public String getPort() {
        if (port == null)
            port = environment.getProperty("server.port", "8080");
        return port;
    }

    /**
     * Get port, as Integer.
     *
     * @return
     */
    public Integer getPortAsInt() {
        return Integer.valueOf(getPort());
    }

    /**
     * Get hostname.
     *
     * @return
     */
    public String getHostname() throws UnknownHostException {
        if (hostname == null) hostname = InetAddress.getLocalHost().getHostName();
        return hostname;
    }

    /**
     * This method creates a URI containing the host name and port the server is running.
     * @return uri to be given to the client
     * @throws UnknownHostException
     * @throws URISyntaxException
     */
    public String getServerUri() throws UnknownHostException, URISyntaxException {
        URI url = new URI(PROTOCOL, null, getHostname(), getPortAsInt(), null, null, null);
        return url.toString();
    }
}

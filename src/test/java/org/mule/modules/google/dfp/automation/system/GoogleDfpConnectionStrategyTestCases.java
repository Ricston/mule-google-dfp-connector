package org.mule.modules.google.dfp.automation.system;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mule.api.ConnectionException;
import org.mule.api.ConnectionExceptionCode;
import org.mule.modules.google.dfp.strategy.GoogleDfpConnectionStrategy;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import com.google.api.ads.common.lib.exception.OAuthException;
import com.google.api.ads.common.lib.exception.ValidationException;

public class GoogleDfpConnectionStrategyTestCases {

    private static Properties validCredentials;

    private static final Logger logger = Logger.getLogger(GoogleDfpConnectionStrategyTestCases.class);

    private static String username;
    private static String password;
    private static String refreshToken;
    private static String tokenServerUrl;
    private static String appName;
    private static String networkCode;
    private static String endpoint;

    @BeforeClass
    public static void setup() throws Exception {

        validCredentials = ConfigurationUtils.getAutomationCredentialsProperties();

        username = validCredentials.getProperty("google-dfp.clientId");
        password = validCredentials.getProperty("google-dfp.clientSecret");
        refreshToken = validCredentials.getProperty("google-dfp.refreshToken");
        tokenServerUrl = validCredentials.getProperty("google-dfp.tokenServerUrl");
        appName = validCredentials.getProperty("google-dfp.applicationName");
        networkCode = validCredentials.getProperty("google-dfp.networkCode");
        endpoint = validCredentials.getProperty("google-dfp.endpoint");

    }

    @Test
    public void validCredentialsConnectivityTest() throws Exception {

        GoogleDfpConnectionStrategy config = new GoogleDfpConnectionStrategy();

        config.setApplicationName(appName);
        config.setNetworkCode(networkCode);
        config.setEndpoint(endpoint);
        config.setRefreshToken(refreshToken);
        config.setTokenServerUrl(tokenServerUrl);

        // Call the @TestConnectivity
        config.connect(username, password);
        Assert.assertTrue(true);
    }

    @Test
    public void invalidCredentialsConnectivityTest() throws Exception {

        GoogleDfpConnectionStrategy config = new GoogleDfpConnectionStrategy();

        config.setApplicationName(appName);
        config.setNetworkCode(networkCode);
        config.setEndpoint(endpoint);
        config.setRefreshToken(refreshToken);
        config.setTokenServerUrl(tokenServerUrl);

        try {
            // Call the @TestConnectivity
            config.connect("noUserName", "noPassword");
        } catch (ConnectionException e) {
            Assert.assertTrue(e.getCode()
                    .equals(ConnectionExceptionCode.INCORRECT_CREDENTIALS));
            Assert.assertTrue(e.getCause() instanceof OAuthException);
            logger.error("Invalid credentials");
            Assert.assertTrue(true);
        }
    }

    @Test
    public void nullCredentialsConnectivityTest() throws Exception {

        GoogleDfpConnectionStrategy config = new GoogleDfpConnectionStrategy();

        config.setApplicationName(appName);
        config.setNetworkCode(networkCode);
        config.setEndpoint(endpoint);
        config.setRefreshToken(refreshToken);
        config.setTokenServerUrl(tokenServerUrl);

        try {
            // Call the @TestConnectivity
            config.connect(null, null);
        } catch (ConnectionException e) {
            Assert.assertTrue(e.getCause() instanceof ValidationException);
            logger.error("Null credentials");
            Assert.assertTrue(true);
        }
    }

}

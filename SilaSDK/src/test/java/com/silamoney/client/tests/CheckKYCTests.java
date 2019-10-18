package com.silamoney.client.tests;

import com.silamoney.client.api.ApiResponse;
import com.silamoney.client.api.SilaApi;
import com.silamoney.client.domain.BaseResponse;
import com.silamoney.client.exceptions.BadRequestException;
import com.silamoney.client.exceptions.InvalidSignatureException;
import com.silamoney.client.exceptions.ServerSideException;
import com.silamoney.client.testsutils.DefaultConfigurations;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Karlo Lorenzana
 */
public class CheckKYCTests {

    SilaApi api = new SilaApi(DefaultConfigurations.host,
            DefaultConfigurations.appHandle,
            DefaultConfigurations.privateKey);

    @Test
    public void Response200Success() throws Exception {
        ApiResponse response = api.CheckKYC(DefaultConfigurations.userHandle, DefaultConfigurations.userPrivateKey);

        assertEquals(200, response.statusCode);
        assertEquals("KYC passed for " + DefaultConfigurations.userHandle, ((BaseResponse) response.data).message);
        assertEquals("SUCCESS", ((BaseResponse) response.data).status);
    }
    
    @Test
    public void Response200Failure() throws Exception {
        ApiResponse response = api.CheckKYC("notpassed.silamoney.eth", DefaultConfigurations.userPrivateKey);

        assertEquals(200, response.statusCode);
        assertEquals("KYC not passed for notpassed.silamoney.eth", ((BaseResponse) response.data).message);
        assertEquals("FAILURE", ((BaseResponse) response.data).status);
    }

    @Test(expected = BadRequestException.class)
    public void Response400() throws
            BadRequestException,
            InvalidSignatureException,
            ServerSideException,
            IOException,
            InterruptedException {
        api.CheckKYC("badrequest.silamoney.eth", DefaultConfigurations.userPrivateKey);
    }

    @Test(expected = InvalidSignatureException.class)
    public void Response401() throws
            BadRequestException,
            InvalidSignatureException,
            ServerSideException,
            IOException,
            InterruptedException {
        api.CheckKYC("invalidsignature.silamoney.eth", DefaultConfigurations.userPrivateKey);
    }
}
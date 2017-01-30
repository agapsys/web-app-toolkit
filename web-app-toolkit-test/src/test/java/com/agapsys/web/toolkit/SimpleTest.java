/*
 * Copyright 2016 Agapsys Tecnologia Ltda-ME.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.agapsys.web.toolkit;

import com.agapsys.http.HttpGet;
import com.agapsys.http.HttpResponse;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleTest {
    // STATIC SCOPE ============================================================
    @WebServlet("/*")
    public static class TestServlet extends HttpServlet {

        @Override
        protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            resp.setStatus(200);
            resp.getWriter().print(req.getPathInfo());
        }
    }
    // =========================================================================

    // INSTANCE SCOPE ==========================================================
    private WebApplicationContainer wac;

    @Before
    public void before() {
        wac = WebApplicationContainer.newInstance(MockedWebApplication.class)
            .registerServlet(TestServlet.class);

        wac.start();
    }

    @After
    public void after() {
        wac.stop();
    }

    @Test
    public void simpleTest() {
        HttpResponse.StringResponse resp = wac.doRequest(new HttpGet("/test-url"));
        TestUtils.assertStatus(200, resp);
        Assert.assertEquals("/test-url", resp.getContentString());
    }
    // =========================================================================

}

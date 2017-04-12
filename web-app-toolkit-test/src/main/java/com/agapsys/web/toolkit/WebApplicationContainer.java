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

import com.agapsys.web.toolkit.filters.AttributeServiceFilter;
import com.agapsys.jee.StacktraceErrorHandler;
import com.agapsys.jee.TestingServletContainer;
import javax.servlet.http.HttpServlet;

/**
 * Default ServletContainerBuilder for web-app-toolkit based applications.
 */
public class WebApplicationContainer<WAC extends WebApplicationContainer<WAC>> extends TestingServletContainer<WAC> {

    public static WebApplicationContainer<?> newInstance(Class<? extends AbstractWebApplication> webApp, Class<? extends HttpServlet>...servlets) {
        WebApplicationContainer wac = new WebApplicationContainer<>(webApp);

        for (Class<? extends HttpServlet> servlet : servlets) {
            wac.registerServlet(servlet);
        }
        
        return wac;
    }

    private void __registerWebApplication(Class<? extends AbstractWebApplication> webApp) {
        if (webApp == null)
            throw new IllegalArgumentException("Null web application");

        super.registerServletContextListener(webApp);
        super.registerFilter(AttributeServiceFilter.class, "/*");
        super.setErrorHandler(new StacktraceErrorHandler());
    }

    public WebApplicationContainer(Class<? extends AbstractWebApplication> webApp) {
        __registerWebApplication(webApp);
    }
}

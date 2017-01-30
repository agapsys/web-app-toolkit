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

import com.agapsys.http.HttpResponse.StringResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;
import javax.servlet.http.HttpServletResponse;

public class TestUtils {

    protected TestUtils() {}

    /**
     * Prints a line message to console.
     * @param msg message to be print
     * @param msgArgs optional message arguments
     */
    public static void println(String msg, Object...msgArgs) {
        if (msgArgs.length > 0)
            msg = String.format(msg, msgArgs);

        System.out.println(msg);
    }

    /**
     * Prints a message to console.
     * @param msg message to be print
     * @param msgArgs optional message arguments
     */
    public static void print(String msg, Object...msgArgs) {
        if (msgArgs.length > 0)
            msg = String.format(msg, msgArgs);

        System.out.print(msg);
    }



    /**
     * Returns an UTC date at midnight according to parameters
     * @param year date year
     * @param month month number (1 to 12)
     * @param day day of the month
     * @return UTC date at midnight
     */
    public static Date getUtcDateAtMidnight(int year, int month, int day) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            return sdf.parse(String.format("%4d-%2d-%2dT00:00:00.000Z", year, month, day));
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Return the instance of running application
     * @return running application instance.
     */
    public static AbstractApplication getRunningApplication() {
        return AbstractApplication.getRunningInstance();
    }

    /**
     * Returns a module used by running application
     * @param <T> module type
     * @param moduleClass module class
     * @return module instance associated with given class
     */
    public static <T extends Module> T getApplicationModule(Class<T> moduleClass) {
        return getRunningApplication().getModule(moduleClass);
    }

    /**
     * Returns a service used by running application
     * @param <T> module type
     * @param serviceClass service class
     * @return service instance associated with given class
     */
    public static <T extends Service> T getApplicationService(Class<T> serviceClass) {
        return getRunningApplication().getService(serviceClass);
    }

    /**
     * Checks if two strings are equals with parameterized case-sensitivity
     * @param str1 first string
     * @param str2 second string
     * @param ignoreCase defines if check must ignore case-sensitivity
     * @return a boolean indicating if given strings are equals
     */
    public static boolean strEquals(String str1, String str2, boolean ignoreCase) {
        if (ignoreCase)
            return str1.equalsIgnoreCase(str2);
        else
            return str1.equals(str2);
    }

    /**
     * Returns a boolean indicating if a string collection contains a string with parameterized case-sensitivity
     * @param strCollection string collection
     * @param str string to be searched
     * @param ignoreCase defines if check must ignore case-sensitivity
     * @return  a boolean indicating if a string collection contains a string with parameterized case-sensitivity
     */
    public static boolean contains(Collection<String> strCollection, String str, boolean ignoreCase) {
        for (String _str : strCollection) {
            if (strEquals(_str, str, ignoreCase))
                return true;
        }

        return false;
    }

    /**
     * Asserts a server response status code
     * @param expected expected status code
     * @param resp server response
     */
    public static void assertStatus(int expected, StringResponse resp) {
        if (expected != HttpServletResponse.SC_INTERNAL_SERVER_ERROR && resp.getStatusCode() == HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            throw new RuntimeException(String.format("Internal Server error. Server response:\n%s", resp.getContentString()));

        assert expected == resp.getStatusCode() : String.format("Expected %d. Returned: %d", expected, resp.getStatusCode());
    }

    /**
     * Asserts both server response status code and response contents
     * @param expectedStatus expected status code
     * @param expectedResponseContent expected response content
     * @param resp server response
     */
    public static void assertErrorStatus(int expectedStatus, String expectedResponseContent, StringResponse resp) {
        assertStatus(expectedStatus, resp);

        assert expectedResponseContent.equals(resp.getContentString()) : String.format("Expected \"%s\". Returned: \"%s\"", expectedResponseContent, resp.getContentString());
    }

    /**
     * Performs a pause during test execution
     * @param interval pause interval (in milliseconds)
     * @param message message
     * @param msgArgs optional message arguments
     */
    public static void pause(long interval, String message, Object...msgArgs) {
        println(message, msgArgs);

        try {
            Object syncObject = new Object();
            synchronized(syncObject) {
                syncObject.wait(interval);
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
    // =========================================================================
}

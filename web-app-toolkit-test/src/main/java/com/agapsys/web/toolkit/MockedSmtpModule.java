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

import com.agapsys.mail.Message;
import com.agapsys.web.toolkit.modules.SmtpModule;

public class MockedSmtpModule extends SmtpModule {
    
    private Message message = null;

    public void reset() {
        message = null;
    }
    
    public boolean isMessageSent() {
        return message != null;
    }
    
    public Message getMessage() {
        return message;
    }
    
    @Override
    protected void onSendMessage(Message message) {
        this.message = message;
    }
}

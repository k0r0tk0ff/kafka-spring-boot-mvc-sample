package ru.k0r0tk0ff.domain;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;

@Validated
public class TextMessage {

    private  String key;

    @NotBlank(message = "MessageBody can not be empty or null!")
    private  String messageBody;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    @Override
    public String toString() {
        return "TextMessage{" +
                "key='" + key + '\'' +
                ", messageBody='" + messageBody + '\'' +
                '}';
    }

    public static final class Builder {
        private  String key;
        private  String messageBody;

        public Builder withKey(String key) {
            this.key = key;
            return this;
        }

        public Builder withMessageBody(String messageBody) {
            this.messageBody = messageBody;
            return this;
        }

        public TextMessage build() {
            TextMessage textMessage = new TextMessage();
            textMessage.setKey(key);
            textMessage.setMessageBody(messageBody);
            return textMessage;
        }
    }
}

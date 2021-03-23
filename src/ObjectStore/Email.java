/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ObjectStore;

import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author TJ
 */
public class Email {
    private String sender;
    private LocalDate sendDate; 
    private String subject;
    private String content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDate getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDate sendDate) {
        this.sendDate = sendDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.sender);
        hash = 59 * hash + Objects.hashCode(this.sendDate);
        hash = 59 * hash + Objects.hashCode(this.subject);
        hash = 59 * hash + Objects.hashCode(this.content);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Email other = (Email) obj;
        if (!Objects.equals(this.sender, other.sender)) {
            return false;
        }
        if (!Objects.equals(this.subject, other.subject)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.sendDate, other.sendDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Email{" + "sender=" + sender + ", sendDate=" + sendDate + ", subject=" + subject + ", content=" + content + '}';
    }
    
    
    
}

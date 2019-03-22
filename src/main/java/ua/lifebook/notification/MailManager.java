package ua.lifebook.notification;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.BodyPart;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class MailManager {
    private JavaMailSenderImpl mailSender;
    private String from;
    private String subject;
    private List<String> allowedIps;
    private List<String> allowedEmails;
    private boolean send;
    private boolean useIpFilter;
    private boolean useEmailFilter;
    private boolean sendInThread = false;
    private int mailSenderPoolSize = 20;
    private int mailSenderPoolCount = 0;

    public MailManager(JavaMailSenderImpl mailSender, String from) {
        this.mailSender = mailSender;
        this.from = from;
    }

    public void sendMail(String to, String subject, String content) throws Exception {
        sendMail(new String[] { to }, null, null, from, subject, content, null, MailType.HTML);
    }

    public void sendMail(String to, String from, String subject, String content, MailType mailType) throws Exception {
        sendMail(new String[] { to }, null, null, from, subject, content, null, mailType);
    }

    public void sendMail(String[] to, String[] BCC, String[] CC, String from, String subject, String content, MailType mailType) throws Exception {
        sendMail(to, BCC, CC, from, subject, content, null, mailType);
    }

    public void sendMail(String[] to, String[] BCC, String[] CC, String from, String subject, String content, MimeBodyPart[] files, MailType mailType) throws Exception {
        try {
            MimeMessage message = getMimeMessage(to, BCC, CC, from, subject, content, files, mailType);
            sendMail(message);
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage() + ". Can't send mail.");
        }
    }

    private MimeMessage getMimeMessage(String[] to, String[] BCC, String[] CC, String from, String subject, String content, MimeBodyPart[] files, MailType mailType) throws Exception {
        final MimeMessage message = mailSender.createMimeMessage();

        try {
            setAddressesAndSubjectToMimeMessage(message, to, BCC, CC, from, subject);

            if (isEmptyArray(files)) {
                if (!isEmpty(content)) message.setContent(content, mailType.toString());
            } else {
                setMultipartContentToMimeMessage(message, content, files, mailType);
            }
        } catch (Exception e) {
            throw new Exception("Error: " + e.getMessage() + ". Can't create mail.");
        }

        return message;
    }

    private void setAddressesAndSubjectToMimeMessage(MimeMessage mimeMessage, String[] to, String[] BCC, String[] CC, String from, String subject) throws MessagingException {
        addRecipientsWithTypeToMimeMessage(mimeMessage, to, RecipientType.TO);
        addRecipientsWithTypeToMimeMessage(mimeMessage, BCC, RecipientType.BCC);
        addRecipientsWithTypeToMimeMessage(mimeMessage, CC, RecipientType.CC);

        if (!isEmpty(from)) mimeMessage.setFrom(new InternetAddress(from));
        if (!isEmpty(subject)) mimeMessage.setSubject(subject);
    }

    private void addRecipientsWithTypeToMimeMessage(MimeMessage mimeMessage, String[] recipients, RecipientType recipientType) throws MessagingException {
        if (isEmptyArray(recipients)) return;

        for (String recipient : recipients) {
            if (isEmailAddressAllowed(recipient)) mimeMessage.addRecipient(recipientType, new InternetAddress(recipient));
        }
    }

    private void setMultipartContentToMimeMessage(MimeMessage message, String content, MimeBodyPart[] files, MailType mailType) throws MessagingException {
        final Multipart multipart = new MimeMultipart();

        if (!isEmpty(content)) {
            final BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, mailType.toString());

            multipart.addBodyPart(bodyPart);
        }

        if (files != null) {
            for (MimeBodyPart file : files) {
                multipart.addBodyPart(file);
            }
        }

        message.setContent(multipart);
    }

    private boolean isEmptyArray(Object[] array) {
        return array == null || array.length == 0;
    }

    private void sendMail(final MimeMessage message) {
        if (sendInThread) {
            sendMessageInThread(message);
        } else {
            mailSender.send(message);
        }
    }

    private void sendMessageInThread(final MimeMessage message) {
        while (mailSenderPoolCount >= mailSenderPoolSize) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        mailSenderPoolCount++;

        new Thread(
            () -> {
                try {
                    mailSender.send(message);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    mailSenderPoolCount--;
                }
            }
        ).start();
    }

    private boolean isEmailAddressAllowed(String email) {
        if (null == email) {
            return false;
        }
        if (!isUseEmailFilter()) {
            return true;
        }
        for (String allowedEmail : allowedEmails) {
            if (email.equalsIgnoreCase(allowedEmail)) return true;
        }
        return false;
    }

    public JavaMailSenderImpl getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getAllowedIps() {
        return allowedIps;
    }

    public void setAllowedIps(List<String> allowedIps) {
        this.allowedIps = allowedIps;
    }

    public List<String> getAllowedEmails() {
        return allowedEmails;
    }

    public void setAllowedEmails(List<String> allowedEmails) {
        this.allowedEmails = allowedEmails;
    }

    public boolean isSend() {
        return send;
    }

    public void setSend(boolean send) {
        this.send = send;
    }

    public boolean isUseIpFilter() {
        return useIpFilter;
    }

    public void setUseIpFilter(boolean useIpFilter) {
        this.useIpFilter = useIpFilter;
    }

    public boolean isUseEmailFilter() {
        return useEmailFilter;
    }

    public void setUseEmailFilter(boolean useEmailFilter) {
        this.useEmailFilter = useEmailFilter;
    }

    public boolean isSendInThread() {
        return sendInThread;
    }

    public void setSendInThread(boolean sendInThread) {
        this.sendInThread = sendInThread;
    }

    public int getMailSenderPoolSize() {
        return mailSenderPoolSize;
    }

    public void setMailSenderPoolSize(int mailSenderPoolSize) {
        this.mailSenderPoolSize = mailSenderPoolSize;
    }

    public int getMailSenderPoolCount() {
        return mailSenderPoolCount;
    }

    public void setMailSenderPoolCount(int mailSenderPoolCount) {
        this.mailSenderPoolCount = mailSenderPoolCount;
    }
}
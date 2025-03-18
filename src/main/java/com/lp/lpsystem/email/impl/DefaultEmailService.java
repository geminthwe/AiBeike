package com.lp.lpsystem.email.impl;

import com.lp.lpsystem.email.EmailConfig;
import com.lp.lpsystem.email.Email;
import com.lp.lpsystem.email.EmailService;
import com.sun.mail.smtp.SMTPSSLTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.annotation.Resource;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DefaultEmailService implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(DefaultEmailService.class);
    private final ExecutorService sender = Executors.newScheduledThreadPool(1);

    @Resource
    private EmailConfig emailConfig;

    @Override
    public void send(Email email) {

        SMTPSSLTransport t = null;
        try {
            Properties prop = System.getProperties();
            Session session = Session.getInstance(prop, null);

            email.setSenderEmailAddress(emailConfig.getSender());

            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(email.getSenderEmailAddress()));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipientsString(), false));
            msg.setSubject(email.getSubject());
            msg.setDataHandler(new DataHandler(new HTMLDataSource(email.getBody())));

            String host = emailConfig.getHost();
            String sender = emailConfig.getSender();
            String password = emailConfig.getPassword();

            t = (SMTPSSLTransport) session.getTransport(emailConfig.getProtocol());
            t.connect(host, sender, password);
            msg.saveChanges();
            t.sendMessage(msg, msg.getAllRecipients());
            logger.debug("email response: {}", t.getLastServerResponse());
        } catch (Exception e) {
            logger.error("send email failed.", e);
        } finally {
            if (t != null) {
                try {
                    t.close();
                } catch (Exception e) {
                    // nothing
                }
            }
        }
    }

    static class HTMLDataSource implements DataSource {

        private final String html;

        HTMLDataSource(String htmlString) {
            html = htmlString;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (html == null) {
                throw new IOException("html message is null!");
            }
            return new ByteArrayInputStream(html.getBytes());
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new IOException("This DataHandler cannot write HTML");
        }

        @Override
        public String getContentType() {
            return "text/html";
        }

        @Override
        public String getName() {
            return "HTMLDataSource";
        }
    }
}

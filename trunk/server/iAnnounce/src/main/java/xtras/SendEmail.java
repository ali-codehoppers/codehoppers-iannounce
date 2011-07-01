/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package xtras;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




/**
 *
 * @author Wasae Shoaib
 */
public class SendEmail {

    private static final String SMTP_SERVER = "mail.datamedusa.com";
    private static final String USER_NAME = "eservices@datamedusa.com";
    private static final String PASSWORD = "datamed";
    private static final String FROM = "noreply@iAnnounce.com";

   public SendEmail(String recipient, String message, String subject) throws AddressException, MessagingException {
        boolean debug = false;

        //Set the host smtp address
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_SERVER);
        properties.put("mail.smtp.auth", "true");

        Authenticator authenticator = new SMTPAuthenticator();
        Session session = Session.getDefaultInstance(properties, authenticator);

        session.setDebug(debug);

        // create a message
        Message msg = new MimeMessage(session);

        // set the from and to address
        InternetAddress addressFrom = new InternetAddress(FROM);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[1];
        addressTo[0] = new InternetAddress(recipient);

        msg.setRecipients(Message.RecipientType.TO, addressTo);


        // Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }

    private class SMTPAuthenticator extends javax.mail.Authenticator {

        @Override
        public PasswordAuthentication getPasswordAuthentication() {
            String username = USER_NAME;
            String password = PASSWORD;
            return new PasswordAuthentication(username, password);
        }
    }
}

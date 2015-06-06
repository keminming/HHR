package tool;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    // Sender's email ID needs to be mentioned
    private String from = "keminming@gmail.com";

    Session session;

    // Get system properties
    private Properties properties = new Properties();
    
	private static EmailSender instance = new EmailSender();
	
	public static void setInstance(EmailSender instance) {
		EmailSender.instance = instance;
	}

	public static EmailSender getInstance(){
		return instance;
	}
    
    private EmailSender(){
    	// Setup mail server
    	properties.put("mail.smtp.host", "smtp.gmail.com");
    	properties.put("mail.smtp.socketFactory.port", "465");
    	properties.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
    	properties.put("mail.smtp.auth", "true");
    	properties.put("mail.smtp.port", "465");

        //Get the default Session object.
        session = Session.getDefaultInstance(properties,
    			new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("keminming","@A*U%S993knc");
			}
		});
    }

    public void send(String language,String content, List<String> recipients) throws AddressException, MessagingException{
        	recipients.add("keminming@gmail.com");
        	recipients.add("BobAchgill@hotmail.com");
        	// Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            for(String recipient : recipients){
            	if(!"".equals(recipient))
            		message.addRecipient(Message.RecipientType.TO,
                        new InternetAddress(recipient));
            }

            // Set Subject: header field
            message.setSubject("HHR Updates - " + language);

            // Now set the actual message
            content = content.replaceAll("\r\n","<br>");
            message.setContent(content, "text/html;charset=utf-8");
            //message.setText(content);
            // Send message
            Transport.send(message);
            //System.out.println("Email sent, please check with your email account for updates.");
    }
    
    public static void main(String[] args){
    	EmailSender sender = new EmailSender();
    	try {
			sender.send("abc","first email",null);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

package io.jopen.core.common;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * @author maxuefeng [m17793873123@163.com]
 */
public class EmailSender {


	Properties props;

	String smtpHost;
	String smtpPort;
	String username;
	String password;


	/**
	 * @param smtpHost
	 * @param smtpPort
	 * @param username
	 * @param password
	 */
	public EmailSender(String smtpHost, String smtpPort, String username, String password) {

		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.username = username;
		this.password = password;

		/**
		 *
		 */
		//Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

		// Get a Properties object
		props = System.getProperties();
		props.setProperty("mail.smtps.host", smtpHost);
		props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", smtpPort);
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);
		props.setProperty("mail.smtps.auth", "true");
		
		/*
		If urls to false, the QUIT command is sent and the connection is immediately closed. If urls
		to true (the default), causes the transport to wait for the response to the QUIT command.
		
		ref :   http://java.sun.com/products/javamail/javadocs/com/sun/mail/smtp/package-summary.html
		        http://forum.java.sun.com/thread.jspa?threadID=5205249
		        smtpsend.java - demo program from javamail
		*/
		props.put("mail.smtps.quitwait", "false");
	}

	/**
	 * Send email using SMTP server
	 *
	 * @param recipient_email
	 * @param title
	 * @param html_msg
	 * @throws AddressException   if the email address parse failed
	 * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
	 */
	public void send(
			String recipient_email,
			String title,
			String html_msg
	)
			throws AddressException, MessagingException {

		String[] recipient_emails = {recipient_email};

		send(recipient_emails, new String[0], title, html_msg);
	}

	/**
	 * Send email using SMTP server
	 *
	 * @param recipient_emails
	 * @param cc_emails
	 * @param title
	 * @param html_msg
	 * @throws AddressException   if the email address parse failed
	 * @throws MessagingException if the connection is dead or not in the connected state or if the message is not a MimeMessage
	 */
	public void send(
			String[] recipient_emails,
			String[] cc_emails,
			String title,
			String html_msg
	)
			throws AddressException, MessagingException {

		Session session = Session.getInstance(props, null);

		// -- Create a new message --
		final MimeMessage msg = new MimeMessage(session);

		// -- Set the FROM and TO fields --
		msg.setFrom(new InternetAddress(username));

		for (String recipient : recipient_emails) {
			if (recipient.length() > 0)
				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
		}

		for (String recipient : cc_emails) {
			if (recipient.length() > 0)
				msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(recipient, false));
		}

		msg.setSubject(title);
		msg.setContent(html_msg, "text/html;charset=UTF-8");
		msg.setSentDate(new Date());

		SMTPTransport t = (SMTPTransport) session.getTransport("smtps");

		t.connect(smtpHost, username, password);
		t.sendMessage(msg, msg.getAllRecipients());
		t.close();
	}
}

package carvalho.danilo;

import java.io.File;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {

    public static void main(String[] args) {
        try {
            System.out.println("ATUALIZANDO BRANCH ITAU git");
            sendEmail();
            System.out.println("NOVA ALTERAÇÃO NO BRANCH ITAU");

            System.out.println(new String(FileUtility.getBytesOfFile(new File("email-template.html")), Charset.forName("UTF-8")));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings({ "resource" })
    public static void sendEmail() {
        try {
            Scanner scan = new Scanner(System.in);

            //            String from = "danilo@flexdoc.com.br";
            //            String to = "danilogga@gmail.com";
            //            String pwd = "Dan215600";
            //            String server = "mail.flexdoc.com.br";
            //            String port = "587";

            System.out.println("De: ");
            String from = scan.nextLine();

            System.out.println("Para: ");
            String to = scan.nextLine();

            System.out.println("Senha: ");
            String pwd = scan.nextLine();

            System.out.println("Server: ");
            String server = scan.nextLine();

            System.out.println("Porta: ");
            String port = scan.nextLine();

            System.out.println("De >> [" + from + "]");
            System.out.println("Para >> [" + to + "]");
            System.out.println("Senha >> [" + pwd + "]");
            System.out.println("Servidor >> [" + server + "]");
            System.out.println("Porta >> [" + port + "]");

            Properties props = System.getProperties();
            props.put("mail.smtp.starttls.enable", false); // added this line
            props.put("mail.smtp.host", server);
            props.put("mail.smtp.user", from);
            props.put("mail.smtp.password", pwd);
            props.put("mail.smtp.port", port);
            //            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.auth", true);

            Session session = Session.getInstance(props, null);
            MimeMessage message = new MimeMessage(session);

            // Create the email addresses involved
            InternetAddress fromAddress = new InternetAddress(from, "FlexPs");
            message.setSubject("teste email [" + Calendar.getInstance().getTimeInMillis() + "]");
            message.setFrom(fromAddress);
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            String htmlMessage = new String(FileUtility.getBytesOfFile(new File("email-template.html")), Charset.forName("UTF-8"));
            messageBodyPart.setContent(htmlMessage, "text/html;charset=UTF-8");

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart();
            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect(server, from, pwd);
            System.out.println("Transport: " + transport.toString());
            transport.sendMessage(message, message.getAllRecipients());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

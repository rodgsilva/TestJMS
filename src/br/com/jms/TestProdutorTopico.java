package br.com.jms;

import java.io.StringWriter;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.xml.bind.JAXB;

import br.com.jms.modelo.Pedido;
import br.com.jms.modelo.PedidoFactory;

public class TestProdutorTopico {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {

		InitialContext context = new InitialContext();
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination topico = (Destination) context.lookup("loja");
		MessageProducer producer = session.createProducer(topico);

		Pedido pedido = new PedidoFactory().geraPedidoComValores();

//	Quando preciso mandar para outro servidor utilizando o XML TODO
//		StringWriter writer = new StringWriter();
//		JAXB.marshal(pedido, writer);
//		String xml = writer.toString();
//		System.out.println(xml);

// Pode ser feito via objeto qundo for de java para java 
		
		Message message = session.createObjectMessage(pedido);
		message.setBooleanProperty("ebook",false);
		producer.send(message);

		// Revebe somente um TODO
//		Message message = consumer.receive();
//		System.out.println("Recebendo msg:" + message);

		new Scanner(System.in).nextLine();

		session.close();
		connection.close();
		context.close();
	}

}

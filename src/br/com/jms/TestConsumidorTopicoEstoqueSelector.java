package br.com.jms;

import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.InitialContext;

import br.com.jms.modelo.Pedido;

public class TestConsumidorTopicoEstoqueSelector {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
	
		InitialContext context = new InitialContext();		
		ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
		
		Connection connection= factory.createConnection();
		connection.setClientID("estoque");
		
		connection.start();		
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 	
		
		Topic topico = (Topic) context.lookup("loja");
																		// Tem como incluir ebook is null and ebook=false
		MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura","ebook=false",false);
		
		consumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				ObjectMessage objectMessage = (ObjectMessage) message;
				
				try {
					Pedido pedido = (Pedido) objectMessage.getObject();
					System.out.println(pedido.toString());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
//	Quando utiliza texto e XML usa esta	
			
//			@Override
//			public void onMessage(Message message) {
//				TextMessage textMessage = (TextMessage) message;
//				
//				try {
//					System.out.println(textMessage.getText());
//				} catch (JMSException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
//			}
	
		});
			
		
		
		//Revebe somente um TODO
//		Message message = consumer.receive();
//		System.out.println("Recebendo msg:" + message);
		
		new Scanner(System.in).nextLine();
		
		session.close();
		connection.close();
		context.close();
	}

}

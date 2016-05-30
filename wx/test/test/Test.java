package test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import dswork.wx.model.msg.Msg;

public class Test
{
	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException
	{
		InputStream inputStream = new FileInputStream("e://a.txt");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.parse(inputStream);
		String type = Msg.readMsgType(document);
		System.out.println(type);
		Msg msg = new Msg(document);
		System.out.println(msg.getContent());
	}
}

package src.getinfo;

import soap.InfoQuery;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Result;

@WebServlet("/GetInfo")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetInfo() {
		super();
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter outWriter = response.getWriter();
		response.setContentType("text/xml");
		response.setCharacterEncoding("utf-8");

		String id = request.getParameter("id");

		if (id != null) {
			// id exists
			try {
				SOAPMessage soapMessage = MessageFactory.newInstance()
						.createMessage();
				SOAPBody body = soapMessage.getSOAPBody();
				SOAPFactory factory = SOAPFactory.newInstance();

				InfoQuery infoQuery = new InfoQuery();
				String[] results = infoQuery.getInfo(id).split(" ");

				if (results.length > 2) {
					body.addNamespaceDeclaration("",
							"http://jw.nju.edu.cn/schema");
					body.addNamespaceDeclaration("xsi",
							"http://www.w3.org/2001/XMLSchema-instance");
					body.addAttribute(factory.createName("xsi:schemaLocation"),
							"http://jw.nju.edu.cn/schema Student.xsd");
					
					SOAPElement studentXML = body.addChildElement("StudentXML");
					SOAPElement student = studentXML.addChildElement("student");
					student.addChildElement("id").setValue(results[0]);
					student.addChildElement("name").setValue(results[1]);
					student.addChildElement("credit").setValue(
							results[2].split(":")[1]);
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		    		soapMessage.writeTo(baos);
		    		String soap=baos.toString();
		    		
		    		outWriter.print(soap);
				} else {
					body.addFault(factory.createName("F"), results[0]+" id.");

					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					soapMessage.writeTo(baos);
					String soap = baos.toString();

					outWriter.print(soap);
				}
			} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// id not exist
			try {
				SOAPMessage soapMessage = MessageFactory.newInstance()
						.createMessage();
				SOAPBody body = soapMessage.getSOAPBody();
				SOAPFactory s = SOAPFactory.newInstance();
				body.addFault(s.createName("F"), "id not exist");

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				soapMessage.writeTo(baos);
				String soap = baos.toString();

				outWriter.print(soap);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}

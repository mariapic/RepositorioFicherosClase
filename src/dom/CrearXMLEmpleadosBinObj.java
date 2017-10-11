package dom;

import java.io.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.*;

public class CrearXMLEmpleadosBinObj {

	public static void main(String[] args) {
		ObjectInputStream ois = null;
				
		try {
			
			ois = new ObjectInputStream(new FileInputStream("empleadosObj.dat"));
			
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			DOMImplementation implementacion = db.getDOMImplementation();
			
			// Creamos un documento vacío de nombre doc con el nodo 
			// raíz de nombre empleados y asignamos la versión del XML
			Document doc = implementacion.createDocument(null, "empleados", null);
			doc.setXmlVersion("1.0");

			// declaramos las variables que voy a utilizar en el while
			Empleado emp = null;
			Element elemento = null;
			
			try {
				while ((emp = (Empleado) ois.readObject()) != null) {
					System.out.println("ID: " + emp.getId() 
										+ " Nombre: " + emp.getNombre() 
										+ " Depto: " + emp.getDep() 
										+ " Salario: " + emp.getSalario());
					// creamos un nodo o elemento de nombre empleado
					elemento = doc.createElement("empleado");   
					doc.getDocumentElement().appendChild(elemento);
					
					crearElementoHijo(doc, elemento, "id", (emp.getId()+""));
					crearElementoHijo(doc, elemento, "nombre", emp.getNombre());
					crearElementoHijo(doc, elemento, "departamento", emp.getDep()+"");
					crearElementoHijo(doc, elemento, "salario", emp.getSalario()+"");
					
				}
			} catch (EOFException e) {
				System.out.println("Fin del fichero");
			} 
			
			// Creamos la fuente XML a partir del documento
			Source source = new DOMSource(doc);
			// Creamos el resultado en el fichero empleados.xml
			Result result = new StreamResult(new File("empleados.xml"));
			
			// Obtenemos un TransformerFactory
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
			// Le damos formato y realizamos la transformación del documento a fichero
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.METHOD, "xml"); 
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.transform(source,  result); 
			
			// Mostramos el documento por pantalla especificando como canal de salida el System.out
			Result console = new StreamResult(System.out);
			transformer.transform(source, console);


			
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el fichero");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al leer el fichero");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (ois != null) {
				try {
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	}

	/*
	 * metodo crearElementoHijo recibe el doc y el elemento al que va a añadir
	 * el elemento hijo, el nombre de la etiqueta correspondiente al elemento hijo
	 * y el contenido del elemento hijo
	 * Este método nos sirve para elementos finales
	 */
	public static void crearElementoHijo(Document doc, Element elemento, 
			String nomElemento, String valor) {
		// crea el elemento hijo
		Element elemHijo = doc.createElement(nomElemento);
		// crea el texto correspondiente al contenido
		Text text = doc.createTextNode(valor);
		// asigna el elemento hijo al padre
		elemento.appendChild(elemHijo);
		// asigna el contenido texto al elemento hijo
		elemHijo.appendChild(text);
	}

}

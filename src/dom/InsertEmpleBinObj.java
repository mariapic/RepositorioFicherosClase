package dom;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class InsertEmpleBinObj {

	public static void main(String[] args) {
		String[] nombres = {"Alberto", "Guillermo", "Alejandro", "Ana", "Patricia"};
		int[] departamentos = {10, 20, 30, 20, 10};
		double[] salarios = {2000.00, 1500.50, 3000.40, 2300.60, 1900.10};
		
		ObjectOutputStream oos = null;
		
		try {
			oos = new ObjectOutputStream(new FileOutputStream("empleadosObj.dat"));
			
			Empleado emp = null;
			
			for (int i = 0; i < salarios.length; i++) {
				emp = new Empleado((i+1), nombres[i], departamentos[i], salarios[i]);
				oos.writeObject(emp);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("No se ha encontrado el fichero");
		} catch (IOException e) {
			System.out.println("Error de entrada/salida");
		} finally {
			if (oos != null) {
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}

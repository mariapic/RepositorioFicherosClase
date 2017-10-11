package dom;

import java.io.Serializable;

public class Empleado implements Serializable {

	private static final long serialVersionUID = 4214973097459329946L;
	
	private int id;
	private String nombre;
	private int dep;
	private double salario;
	
	public Empleado(int id, String nombre, int dep, double salario) {
		this.id = id;
		this.nombre = nombre;
		this.dep = dep;
		this.salario = salario;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public int getDep() {
		return dep;
	}

	public double getSalario() {
		return salario;
	}
	
	
	
	

}

package application.mercadona.crud;

import java.sql.Date;

public class Product 
{
	private int id;
	private String name;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	
	public Product(int id, String name, Date fechaCreacion, Date fechaActualizacion) {
		super();
		this.id = id;
		this.name = name;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
}

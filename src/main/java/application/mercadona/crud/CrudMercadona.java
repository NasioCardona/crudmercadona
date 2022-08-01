package application.mercadona.crud;

import java.sql.SQLException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import io.agroal.api.AgroalDataSource;

@Path("/crud-mercadona")
public class CrudMercadona {

	@Inject
	AgroalDataSource defaultDataSource;

	@Inject
	CrudMercadonaService crudMercadonaService;

	@GET
	@Path("/get-product-list")
	@Produces(MediaType.APPLICATION_JSON)
	public String RecuperarListadoProductos() throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(crudMercadonaService.recuperarListadoProductos());
	}

	@GET
	@Path("/get-category-list")
	@Produces(MediaType.APPLICATION_JSON)
	public String RecuperarListadoCategorias() throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(crudMercadonaService.recuperarListadoCategorias());
	}

	@GET
	@Path("/get-detail-products")
	@Produces(MediaType.APPLICATION_JSON)
	public String RecuperarDetalleProductos() throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(crudMercadonaService.recuperarDetalleProductos());
	}

	@GET
	@Path("/get-detail-product-by-id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String RecuperarDetalleProductosById(@PathParam("id") int productId) throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		return gson.toJson(crudMercadonaService.recuperarDetalleProductosById(productId));
	}

	/*
	 * EXAMPLE BODY NEW PRODUCT { "idcategory": 2, "name":"rosquilles 2", "price":
	 * 1.1, "stock": 2 }
	 */
	@POST
	@Path("/new-product/")
	@Produces(MediaType.APPLICATION_JSON)
	public String UnsertNewProduct(@RequestBody() String requestBody) throws SQLException {

		JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		if (crudMercadonaService.insertProduct(jsonObject.get("idcategory").getAsInt(),
				jsonObject.get("name").getAsString(), jsonObject.get("stock").getAsInt(),
				jsonObject.get("price").getAsFloat()))
			return gson.toJson("PRODUCTO INSERTADO CORRECTAMENTE");
		else
			return gson.toJson("PRODUCTO  NO SE HA PODIDO INSERTAR");
	}

	/*
	 * EXAMPLE BODY UPDATE PRODUCT { "idproduct": 1, "idcategory": 2,
	 * "name":"rosquilles 2", "price": 1.1, "stock": 2 }
	 */
	@POST
	@Path("/update-product/")
	@Produces(MediaType.APPLICATION_JSON)
	public String UpdateProduct(@RequestBody() String requestBody) throws SQLException {

		JsonObject jsonObject = new JsonParser().parse(requestBody).getAsJsonObject();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		if (crudMercadonaService.updateProduct(jsonObject.get("idproduct").getAsInt(),
				jsonObject.get("idcategory").getAsInt(), jsonObject.get("name").getAsString(),
				jsonObject.get("stock").getAsInt(), jsonObject.get("price").getAsFloat()))
			return gson.toJson("PRODUCTO: " + jsonObject.get("idproduct").getAsInt() + " ACTUALIZADO CORRECTAMENTE");
		else
			return gson.toJson("PRODUCTO: " + jsonObject.get("idproduct").getAsInt() + " NO SE HA PODIDO ACTUALIZAR");
	}

	@POST
	@Path("/insert-category/{name}")
	@Produces(MediaType.APPLICATION_JSON)
	public String InsertCategory(@PathParam("name") String name) throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		if (crudMercadonaService.insertCategory(name))
			return gson.toJson("CATEGORIA: " + name + " INSERTADA CORRECTAMENTE");
		else
			return gson.toJson("CATEGORIA: " + name + " NO SE HA PODIDO INSERTAR");

	}

	@POST
	@Path("/delete-product-by-id/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String EliminarProductosById(@PathParam("id") int productId) throws SQLException {

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();

		if (crudMercadonaService.deleteProduct(productId))
			return gson.toJson("PRODUCTO: " + productId + " ELIMINADO CORRECTAMENTE");
		else
			return gson.toJson("PRODUCTO: " + productId + " NO SE HA PODIDO ELIMINAR");

	}

}
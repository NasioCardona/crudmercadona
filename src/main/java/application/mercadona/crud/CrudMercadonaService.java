package application.mercadona.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import com.google.gson.Gson;
import io.agroal.api.AgroalDataSource;

@ApplicationScoped
public class CrudMercadonaService {
	@Inject
	AgroalDataSource defaultDataSource;

	final String BODY_NEW_PRODUCT = "{" + "	\"idcategory\": 2," + "	\"name\":\"rosquilles 2\"," + "	\"price\": 1.1,"
			+ "	\"stock\": 2" + "}";

	public ArrayList<Product> recuperarListadoProductos() throws SQLException {

		ArrayList<Product> listaProductos = new ArrayList<Product>();
		try (Connection con = defaultDataSource.getConnection()) {
			PreparedStatement pstm = con.prepareStatement("Select * from products");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Product p = new Product(rs.getInt("id"), rs.getString("name"), rs.getDate("d_create"),
						rs.getDate("d_update"));
				listaProductos.add(p);
			}
		}

		return listaProductos;
	}

	public ArrayList<Category> recuperarListadoCategorias() throws SQLException {

		ArrayList<Category> listaCategorias = new ArrayList<Category>();
		try (Connection con = defaultDataSource.getConnection()) {
			PreparedStatement pstm = con.prepareStatement("Select * from categories");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				Category c = new Category(rs.getInt("id"), rs.getString("name"));
				listaCategorias.add(c);
			}
		}

		return listaCategorias;
	}

	public ArrayList<DetailProduct> recuperarDetalleProductos() throws SQLException {
		ArrayList<DetailProduct> listaDetalleProductos = new ArrayList<DetailProduct>();

		try (Connection con = defaultDataSource.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(
					"select p.id id_product, p.name, p.d_create, p.d_update, c.id id_category, c.name category_name, d.stock, d.price from"
							+ " products p, categories c, detail d "
							+ " where d.id_categories = c.id and d.id_product = p.id order by category_name");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Product p = new Product(rs.getInt("id_product"), rs.getString("name"), rs.getDate("d_create"),
						rs.getDate("d_update"));
				Category c = new Category(rs.getInt("id_category"), rs.getString("category_name"));

				DetailProduct dp = new DetailProduct(rs.getInt("stock"), rs.getFloat("price"), p, c);

				listaDetalleProductos.add(dp);
			}
		}

		return listaDetalleProductos;
	}

	public ArrayList<DetailProduct> recuperarDetalleProductosById(int productId) throws SQLException {
		ArrayList<DetailProduct> listaDetalleProductos = new ArrayList<DetailProduct>();

		try (Connection con = defaultDataSource.getConnection()) {
			PreparedStatement pstm = con.prepareStatement(
					"select p.id id_product, p.name, p.d_create, p.d_update, c.id id_category, c.name category_name, d.stock, d.price from"
							+ " products p, categories c, detail d "
							+ " where d.id_categories = c.id and d.id_product = p.id  and id_product = " + productId
							+ " order by category_name");

			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {

				Product p = new Product(rs.getInt("id_product"), rs.getString("name"), rs.getDate("d_create"),
						rs.getDate("d_update"));
				Category c = new Category(rs.getInt("id_category"), rs.getString("category_name"));

				DetailProduct dp = new DetailProduct(rs.getInt("stock"), rs.getFloat("price"), p, c);

				listaDetalleProductos.add(dp);
			}
		}

		return listaDetalleProductos;
	}

	public String recuperarCategoryById(int categoryId) throws SQLException {
		String name = "";
		try (Connection con = defaultDataSource.getConnection()) {
			PreparedStatement pstm = con.prepareStatement("select name from categories where id = ?");
			pstm.setInt(1, categoryId);
			ResultSet rs = pstm.executeQuery();

			while (rs.next()) {
				name = rs.getString("name");
			}
		}

		return name;
	}

	public boolean insertProduct(int idCategory, String name, int stock, float price) throws SQLException {
		boolean result = false;
		try (Connection con = defaultDataSource.getConnection()) {
			// INSERCIÓN PRODUCTO
			int last_inserted_id = -1;
			con.setAutoCommit(true);
			PreparedStatement pstm = con.prepareStatement("Insert into products (name) values (?)",
					Statement.RETURN_GENERATED_KEYS);
			pstm.setString(1, name);
			pstm.executeUpdate();

			ResultSet rs = pstm.getGeneratedKeys();
			if (rs.next())
				last_inserted_id = rs.getInt(1);

			// INSERCIÓN DETALLE PRODUCTO
			pstm = con
					.prepareStatement("Insert into detail (id_product, id_categories, stock, price) values (?,?,?,?)");
			pstm.setInt(1, last_inserted_id);
			pstm.setInt(2, idCategory);
			pstm.setInt(3, stock);
			pstm.setFloat(4, price);

			pstm.executeUpdate();
			result = true;
		}

		return result;
	}

	public boolean updateProduct(int idProduct, int idCategory, String name, int stock, float price)
			throws SQLException {
		boolean result = false;
		try (Connection con = defaultDataSource.getConnection()) {
			// ACTUALIZACIÓN PRODUCTO

			con.setAutoCommit(true);
			PreparedStatement pstm = con.prepareStatement("Update products set name = ? where id = ?");
			pstm.setString(1, name);
			pstm.setInt(2, idProduct);
			pstm.executeUpdate();

			// ACTUALIZACIÓN DETALLE PRODUCTO
			pstm = con
					.prepareStatement("Update detail set id_categories = ?, stock = ?, price = ? where id_product = ?");
			pstm.setInt(1, idCategory);
			pstm.setInt(2, stock);
			pstm.setFloat(3, price);
			pstm.setInt(4, idProduct);

			pstm.executeUpdate();
			result = true;
		}

		return result;
	}

	public boolean deleteProduct(int idProduct) throws SQLException {
		boolean result = false;
		try (Connection con = defaultDataSource.getConnection()) {
			// ELIMINAR DE DETALLE PRODUCTO
			con.setAutoCommit(true);
			PreparedStatement pstm = con.prepareStatement("Delete from detail where id_product = ?");
			pstm.setInt(1, idProduct);
			pstm.executeUpdate();

			// ELIMINAR DE PRODUCTO
			pstm = con.prepareStatement("Delete from products where id = ?");
			pstm.setInt(1, idProduct);
			pstm.executeUpdate();

			result = true;
		}

		return result;
	}

	public boolean insertCategory(String name) throws SQLException {
		boolean result = false;
		try (Connection con = defaultDataSource.getConnection()) {
			// INSERCIÓN CATEGORIA

			con.setAutoCommit(true);
			PreparedStatement pstm = con.prepareStatement("Insert into categories (name) VALUES (?)");
			pstm.setString(1, name);
			pstm.executeUpdate();
			result = true;
		}

		return result;
	}

}

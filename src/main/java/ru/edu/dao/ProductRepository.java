package ru.edu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

	private final Connection connection;

	public ProductRepository(Connection connection) {
		this.connection = connection;
	}

	public void createTable() {
		String sql = "create table if not exists product ("
				+ "    id varchar(32),"
				+ "    name varchar(32),"
				+ "    manufacture_date varchar(32)"
				+ ")";
		try (Statement statement = connection.createStatement()) {
			int affectedRows = statement.executeUpdate(sql);
			System.out.println("createTable completed affectedRows=" + affectedRows);
		} catch (Exception e) {
			throw new RuntimeException("Failed to .createTable");
		}
	}

	public void dropTable() {
		try (Statement statement = connection.createStatement()) {
			int affectedRows = statement.executeUpdate("DROP TABLE IF EXISTS product;");
			System.out.println("dropTable completed affectedRows=" + affectedRows);
		} catch (Exception e) {
			throw new RuntimeException("Failed to .dropTable");
		}
	}

	public void addProduct(Product product) {
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("insert into product ('id','name','manufacture_date') values ('%s', '%s', '%s')",
					product.getId(), product.getName(), product.getManufactureDate().toString()
			);
			boolean hasResultSet = statement.execute(sql);
			int affectedRows = statement.getUpdateCount();
			System.out.println("addProduct completed hasResultSet=" + hasResultSet + " affectedRows=" + affectedRows);
		} catch (Exception e) {
			throw new RuntimeException("Failed to .addProduct");
		}
	}

	public void changeName(String id, String newName) {
		try (Statement statement = connection.createStatement()) {
			int affectedRows = statement.executeUpdate("update product set name='" + newName + "' where id='" + id + "'");
			System.out.println("changeName completed affectedRows=" + affectedRows);
		} catch (Exception e) {
			throw new RuntimeException("Failed to .findById");
		}
	}

	public List<Product> findAll() {
		try (Statement statement = connection.createStatement()) {
			try (ResultSet resultSet = statement.executeQuery("select * from product")) {

				List<Product> productList = new ArrayList<>();
				while (resultSet.next()) {
					String id = resultSet.getString("id");
					String name = resultSet.getString("name");
					LocalDate manufactureDate = LocalDate.parse(resultSet.getString("manufacture_date"));
					Product product = new Product(id, name, manufactureDate);
					productList.add(product);
				}

				return productList;
			} catch (Exception e) {
				throw new RuntimeException("Failed to .findAll");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to .findAll");
		}
	}

	public Product findById(String productId) {
		try (Statement statement = connection.createStatement()) {
			boolean hasResultSet = statement.execute("select * from product where id='" + productId + "'");
			try (ResultSet resultSet = statement.getResultSet()) {

				if (!resultSet.next()) {
					return null;
				}

				String id = resultSet.getString("id");
				String name = resultSet.getString("name");
				LocalDate manufactureDate = LocalDate.parse(resultSet.getString("manufacture_date"));

				System.out.println("findById completed hasResultSet=" + hasResultSet);

				return new Product(id, name, manufactureDate);
			} catch (Exception e) {
				throw new RuntimeException("Failed to .findById");
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to .findById");
		}
	}

	public void deleteById(String id) {
//		Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
//		String sql = "delete from product where id='" + id + "'";
		try (PreparedStatement statement = connection.prepareStatement("delete from product where id=?")) {
			statement.setString(1, id);
			int affectedRows = statement.executeUpdate();
			System.out.println("deleteById completed affectedRows=" + affectedRows);
		}  catch (Exception e) {
			throw new RuntimeException("Failed to .deleteById");
		}
	}

}

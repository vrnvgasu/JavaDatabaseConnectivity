package ru.edu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.List;
import ru.edu.dao.Product;
import ru.edu.dao.ProductRepository;

/**
 * Hello world!
 */
public class App {

	public static void main(String[] args) {
		String jdbcUrl = "jdbc:sqlite:/Users/dmitrii/code/edu/java/JavaDatabaseConnectivity/sqlite.db";

		try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
			ProductRepository repository = new ProductRepository(connection);
			repository.createTable();

			for (int i = 0; i < 5; i++) {
				int month = ((int) (Math.random() * 100) % 12 + 1);
				Product product1 = new Product(String.valueOf(i), "product_" + i, LocalDate.of(2020, month, 1));
				repository.addProduct(product1);
			}

			repository.changeName("2", "new product 2");

			List<Product> productList = repository.findAll();
			productList.forEach(System.out::println);

			Product product = repository.findById("2");
			System.out.println(product);

			/*sql injection*/
			repository.deleteById("2'; delete from product where id <> '");
			repository.deleteById("2");
			repository.findAll().forEach(System.out::println);

			repository.dropTable();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}

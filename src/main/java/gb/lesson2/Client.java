package gb.lesson2;

import gb.lesson2.impl.CartImpl;
import gb.lesson2.impl.ProductImpl;
import gb.lesson2.impl.ProductRepositoryImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {

        final String ANSI_RESET = "\u001B[0m";
        final String ANSI_RED = "\u001B[31m";
        final String ANSI_BLUE = "\u001B[34m";

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductRepository productRepository = context.getBean("productRepository", ProductRepositoryImpl.class);

        Product product = context.getBean("product", ProductImpl.class);
        product.init(productRepository, "Apple", 100l);
        product = context.getBean("product", ProductImpl.class);
        product.init(productRepository, "Orange", 110l);
        product = context.getBean("product", ProductImpl.class);
        product.init(productRepository, "Banana", 120l);
        product = context.getBean("product", ProductImpl.class);
        product.init(productRepository, "Strawberry", 130l);
        product = context.getBean("product", ProductImpl.class);
        product.init(productRepository, "Lemon", 140l);

        List<Cart> carts = new ArrayList();
        int lastCart = -1;

        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println(ANSI_BLUE + "\n Type the command..." + ANSI_RESET);
            String command = scanner.nextLine();

            if (command.toLowerCase().startsWith("/add ")) {
                String name = command.substring(command.indexOf("-n ") + 3, command.indexOf(" -p"));
                Long price = 0l;
                Integer value = 0;
                try {
                    price = Long.parseLong(command.substring(command.indexOf("-p ") + 3, command.indexOf(" -v")));
                    value = Integer.parseInt(command.substring(command.indexOf("-v ") + 3));
                } catch (NumberFormatException ex) {
                    System.out.printf(ANSI_RED + "illegal format for price or value" + ANSI_RESET);
                    continue;
                }
                if (lastCart < 0) {
                    Cart cart = context.getBean("cart", CartImpl.class);
                    cart.init(productRepository);
                    if (carts.add(cart)) {
                        lastCart = carts.size() - 1;
                    }
                }
                Cart cart = carts.get(lastCart);
                cart.addProduct(name, price, value);
                System.out.printf("Product added. Now in cart " + cart.getValue(name) + " of " + name);

            } else if (command.equals("/cart".toLowerCase())) {
                if (lastCart < 0)
                    System.out.println("No cart has been created");
                else
                    System.out.printf("Current cart has index " + lastCart);

            } else if (command.toLowerCase().startsWith("/change ")) {

                int index = -1;

                try {
                    index = Integer.parseInt(command.substring(command.indexOf("-i ") + 3));
                } catch (NumberFormatException ex) {
                    System.out.printf(ANSI_RED + "illegal format for index" + ANSI_RESET);
                    continue;
                }

                if (index > 0 && index < carts.size())
                    lastCart = index;
                else
                    System.out.println(ANSI_RED + "Index is out of bounds of the array" + ANSI_RESET);

            } else if (command.equals("/h".toLowerCase()) || command.equals("/help".toLowerCase())) {

                StringBuilder sb = new StringBuilder();
                sb.append("/add -n <name> -p <price> -v <value> - adds a new item to the cart"
                        + System.lineSeparator());
                sb.append("/cart                                - shows the current cart" + System.lineSeparator());
                sb.append("/change -i <index>                   - changes the current cart to the specified one"
                        + System.lineSeparator());
                sb.append("/end                                 - end of program execution" + System.lineSeparator());
                sb.append("/h, /help                            - display help message and exit"
                        + System.lineSeparator());
                sb.append("/list                                - show current cart" + System.lineSeparator());
                sb.append("/new                                 - create a new cart" + System.lineSeparator());
                sb.append("/product -n <name>                   - show information about product in current cart"
                        + System.lineSeparator());
                sb.append("/remove -n <name> -v <value>         - removes the specified quantity of goods from the " +
                        "current cart" + System.lineSeparator());
                sb.append("/show                                - shows all carts" + System.lineSeparator());

                System.out.println(sb);

            } else if (command.equals("/list".toLowerCase())) {

                if (carts.size() == 0) {
                    System.out.println("No cart has been created");
                    continue;
                }
                System.out.println(carts.get(lastCart).getList());

            } else if (command.equals("/new".toLowerCase())) {

                Cart cart = context.getBean("cart", CartImpl.class);
                cart.init(productRepository);
                if (carts.add(cart)) {
                    lastCart = carts.size() - 1;
                }
                System.out.println("New cart was created");

            } else if (command.toLowerCase().startsWith("/product ")) {

                if (carts.size() == 0) {
                    System.out.println("No cart has been created");
                    continue;
                }

                String name = command.substring(command.indexOf("-n ") + 3);
                System.out.println(carts.get(lastCart).getItem(name));

            } else if (command.toLowerCase().startsWith("/remove ")) {

                if (carts.size() == 0) {
                    System.out.println("No cart has been created");
                    continue;
                }

                String name = command.substring(command.indexOf("-n ") + 3, command.indexOf(" -v"));
                Integer value = 0;
                try {
                    value = Integer.parseInt(command.substring(command.indexOf("-v ") + 3));
                } catch (NumberFormatException ex) {
                    System.out.printf(ANSI_RED + "illegal format for value" + ANSI_RESET);
                    continue;
                }
                product = productRepository.findByName(name);
                if (product == null)
                    System.out.println("There is no product with a name " + name);
                else if (carts.get(lastCart).removeProduct(product.getID(), value))
                    System.out.println("Product " + name + " in the amount of " + value
                            + " pieces has been removed from the cart");
                else
                    System.out.println("There is no product in the list with a name " + name);

            } else if (command.equals("/show".toLowerCase())) {

                String result = "No cart has been created";
                for (int i = 0; i < carts.size(); i++) {
                    if (i == 0)
                        result = "There are " + carts.size() + " cards with the following indices: " + i;
                    else
                        result = result + ", " + i;
                }
                System.out.println(result);

            } else if (command.equals("/end".toLowerCase())) {
                System.out.println("End of program execution");
            } else {
                System.out.println(ANSI_RED + "Unknown command " + command + ". For get help type \"/h\"" + ANSI_RESET);
            }
        }
    }
}

package gb.Hibernate2.repository;

import gb.Hibernate2.model.Customer;
import gb.Hibernate2.model.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class CartRepository {

    private static SessionFactory factory;

    public CartRepository() {
        factory = new Configuration().configure("configs/hibernate.cfg.xml").buildSessionFactory();
    }

    public void newSale(Customer customer, Product product) {

        Session session = factory.getCurrentSession();
        session.beginTransaction();

        customer = session.get(Customer.class, customer.getId());
        if (!customer.getProducts().contains(product)) {
            customer.getProducts().add(product);
        }
        session.getTransaction().commit();

    }

}

package gb.Hibernate2.repository;

import gb.Hibernate2.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository {

    private static SessionFactory factory;

    public CustomerRepository() {
        factory = new Configuration().configure("configs/hibernate.cfg.xml").buildSessionFactory();
    }

    public Customer getByID(int id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Customer customer = session.createNamedQuery("getByID", Customer.class).setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
            return customer;
        } catch (Exception exception) {
            return null;
        }
    }
    public List<Customer> getByName(String name) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Customer> customers = session.createNamedQuery("getByName", Customer.class).setParameter("name", name).getResultList();
            session.getTransaction().commit();
            return customers;
        } catch (Exception exception) {
            List<Customer> customers = new ArrayList<>();
            return customers;
        }
    }

    public void addNew(Customer customer) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    public static void shutdown() {
        factory.close();
    }

    public List<Customer> getAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Customer> customers = session.createQuery("from Customer").getResultList();
            session.getTransaction().commit();
            return customers;
        }
    }
}

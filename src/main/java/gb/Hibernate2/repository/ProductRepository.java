package gb.Hibernate2.repository;

import gb.Hibernate2.model.Product;
        import org.hibernate.Session;
        import org.hibernate.SessionFactory;
        import org.hibernate.cfg.Configuration;

        import java.util.List;

@org.springframework.stereotype.Repository
public class ProductRepository {

    private static SessionFactory factory;

    public ProductRepository() {
        factory = new Configuration().configure("configs/hibernate.cfg.xml").buildSessionFactory();
    }

    public Product getById(Integer id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            Product product = session.createNamedQuery("getById", Product.class).setParameter("id", id).getSingleResult();
            session.getTransaction().commit();
            return product;
        } catch (Exception exception) {
            return null;
        }
    }

    public List<Product> getAll() {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<Product> products = session.createQuery("from Product").getResultList();
            session.getTransaction().commit();
            return products;
        }
    }

    public void addNew(Product product) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public static void shutdown() {
        factory.close();
    }

    public void remove(int id) {

        Product product = getById(id);
        if (product == null)
            return;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(product);
            session.getTransaction().commit();
        }
    }

    public void updatePrice(int id, int cost) {
        Product product = getById(id);
        if (product == null)
            return;
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            product.setCost(cost);
            session.update(product);
            session.getTransaction().commit();
        }
    }
}
package db;

import model.PostData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

public class HbConnectionTest {

    private SessionFactory sessionFactory;

    @BeforeClass
    protected void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test(enabled = false)
    public void testHbConnection() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
//        List<PostData> result = session.createQuery("from PostData where deprecated = '0000-00-00'").list();
        List<PostData> result = session.createQuery("from PostData").list();
        for (PostData post : result) {
            System.out.println(post);
//            System.out.println(contact.getGroups());
        }
        session.getTransaction().commit();
        session.close();
    }

//    @Test
//    public void testHbConnectionGroupData() {
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//        List<GroupData> result = session.createQuery("from GroupData").list();
//        for (GroupData group : result) {
//            System.out.println(group);
//        }
//        session.getTransaction().commit();
//        session.close();
//    }

}

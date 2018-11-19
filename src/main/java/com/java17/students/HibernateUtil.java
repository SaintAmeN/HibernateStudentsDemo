package com.java17.students;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.boot.registry.internal.StandardServiceRegistryImpl;

// niezbędna jako punkt konfiguracyjny połączenia z hibernate'm
public class HibernateUtil {

    private static SessionFactory sessionFactory;

    //    private static final String costam = "awdawd";
    static {
        // Tworzymy sobie obiekt, który pobiera konfigurację z pliku hibernate cfg xml
        StandardServiceRegistry standardServiceRegistry =
                new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml").build();

        // metadata to informacje dotyczące plików. z danych załadowanych wcześniej
        // towrzymy sobie obiekt metadata.
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .getMetadataBuilder().build();

        // stworzenie sesji z danych zawartych w pliku hibernate cfg xml
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}

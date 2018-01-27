package ru.otus.hw12.webserver.dbService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw12.webserver.base.dataSets.UserDataSet;
import ru.otus.hw12.webserver.cache.CacheEngine;
import ru.otus.hw12.webserver.cache.CacheEngineImpl;
import ru.otus.hw12.webserver.dbService.dao.UserDataSetDAO;
import ru.otus.hw12.webserver.base.DBService;
import ru.otus.hw12.webserver.base.dataSets.AddressDataSet;
import ru.otus.hw12.webserver.base.dataSets.EmptyDataSet;
import ru.otus.hw12.webserver.base.dataSets.PhoneDataSet;
import ru.otus.hw12.webserver.cache.CacheElement;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DBServiceImpl implements DBService {
    private final SessionFactory sessionFactory;
    private CacheEngine userCache;
    private Map<String, Long> indexByName = new HashMap<>();

    public DBServiceImpl() {
        userCache = new CacheEngineImpl<Long, UserDataSet>(5, 0, 0, true);
        sessionFactory = createSessionFactory(setConfiguration());
    }

    public DBServiceImpl(CacheEngine userCache) {
        this.userCache = userCache;
        sessionFactory = createSessionFactory(setConfiguration());
    }

    private static Configuration setConfiguration(){
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(EmptyDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/db_example");
        configuration.setProperty("hibernate.connection.username", "test");
        configuration.setProperty("hibernate.connection.password", "test");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void save(UserDataSet dataSet) {
        runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.save(dataSet);
        });

        userCache.put(new SoftReference<>(new CacheElement<>(dataSet.getId(), dataSet)));
        indexByName.put(dataSet.getName(), dataSet.getId());
    }

    public UserDataSet read(long id) {
        SoftReference<CacheElement<Long, UserDataSet>> element = userCache.get(id);
        UserDataSet dataSet;
        if (element != null){
            dataSet = element.get().getValue();
        } else {
            dataSet = runInSession(session -> {
                UserDataSetDAO dao = new UserDataSetDAO(session);
                return dao.read(id);
            });
            userCache.put(new SoftReference<>(new CacheElement<>(dataSet.getId(), dataSet)));
            indexByName.put(dataSet.getName(), dataSet.getId());
        }
        return dataSet;
    }

    public UserDataSet readByName(String name) {
        Long id = indexByName.get(name);
        UserDataSet dataSet;
        SoftReference<CacheElement<String, UserDataSet>> element = userCache.get(id);
        if (element != null){
            dataSet = element.get().getValue();
        } else {
            dataSet =  runInSession(session -> {
                UserDataSetDAO dao = new UserDataSetDAO(session);
                return dao.readByName(name);
            });
            putCache(dataSet);
        }
        return dataSet;
    }

    public List<UserDataSet> readAll() {
        List<UserDataSet> dataSetList = new ArrayList<>();
        dataSetList.addAll(runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        }));
        for (UserDataSet dataSet : dataSetList){
            putCache(dataSet);
        }
        return dataSetList;
    }

    public void shutdown() {
        userCache.dispose();
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            R result = function.apply(session);
            transaction.commit();
            return result;
        }
    }

    private void putCache(UserDataSet dataSet) {
        userCache.put(new SoftReference<>(new CacheElement<>(dataSet.getId(), dataSet)));
        indexByName.put(dataSet.getName(), dataSet.getId());
    }
}

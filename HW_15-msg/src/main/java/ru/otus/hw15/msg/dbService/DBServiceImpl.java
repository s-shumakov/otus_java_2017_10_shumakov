package ru.otus.hw15.msg.dbService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw15.msg.app.DBService;
import ru.otus.hw15.msg.app.MessageSystemContext;
import ru.otus.hw15.msg.dataSets.AddressDataSet;
import ru.otus.hw15.msg.dataSets.EmptyDataSet;
import ru.otus.hw15.msg.dataSets.PhoneDataSet;
import ru.otus.hw15.msg.dataSets.UserDataSet;
import ru.otus.hw15.msg.cache.CacheElement;
import ru.otus.hw15.msg.cache.CacheEngine;
import ru.otus.hw15.msg.dbService.dao.UserDataSetDAO;
import ru.otus.hw15.msg.messageSystem.Address;
import ru.otus.hw15.msg.messageSystem.MessageSystem;

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
    private final Address address;
    private final MessageSystemContext context;
    private static final Logger log = LogManager.getLogger();

    public DBServiceImpl(MessageSystemContext context, Address address, CacheEngine userCache) {
        log.info("DBServiceImpl(MessageSystemContext context, Address address, CacheEngine userCache)");
        sessionFactory = createSessionFactory(setConfiguration());
        this.context = context;
        this.address = address;
        this.userCache = userCache;
    }

    public void init() {
        log.info("DBServiceImpl init()");
        context.getMessageSystem().addAddressee(this);
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

    @Override
    public void shutdown() {
        userCache.dispose();
        sessionFactory.close();
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public CacheEngine getCache() {
        return userCache;
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

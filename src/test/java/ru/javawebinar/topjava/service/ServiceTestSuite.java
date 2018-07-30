package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.service.datajpa.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.datajpa.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaUserServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaMealServiceTest;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        JdbcUserServiceTest.class, JdbcMealServiceTest.class,
        JpaUserServiceTest.class, JpaMealServiceTest.class,
        DataJpaUserServiceTest.class, DataJpaMealServiceTest.class
})
public class ServiceTestSuite extends TestSuite {
}
package ru.javawebinar.topjava.service.jdbc;

import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.Profiles;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(Profiles.JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

}
package com.howtodoinjava.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class AppTest {

  @Autowired
  DatasourceProps datasourceProps;

  @Autowired
  AppProperties props;

  @Test
  void contextLoads() {
    assertTrue(true);
  }

  @Test
  void testPropertiesList() {
    List<UserRole> list = List.of(
        new UserRole(1, "Subscriber"),
        new UserRole(2, "Editor"),
        new UserRole(3, "Admin"));

    String url = this.datasourceProps.getUrl();

    assertEquals("jdbc:h2:file:C:/temp/test", url);
  }

  @Test
  void testProperties() {
    String applicationName = this.props.getAppName();
    String datasourceUrl = this.props.getDatasourceUrl();

    assertEquals("Test App", applicationName);
    assertEquals("jdbc:h2:file:C:/temp/test", datasourceUrl);

    assertThat(this.props.getEnvironmentsList())
        .hasSameElementsAs(Arrays.asList("local", "dev", "test", "prod"));
    assertArrayEquals(new String[]{"local", "dev", "test", "prod"},
            this.props.getEnvironments());
  }
}

package com.sky.docs;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  private final Logger log = LoggerFactory.getLogger(SwaggerConfiguration.class);

  public static final String DEFAULT_INCLUDE_PATTERN = "/api/.*";

  /**
   * Swagger Springfox configuration.
   *
   * @return the Swagger Springfox configuration
   */
  @Bean
  public Docket swaggerSpringfoxDocket() {
    log.debug("Starting Swagger");
    StopWatch watch = new StopWatch();
    watch.start();
/*    Contact contact = new Contact(
      shopPlatformProperties.getSwagger().getContactName(),
      shopPlatformProperties.getSwagger().getContactUrl(),
      shopPlatformProperties.getSwagger().getContactEmail());

    ApiInfo apiInfo = new ApiInfo(
      shopPlatformProperties.getSwagger().getTitle(),
      shopPlatformProperties.getSwagger().getDescription(),
      shopPlatformProperties.getSwagger().getVersion(),
      shopPlatformProperties.getSwagger().getTermsOfServiceUrl(),
      contact,
      shopPlatformProperties.getSwagger().getLicense(),
      shopPlatformProperties.getSwagger().getLicenseUrl());*/

    Docket docket = new Docket(DocumentationType.SWAGGER_2)
      .produces(Sets.newHashSet("application/json; charset=UTF-8"))
      .consumes(Sets.newHashSet("application/json; charset=UTF-8"))
      .protocols(Sets.newHashSet("http", "https"))
      .apiInfo(apiInfo())
      .forCodeGeneration(true)
      .genericModelSubstitutes(ResponseEntity.class)
      .ignoredParameterTypes(Pageable.class)
      .ignoredParameterTypes(java.sql.Date.class)
      .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
      .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
      .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
      //.groupName("business-api")
      .select()
      .apis(RequestHandlerSelectors.any())
      .paths(regex(DEFAULT_INCLUDE_PATTERN))
      .build();
    watch.stop();
    log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
    return docket;
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
      .title("微电商 API Docs")
      .description("微电商 API Docs (提供对外接口)")
      .contact(new Contact("李兴平", "http:/test-url.com", "814367966@qq.com"))
      .license("Apache 2.0")
      .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
      .version("1.0.0")
      .build();
  }
}

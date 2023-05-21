package pp.ua.lifebook.featuretests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ContextConfiguration;
import pp.ua.lifebook.featuretests.config.FeatureTestInitializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = FeatureTestInitializer.class)
public @interface FeatureTest {

    @AliasFor(annotation = ContextConfiguration.class, attribute = "classes")
    Class<?>[] contextClasses() default {};
}

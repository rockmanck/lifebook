package pp.ua.lifebook.featuretests.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public class FeatureTestInitializer implements ApplicationContextInitializer<GenericApplicationContext> {
    @Override
    public void initialize(GenericApplicationContext applicationContext) {
        new AnnotatedBeanDefinitionReader(applicationContext)
            .register(InitializerConfig.class);
    }
}

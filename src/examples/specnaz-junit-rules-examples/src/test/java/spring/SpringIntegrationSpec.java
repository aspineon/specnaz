package spring;

import a.ServiceA;
import org.junit.ClassRule;
import org.specnaz.junit.SpecnazJUnit2_Rules;
import org.specnaz.junit.rules.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestSpringConfig.class)
public class SpringIntegrationSpec extends SpecnazJUnit2_Rules {
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    public Rule<SpringMethodRule> springMethodRule = Rule.of(() -> new SpringMethodRule());

    @Autowired
    ServiceA serviceA;

    int count;

    {
        describes("Spring integration", it -> {
            it.should("inject ServiceA correctly", () -> {
                assertThat(serviceA).isNotNull();
            });

            it.should("inject the dependencies correctly", () -> {
                assertThat(serviceA.findA()).isEqualTo("ServiceA:TestDaoA");
            });

            it.beginsAll(() -> {
                count++;
            });

            it.should("run the beginsAll methods", () -> {
                assertThat(count).isOne();
            });
        });
    }

    // needed only to fool the Spring validation
    @org.junit.Rule
    public SpringMethodRule dummy = null;
}
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SanityTest {

    @Test
    void sanityCheck() {
        assertThat("something").isEqualTo("something");
    }
}

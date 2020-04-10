package com.thomasory.guessmycategory.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.thomasory.guessmycategory.web.rest.TestUtil;

public class OptionalImageTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionalImage.class);
        OptionalImage optionalImage1 = new OptionalImage();
        optionalImage1.setId(1L);
        OptionalImage optionalImage2 = new OptionalImage();
        optionalImage2.setId(optionalImage1.getId());
        assertThat(optionalImage1).isEqualTo(optionalImage2);
        optionalImage2.setId(2L);
        assertThat(optionalImage1).isNotEqualTo(optionalImage2);
        optionalImage1.setId(null);
        assertThat(optionalImage1).isNotEqualTo(optionalImage2);
    }
}

package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class CorretoraTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Corretora.class);
        Corretora corretora1 = new Corretora();
        corretora1.setId(1L);
        Corretora corretora2 = new Corretora();
        corretora2.setId(corretora1.getId());
        assertThat(corretora1).isEqualTo(corretora2);
        corretora2.setId(2L);
        assertThat(corretora1).isNotEqualTo(corretora2);
        corretora1.setId(null);
        assertThat(corretora1).isNotEqualTo(corretora2);
    }
}

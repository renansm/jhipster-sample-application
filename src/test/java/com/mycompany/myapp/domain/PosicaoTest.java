package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class PosicaoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Posicao.class);
        Posicao posicao1 = new Posicao();
        posicao1.setId(1L);
        Posicao posicao2 = new Posicao();
        posicao2.setId(posicao1.getId());
        assertThat(posicao1).isEqualTo(posicao2);
        posicao2.setId(2L);
        assertThat(posicao1).isNotEqualTo(posicao2);
        posicao1.setId(null);
        assertThat(posicao1).isNotEqualTo(posicao2);
    }
}

package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class NotaCorretagemTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NotaCorretagem.class);
        NotaCorretagem notaCorretagem1 = new NotaCorretagem();
        notaCorretagem1.setId(1L);
        NotaCorretagem notaCorretagem2 = new NotaCorretagem();
        notaCorretagem2.setId(notaCorretagem1.getId());
        assertThat(notaCorretagem1).isEqualTo(notaCorretagem2);
        notaCorretagem2.setId(2L);
        assertThat(notaCorretagem1).isNotEqualTo(notaCorretagem2);
        notaCorretagem1.setId(null);
        assertThat(notaCorretagem1).isNotEqualTo(notaCorretagem2);
    }
}

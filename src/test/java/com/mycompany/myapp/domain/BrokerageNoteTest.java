package com.mycompany.myapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.mycompany.myapp.web.rest.TestUtil;

public class BrokerageNoteTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BrokerageNote.class);
        BrokerageNote brokerageNote1 = new BrokerageNote();
        brokerageNote1.setId(1L);
        BrokerageNote brokerageNote2 = new BrokerageNote();
        brokerageNote2.setId(brokerageNote1.getId());
        assertThat(brokerageNote1).isEqualTo(brokerageNote2);
        brokerageNote2.setId(2L);
        assertThat(brokerageNote1).isNotEqualTo(brokerageNote2);
        brokerageNote1.setId(null);
        assertThat(brokerageNote1).isNotEqualTo(brokerageNote2);
    }
}

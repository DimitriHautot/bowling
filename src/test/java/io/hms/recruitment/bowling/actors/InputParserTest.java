package io.hms.recruitment.bowling.actors;

import io.hms.recruitment.bowling.domain.Frame;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by dimitri on 26/04/2019.
 */
public class InputParserTest {

    private final InputParser unitUnderTest = new InputParser();

    @Test
    public void parse_2_3() throws Exception {
        List<Frame> frames = unitUnderTest.parse(new String[]{"2", "3"});
        assertThat(frames).hasSize(1);

        assertThat(frames.get(0).getKnockedDown()).isEqualTo(5);
        assertThat(frames.get(0).isLast()).isFalse();
        assertThat(frames.get(0).isSpare()).isFalse();
        assertThat(frames.get(0).isStrike()).isFalse();
        assertThat(frames.get(0).isOpen()).isTrue();
    }

    @Test
    public void parse_1_1_9_1() throws Exception {
        List<Frame> frames = unitUnderTest.parse(new String[]{"1", "1", "9", "1"});
        assertThat(frames).hasSize(2);

        assertThat(frames.get(0).getKnockedDown()).isEqualTo(2);
        assertThat(frames.get(0).isLast()).isFalse();
        assertThat(frames.get(0).isSpare()).isFalse();
        assertThat(frames.get(0).isStrike()).isFalse();
        assertThat(frames.get(0).isOpen()).isTrue();

        assertThat(frames.get(1).getKnockedDown()).isEqualTo(10);
        assertThat(frames.get(1).isLast()).isFalse();
        assertThat(frames.get(1).isSpare()).isTrue();
        assertThat(frames.get(1).isStrike()).isFalse();
        assertThat(frames.get(1).isOpen()).isFalse();
    }

    @Test
    public void parse_1_1_9_1_10() throws Exception {
        List<Frame> frames = unitUnderTest.parse(new String[]{"1", "1", "9", "1", "10"});
        assertThat(frames).hasSize(3);

        assertThat(frames.get(0).getKnockedDown()).isEqualTo(2);
        assertThat(frames.get(0).isLast()).isFalse();
        assertThat(frames.get(0).isSpare()).isFalse();
        assertThat(frames.get(0).isStrike()).isFalse();
        assertThat(frames.get(0).isOpen()).isTrue();

        assertThat(frames.get(1).getKnockedDown()).isEqualTo(10);
        assertThat(frames.get(1).isLast()).isFalse();
        assertThat(frames.get(1).isSpare()).isTrue();
        assertThat(frames.get(1).isStrike()).isFalse();
        assertThat(frames.get(1).isOpen()).isFalse();

        assertThat(frames.get(2).getKnockedDown()).isEqualTo(10);
        assertThat(frames.get(2).isLast()).isFalse();
        assertThat(frames.get(2).isSpare()).isFalse();
        assertThat(frames.get(2).isStrike()).isTrue();
        assertThat(frames.get(2).isOpen()).isFalse();
    }

    @Test
    public void parse_3_0_0_6_4_2_8_1_5_2_2_8_3_5_10_7_1_8_0() {
        List<Frame> frames = unitUnderTest.parse(new String[]{"3", "0", "0", "6", "4", "2", "8", "1", "5", "2", "2", "8", "3", "5", "10", "7", "1", "8", "0"});
        assertThat(frames).hasSize(10);
        assertThat(frames.get(9).isLast()).isTrue();
    }

    @Test
    public void parse_10_10_10_8_2_10_10_7_3_10_10_10_10_9() {
        List<Frame> frames = unitUnderTest.parse(new String[]{"10", "10", "10", "8", "2", "10", "10", "7", "3", "10", "10", "10", "10", "9"});
        assertThat(frames).hasSize(10);
        assertThat(frames.get(9).isLast()).isTrue();
    }

    @Test(expected = RuntimeException.class)
    public void parse__invalid_numeric_value() {
        unitUnderTest.parse(new String[]{"1", "1", "42", "1", "10"});
    }
    
    @Test(expected = RuntimeException.class)
    public void parse__invalid_value_type() {
        unitUnderTest.parse(new String[]{"1", "1", "AB", "1", "10"});
    }
}

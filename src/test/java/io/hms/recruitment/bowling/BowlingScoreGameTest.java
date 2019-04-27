package io.hms.recruitment.bowling;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link BowlingScoreGame}.
 *
 * Created by dimitri on 27/04/2019.
 */
public class BowlingScoreGameTest {

    @Test
    public void play_partial_game_1() throws Exception {
        int score = new BowlingScoreGame().play(new String[]{"3", "2"});

        assertThat(score).isEqualTo(5);
    }

    @Test
    public void play_partial_game_2() throws Exception {
        int score = new BowlingScoreGame().play(new String[]{"1", "1", "9", "1"});

        assertThat(score).isEqualTo(12);
    }

    @Test
    public void play_partial_game_3() throws Exception {
        int score = new BowlingScoreGame().play(new String[]{"1", "1", "9", "1", "10"});

        assertThat(score).isEqualTo(32);
    }

    @Test
    public void play_beginner_game() throws Exception {
        int score = new BowlingScoreGame().play(new String[]{"3", "0", "0", "6", "4", "2", "8", "1", "5", "2", "2", "8", "3", "5", "10", "7", "1", "8", "0"});

        assertThat(score).isEqualTo(86);
    }

    @Test
    public void play_professional_game() throws Exception {
        int score = new BowlingScoreGame().play(new String[]{"10", "10", "10", "8", "2", "10", "10", "7", "3", "10", "10", "10", "10", "9"});

        assertThat(score).isEqualTo(254);
    }

}
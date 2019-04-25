package io.hms.recruitment.bowling.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Representation of a bowling frame. Support 3 throws in last (=10th) frame of a game.
 *
 * Created by dimitri on 24/04/2019.
 */
@Getter
public class Frame {

    private final int knockedDown;
    private final int firstThrowScore;
    private final Integer secondThrowScore;
    private final Integer thirdThrowScore;
    @Setter
    private int cumulativeScore;
    @Setter
    private boolean last = false;

    public Frame(int firstThrowScore) { // Strike
        assert firstThrowScore == 10;

        this.knockedDown = firstThrowScore;
        this.firstThrowScore = firstThrowScore;
        this.secondThrowScore = null;
        this.thirdThrowScore = null;
    }

    public Frame(int firstThrowScore, int secondThrowScore) { // Spare or open
        assert firstThrowScore >= 0;
        assert secondThrowScore >= 0;

        this.knockedDown = firstThrowScore + secondThrowScore;
        this.firstThrowScore = firstThrowScore;
        this.secondThrowScore = secondThrowScore;
        this.thirdThrowScore = null;
    }

    public Frame(int firstThrowScore, int secondThrowScore, int thirdThrowScore) { // Last frame with strike or spare
        assert firstThrowScore >= 0;
        assert secondThrowScore >= 0;
        assert thirdThrowScore >= 0;

        this.knockedDown = firstThrowScore + secondThrowScore + thirdThrowScore;
        this.firstThrowScore = firstThrowScore;
        this.secondThrowScore = secondThrowScore;
        this.thirdThrowScore = thirdThrowScore;
        this.last = true;
    }

    public boolean isOpen() {
        if (! last) {
            return thirdThrowScore == null && knockedDown < 10;
        }
        if (thirdThrowScore == null) {
            return knockedDown != 10;
        }
        return knockedDown != 20;
    }

    public boolean isSpare() {
        return knockedDown == 10 && secondThrowScore != null && thirdThrowScore == null;
    }

    public boolean isStrike() {
        return knockedDown == 10 && secondThrowScore == null && thirdThrowScore == null;
    }

    @Override
    public String toString() {
        String result = "Frame (knockedDown: " + knockedDown + ", 1st throw score: " + firstThrowScore + ", 2nd throw score: " + secondThrowScore;
        if (thirdThrowScore != null) {
            result += ", 3rd throw score: " + thirdThrowScore;
        }
        result += ", cumulativeScore: " + cumulativeScore + ", last? " + last + ")";
        return result;
    }
}

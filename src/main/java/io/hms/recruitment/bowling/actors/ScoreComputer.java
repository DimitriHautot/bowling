package io.hms.recruitment.bowling.actors;

import io.hms.recruitment.bowling.domain.Frame;
import lombok.Getter;

import java.util.List;

/**
 * This class allows the computation of the score of a bowling game, represented as a list of frames.
 *
 * Created by dimitri on 24/04/2019.
 */
public class ScoreComputer {

    private final List<Frame> frames;

    @Getter
    private int totalScore;
    private boolean computed = false;

    public ScoreComputer(List<Frame> frames) {
        this.frames = frames;
    }

    /**
     * Entry point for the score computer.
     *
     * @return The total score of the game
     */
    public int computeTotalScore() {
        if (!computed) {
            for (int index = 0; index < frames.size(); index++) {
                computeCumulativeScore(index);
            }

            this.totalScore = frames.get(frames.size() - 1).getCumulativeScore();
            this.computed = true;
        }

        return this.totalScore;
    }

    /**
     * Compute the cumulative score of a given frame. Cumulative means that the frame's score is added to the sum of the score of the previous frames.
     * Just like displayed on the scoreboard of a real bowling game.
     *
     * @param index The position, 0-based, of the frame to compute the score
     * @return The sum of score from the start up to the selected frame
     */
    private int computeCumulativeScore(int index) {
        int currentFrameScore;
        Frame frame = frames.get(index);
        currentFrameScore = computeScore(index);

        int previousFrameCumulativeScore = 0;
        if (index > 0) {
            previousFrameCumulativeScore = frames.get(index - 1).getCumulativeScore();
        }

        frame.setCumulativeScore(previousFrameCumulativeScore + currentFrameScore);
        return frame.getCumulativeScore();
    }

    /**
     * Compute the score of a frame.
     *
     * @param index The position, 0-based, of the frame whose score is computed
     * @return The computed score
     */
    int computeScore(int index) {
        Frame frame = frames.get(index);
        int score;
        if (frame.isStrike()) {
            score = getScoreForStrike(index);
        } else if (frame.isSpare()) {
            score = getScoreForSpare(index);
        } else {
            score = frame.getKnockedDown();
        }
        return score;
    }

    /**
     * Compute the score of a strike frame. Does not check that the frame is actually a strike frame.
     *
     * @param index The position, 0-based, of the frame whose score is computed
     * @return The computed score
     */
    int getScoreForStrike(int index) {
        if (index == 9 || (index + 1) == frames.size()) {
            return frames.get(index).getKnockedDown();
        }
        Frame next1Frame = frames.get(index + 1);
        if (next1Frame.isSpare() || next1Frame.isOpen()) {
            return 10 + next1Frame.getFirstThrowScore() + next1Frame.getSecondThrowScore();
        }
        if (next1Frame.isStrike()) {
            // Special case: last frame
            if (next1Frame.isLast()) {
                return 10 + next1Frame.getFirstThrowScore() + next1Frame.getSecondThrowScore();
            }
            Frame next2Frame = frames.get(index + 2);
            return 10 + next1Frame.getFirstThrowScore() + next2Frame.getFirstThrowScore();
        }

        return frames.get(index).getKnockedDown();
    }

    /**
     * Compute the score of a spare frame. Does not check that the frame is actually a spare frame.
     *
     * @param index The position, 0-based, of the frame whose score is computed
     * @return The computed score
     */
    int getScoreForSpare(int index) {
        if (index == 9 || (index + 1) == frames.size()) {
            return frames.get(index).getKnockedDown();
        }
        Frame next1Frame = frames.get(index + 1);
        return 10 + next1Frame.getFirstThrowScore();
    }
}

package io.hms.recruitment.bowling;

import io.hms.recruitment.bowling.actors.InputParser;
import io.hms.recruitment.bowling.actors.ScoreComputer;
import io.hms.recruitment.bowling.domain.Frame;

import java.util.List;

/**
 * Entry point of the whole bowling score computing challenge.
 */
public class BowlingScoreGame {

    private static final boolean debug = false; // If true, print out the detail of each frame, for easier debugging

    private static BowlingScoreGame instance = new BowlingScoreGame();

    public static void main(String[] allPinScores) {
        int totalScore = instance.play(allPinScores);
        System.exit(totalScore);
    }

    int play(String[] allPinScores) {
        int totalScore = 0;

        if (allPinScores.length > 0) {
            List<Frame> frames = new InputParser().parse(allPinScores);
            totalScore = new ScoreComputer(frames).computeTotalScore();
            toString(frames);
        }
        System.out.println("=> " + totalScore);

        return totalScore;
    }

    private static void toString(List<Frame> frames) {
        if (debug) {
            for (int i = 0; i < frames.size(); i++) {
                Frame frame = frames.get(i);
                System.out.println("" + (i + 1) + ": " + frame);
            }
        }
    }
}

package io.hms.recruitment.bowling.actors;

import io.hms.recruitment.bowling.domain.Frame;

import java.util.ArrayList;
import java.util.List;

/**
 * This class allows to transform raw input values into valid bowling frames.
 *
 * Created by dimitri on 26/04/2019.
 */
public class InputParser {

    /**
     * Parse a sequence of knocked down pins. Validate data type and values.
     *
     * @param allPinScores The raw sequence of input data
     * @return A list of frames, modeling the evolution of a bowling game, complete or not.
     */
    public List<Frame> parse(String[] allPinScores) {
        // (1) Make sure we have valid values
        List<Integer> parsedScores = new ArrayList<>(allPinScores.length);
        for (String stringPinScore : allPinScores) {
            try {
                int pinScore = Integer.parseInt(stringPinScore);
                if (pinScore >= 0 && pinScore <= 10) {
                    parsedScores.add(pinScore);
                } else {
                    throw new RuntimeException("Invalid numeric value found ([0, 10]): " + pinScore);
                }
            } catch (NumberFormatException nfe) {
                throw new RuntimeException("Invalid value type in provided data");
            }
        }

        // (2) Determine frames from points sequence
        List<Frame> frames = new ArrayList<>();
        for (int index = 0; index < parsedScores.size(); index++) {
            if (parsedScores.get(index) == 10) { // Strike
                if (frames.size() < 9) { // Not last frame
                    frames.add(new Frame(10));
                    continue;
                }
            }

            int score1 = parsedScores.get(index);
            if (index + 1 < parsedScores.size()) { // Not the last throw
                index++;
                int score2 = parsedScores.get(index);
                if (frames.size() == 9) { // Last throw
                    if (index + 1 < parsedScores.size()) {
                        int score3 = parsedScores.get(index + 1);
                        frames.add(new Frame(score1, score2, score3));
                    } else {
                        frames.add(new Frame(score1, score2));
                    }
                } else {
                    frames.add(new Frame(score1, score2));
                }
            }
        }

        if (frames.size() == 10) {
            frames.get(9).setLast(true);
        }

        return frames;
    }
}

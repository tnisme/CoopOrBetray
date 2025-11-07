package game;

import java.util.List;

public interface NPC {
    Move decideMove(List<Move> myHistory, List<Move> opponentHistory);
    String getName();
    String getStory();
    String getPersonality();
}

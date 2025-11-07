package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class ForgivingSoul implements NPC {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        int size = opponentHistory.size();
        if (size < 2) return Move.COOPERATE;
        Move lastMove = opponentHistory.get(opponentHistory.size() - 1);
        Move secondLastMove = opponentHistory.get(opponentHistory.size() - 2);
        if (lastMove == Move.DEFECT && secondLastMove == Move.DEFECT) return Move.DEFECT;
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "Forgiving Soul";
    }

    @Override
    public String getStory() {
        return "Là một linh mục, tôi tin vào sức mạnh của sự tha thứ. " +
                "Ai cũng có thể mắc sai lầm, và mọi người xứng đáng có cơ hội thứ hai. " +
                "Tôi chỉ trừng phạt khi sự phản bội trở thành thói quen, bởi khoan dung " +
                "không có nghĩa là nhu nhược. Tha thứ một lần là nhân đức, tha thứ mãi mãi là ngu ngốc";
    }

    @Override
    public String getPersonality() {
        return "Khoan dung, kiên nhẫn, nhưng có giới hạn";
    }
}

package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class PragmaticOpportunist implements NPC {
    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (myHistory.isEmpty()) return Move.COOPERATE;
        Move myLastMove = myHistory.get(myHistory.size() - 1);
        Move opponentLastMove = opponentHistory.get(opponentHistory.size() - 1);
        if ((myLastMove == Move.COOPERATE && opponentLastMove == Move.COOPERATE) ||
                (myLastMove == Move.DEFECT && opponentLastMove == Move.DEFECT)) {
            return myLastMove;
        } else {
            return (myLastMove == Move.COOPERATE) ? Move.DEFECT : Move.COOPERATE;
        }
    }

    @Override
    public String getName() {
        return "Pragmatic Opportunist";
    }

    @Override
    public String getStory() {
        return "Từng là một doanh nhân, tôi học được rằng thành công không đến từ nguyên tắc cứng nhắc " +
                "mà từ khả năng thích nghi. Tôi không theo đuổi lý tưởng hay trả thù - tôi theo đuổi " +
                "kết quả. Nếu chiến lược hiện tại không hiệu quả, tôi sẽ thay đổi nó. Không có chiến lược tồi, chỉ có chiến lược không phù hợp với tình huống";
    }

    @Override
    public String getPersonality() {
        return "Thực dụng, linh hoạt, và kết quả là trên hết";
    }
}

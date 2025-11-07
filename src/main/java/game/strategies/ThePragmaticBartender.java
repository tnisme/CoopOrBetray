package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class ThePragmaticBartender implements NPC {

    private int totalRounds = 0;
    private int opponentCooperateCount = 0;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        totalRounds++;

        if (opponentHistory.isEmpty()) {
            return Move.COOPERATE;
        }

        // Cập nhật thống kê
        for (Move move : opponentHistory) {
            if (move == Move.COOPERATE) {
                opponentCooperateCount++;
            }
        }

        double cooperationRate = (double) opponentCooperateCount / opponentHistory.size();

        // Kinh nghiệm: "Tôi đã thấy đủ loại người trong quán rượu của mình"
        if (cooperationRate > 0.8) {
            return Move.COOPERATE; // Người tốt - hợp tác
        } else if (cooperationRate < 0.2) {
            return Move.DEFECT; // Kẻ xấu - tránh xa
        } else {
            // Người bình thường - TitForTat
            return opponentHistory.get(opponentHistory.size() - 1);
        }
    }

    @Override
    public String getName() {
        return "The Pragmatic Bartender";
    }

    public String getStory() {
        return "30 năm điều hành quán rượu đã dạy tôi cách đánh giá con người. " +
                "Tôi có thể nhận ra kẻ lừa đảo chỉ sau vài câu chuyện. " +
                "Tôi công bằng với tất cả, nhưng không ngây thơ với bất kỳ ai.";
    }

    public String getPersonality() {
        return "Thực tế, kinh nghiệm, và công bằng";
    }

    public String getWisdom() {
        return "Một lần bị lừa là lỗi của người khác, hai lần bị lừa là lỗi của bạn";
    }
}

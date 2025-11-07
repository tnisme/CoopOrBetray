package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;
import java.util.Random;

public class SneakyDefector implements NPC {

    private Random random = new Random();
    private int cooperateStreak = 0;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) return Move.COOPERATE;

        cooperateStreak++;
        if (cooperateStreak > 10 + random.nextInt(5)) {
            cooperateStreak = 0;
            return Move.DEFECT;
        }
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "Sneaky Defector";
    }

    @Override
    public String getStory() {
        return "Tôi là một người chơi thông minh, luôn tìm kiếm lợi ích cá nhân. " +
                "Tôi không theo đuổi lý tưởng hay trả thù - tôi chỉ quan tâm đến kết quả. " +
                "Nếu chiến lược hiện tại không hiệu quả, tôi sẽ thay đổi nó. Không có chiến lược tồi, chỉ có chiến lược không phù hợp với tình huống.";
    }

    @Override
    public String getPersonality() {
        return "Thực dụng, linh hoạt, và kết quả là trên hết";
    }
}

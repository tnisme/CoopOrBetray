package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;
import java.util.Random;

public class TheHonestMerchant implements NPC {

    private Random random = new Random();
    private int brokenPromises = 0;
    private boolean wasBetrayed = false;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            return Move.COOPERATE; // Luôn bắt đầu với niềm tin
        }

        Move lastOpponent = opponentHistory.get(opponentHistory.size() - 1);

        // Triết lý: "Một lần bất tín, vạn lần bất tin"
        if (lastOpponent == Move.DEFECT) {
            wasBetrayed = true;
            brokenPromises++;

            // Nếu bị phản bội quá 3 lần, chuyển sang phòng thủ
            if (brokenPromises > 3) {
                return Move.DEFECT;
            }

            // Cho một cơ hội sửa sai
            return (random.nextDouble() < 0.7) ? Move.DEFECT : Move.COOPERATE;
        }

        // Nếu đối tác giữ lời hứa, tiếp tục hợp tác
        return Move.COOPERATE;
    }

    @Override
    public String getName() {
        return "The Honest Merchant";
    }

    public String getStory() {
        return "Từng là một thương gia giàu có, tôi đã mất hết tài sản vì bị đối tác lừa gạt. " +
                "Giờ đây, tôi đề cao chữ tín nhưng cũng học cách tự bảo vệ mình. " +
                "Tôi luôn bắt đầu với lòng tin, nhưng một khi bị phản bội, tôi sẽ không dễ dàng tin tưởng lại.";
    }

    public String getPersonality() {
        return "Trung thực, cảnh giác, nhưng vẫn giữ lòng nhân hậu";
    }
}

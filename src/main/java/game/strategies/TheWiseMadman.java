package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;
import java.util.Random;

public class TheWiseMadman implements NPC {

    private Random random = new Random();
    private int strangeBehaviorCounter = 0;
    private boolean inCooperativeMode = true;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            strangeBehaviorCounter = 0;
            inCooperativeMode = random.nextBoolean();
            return inCooperativeMode ? Move.COOPERATE : Move.DEFECT;
        }

        // Đôi khi hành động ngẫu nhiên để gây bối rối
        if (random.nextDouble() < 0.15) { // 15% hành động ngẫu nhiên
            strangeBehaviorCounter++;
            return random.nextBoolean() ? Move.COOPERATE : Move.DEFECT;
        }

        Move lastOpponent = opponentHistory.get(opponentHistory.size() - 1);

        // Chiến thuật không thể đoán trước
        if (strangeBehaviorCounter > 2) {
            // Sau vài hành động kỳ lạ, quay lại chiến thuật thông minh
            strangeBehaviorCounter = 0;
            return (lastOpponent == Move.COOPERATE) ? Move.COOPERATE : Move.DEFECT;
        }

        // Phản ứng dựa trên mode hiện tại
        if (inCooperativeMode) {
            if (lastOpponent == Move.DEFECT) {
                inCooperativeMode = false; // Chuyển sang mode phòng thủ
            }
            return Move.COOPERATE;
        } else {
            if (lastOpponent == Move.COOPERATE && random.nextDouble() < 0.3) {
                inCooperativeMode = true; // Thỉnh thoảng cho cơ hội
            }
            return Move.DEFECT;
        }
    }

    @Override
    public String getName() {
        return "The Wise Madman";
    }

    public String getStory() {
        return "Người ta gọi tôi là điên, nhưng tôi thấy thế giới này mới thật sự điên rồ. " +
                "Đôi khi tôi hành động ngẫu nhiên để không ai có thể đoán được ý đồ. " +
                "Nhưng đừng nhầm lẫn - sự điên rồ của tôi ẩn chứa một phương pháp riêng.";
    }

    public String getPersonality() {
        return "Khó đoán, thông minh, và hơi điên rồ";
    }

    public String getMadnessQuote() {
        return "Để đánh bại một kẻ không thể đoán trước, bạn phải trở thành kẻ không thể đoán trước hơn";
    }
}

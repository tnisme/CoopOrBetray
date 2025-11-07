package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;

public class TheCunningTrickster implements NPC {

    private int cooperationStreak = 0;
    private boolean settingTrap = false;

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            cooperationStreak = 0;
            return Move.COOPERATE; // Bắt đầu ngọt ngào
        }

        Move lastOpponent = opponentHistory.get(opponentHistory.size() - 1);

        // Chiến thuật: Hợp tác liên tục để tạo niềm tin, rồi phản bội đột ngột
        if (lastOpponent == Move.COOPERATE) {
            cooperationStreak++;

            if (cooperationStreak >= 5 && !settingTrap) {
                settingTrap = true;
                return Move.DEFECT; // Đòn phản bội sau khi đã gây dựng niềm tin
            }

            if (settingTrap) {
                // Sau khi phản bội, quay lại hợp tác để chuẩn bị đòn tiếp theo
                settingTrap = false;
                cooperationStreak = 0;
                return Move.COOPERATE;
            }

            return Move.COOPERATE;
        } else {
            cooperationStreak = 0;
            return Move.DEFECT; // Trả đũa ngay lập tức
        }
    }

    @Override
    public String getName() {
        return "The Cunning Trickster";
    }

    @Override
    public String getStory() {
        return "Lớn lên trong khu ổ chuột, tôi học được rằng trong thế giới này, " +
                "chỉ có kẻ khôn ngoan mới sống sót. Tôi dùng vẻ ngoài ngây thơ để " +
                "lừa gạt những kẻ cả tin, nhưng luôn tránh xa những tay chơi nguy hiểm.";
    }

    public String getPersonality() {
        return "Khôn ngoan, mánh khóe, nhưng biết điểm dừng";
    }
}

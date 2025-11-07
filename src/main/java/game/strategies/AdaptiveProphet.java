package game.strategies;

import game.Move;
import game.NPC;

import java.util.List;
import java.util.Random;

public class AdaptiveProphet implements NPC {
    private Random random = new Random();
    private double opponentCooperationRate = 0.5;
    private int opponentDefectCount = 0;
    private int myDefectCount = 0;
    private boolean isFirstMove = true;
    private int roundCount = 0;
    private double forgivenessThreshold = 0.1; // Ngưỡng tha thứ

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        if (opponentHistory.isEmpty()) {
            isFirstMove = false;
            roundCount++;
            return Move.COOPERATE; // Luôn bắt đầu bằng hợp tác
        }

        roundCount++;
        Move lastOpponentMove = opponentHistory.get(opponentHistory.size() - 1);

        // Cập nhật thống kê đối thủ
        updateOpponentStats(opponentHistory);

        // Chiến lược thích ứng
        return adaptiveDecision(myHistory, opponentHistory, lastOpponentMove);
    }

    private void updateOpponentStats(List<Move> opponentHistory) {
        long cooperations = opponentHistory.stream().filter(m -> m == Move.COOPERATE).count();
        opponentCooperationRate = (double) cooperations / opponentHistory.size();

        opponentDefectCount = (int) opponentHistory.stream().filter(m -> m == Move.DEFECT).count();
    }

    private Move adaptiveDecision(List<Move> myHistory, List<Move> opponentHistory, Move lastOpponentMove) {
        // Giai đoạn đầu: Xây dựng danh tiếng
        if (roundCount < 10) {
            return buildReputationPhase(lastOpponentMove);
        }

        // Giai đoạn giữa: Thích ứng linh hoạt
        if (roundCount < 150) {
            return adaptiveMiddleGame(myHistory, opponentHistory);
        }

        // Giai đoạn cuối: Tối ưu hóa điểm số
        return endgameStrategy(myHistory, opponentHistory);
    }

    private Move buildReputationPhase(Move lastOpponentMove) {
        // Bắt đầu hợp tác, nhưng trả đũa ngay lập tức
        if (isFirstMove) {
            isFirstMove = false;
            return Move.COOPERATE;
        }

        // TitForTat đơn giản trong giai đoạn đầu
        return lastOpponentMove;
    }

    private Move adaptiveMiddleGame(List<Move> myHistory, List<Move> opponentHistory) {
        double recentCooperationRate = calculateRecentCooperationRate(opponentHistory, 10);
        Move lastOpponentMove = opponentHistory.get(opponentHistory.size() - 1);

        // Nếu đối thủ rất hợp tác (>80%), tiếp tục hợp tác
        if (recentCooperationRate > 0.8) {
            return Move.COOPERATE;
        }

        // Nếu đối thủ rất không hợp tác (<20%), chuyển sang phòng thủ
        if (recentCooperationRate < 0.2) {
            return Move.DEFECT;
        }

        // Phát hiện chiến lược AlwaysDefect
        if (opponentCooperationRate < 0.05) {
            return Move.DEFECT;
        }

        // Phát hiện chiến lược AlwaysCooperate - khai thác
        if (opponentCooperationRate > 0.95 && roundCount > 20) {
            // Thỉnh thoảng phản bội để khai thác, nhưng không quá tham lam
            return (random.nextDouble() < 0.3) ? Move.DEFECT : Move.COOPERATE;
        }

        // Với các chiến lược TitForTat, duy trì hợp tác
        if (isTitForTatPattern(opponentHistory)) {
            return Move.COOPERATE;
        }

        // Chiến lược Generous TitForTat: tha thứ 10% số lần bị phản bội
        if (lastOpponentMove == Move.DEFECT) {
            if (random.nextDouble() < forgivenessThreshold) {
                return Move.COOPERATE; // Tha thứ
            }
        }

        // Mặc định: TitForTat với tính toán xác suất
        return (random.nextDouble() < 0.95) ? lastOpponentMove :
                (lastOpponentMove == Move.COOPERATE ? Move.DEFECT : Move.COOPERATE);
    }

    private Move endgameStrategy(List<Move> myHistory, List<Move> opponentHistory) {
        int roundsLeft = 240 - roundCount; // Giả sử tối đa 240 vòng

        // Nếu sắp kết thúc, tính toán lợi ích cuối cùng
        if (roundsLeft < 10) {
            // Trong 3 vòng cuối, phản bội nếu có lợi
            if (roundsLeft <= 3) {
                double expectedGain = calculateExpectedGain(opponentHistory);
                return (expectedGain > 0) ? Move.DEFECT : Move.COOPERATE;
            }

            // Trong 10 vòng cuối, cân nhắc danh tiếng để các game sau
            return (opponentCooperationRate > 0.7) ? Move.COOPERATE : Move.DEFECT;
        }

        return adaptiveMiddleGame(myHistory, opponentHistory);
    }

    private double calculateRecentCooperationRate(List<Move> history, int lookback) {
        int endIndex = history.size();
        int startIndex = Math.max(0, endIndex - lookback);
        int cooperateCount = 0;

        for (int i = startIndex; i < endIndex; i++) {
            if (history.get(i) == Move.COOPERATE) {
                cooperateCount++;
            }
        }

        return (double) cooperateCount / (endIndex - startIndex);
    }

    private boolean isTitForTatPattern(List<Move> opponentHistory) {
        if (opponentHistory.size() < 3) return false;

        // Kiểm tra xem đối thủ có phải là TitForTat không
        for (int i = 1; i < opponentHistory.size(); i++) {
            Move current = opponentHistory.get(i);
            Move previous = (i > 0) ? opponentHistory.get(i - 1) : Move.COOPERATE;

            // TitForTat thường copy nước đi trước đó của ta
            // Đây là kiểm tra đơn giản
            if (current != previous && i > 1) {
                // Có sự khác biệt, có thể không phải TitForTat thuần túy
                return false;
            }
        }

        return opponentCooperationRate > 0.5; // TitForTat thường hợp tác nhiều
    }

    private double calculateExpectedGain(List<Move> opponentHistory) {
        // Tính toán lợi ích kỳ vọng từ việc phản bội
        double cooperateValue = 3.0; // Cả hai hợp tác
        double defectValue = 5.0;    // Ta phản bội, đối thủ hợp tác
        double mutualDefect = 1.0;   // Cả hai phản bội

        double opponentCooperateProb = opponentCooperationRate;

        // Lợi ích kỳ vọng khi phản bội
        double expectedDefectGain = opponentCooperateProb * defectValue +
                (1 - opponentCooperateProb) * mutualDefect;

        // Lợi ích kỳ vọng khi hợp tác
        double expectedCooperateGain = opponentCooperateProb * cooperateValue;

        return expectedDefectGain - expectedCooperateGain;
    }

    @Override
    public String getName() {
        return "Adaptive Prophet";
    }

    @Override
    public String getStory() {
        return "Tôi là hậu duệ cuối cùng của dòng họ tiên tri. Từ khi sinh ra, tôi đã có thể " +
                "nhìn thấy những mẫu hình trong hành vi con người. Mỗi người mang theo một câu chuyện, " +
                "một mẫu hình mà tôi có thể đọc được như những trang sách mở.\n" +
                "Trong tu viện cổ nơi tôi lớn lên, các trưởng lão dạy tôi rằng: 'Sự thật không nằm ở " +
                "hành động, mà ở mẫu hình đằng sau chúng'. Tôi học cách nhìn xuyên qua lớp vỏ bề ngoài, " +
                "thấy được bản chất thực sự của mỗi linh hồn.\n" +
                "Giờ đây, tôi bước vào thế giới này không phải để chiến thắng, mà để thấu hiểu. " +
                "Mỗi trận đấu là một câu chuyện mới, mỗi đối thủ là một chương sách chờ được đọc.";
    }

    @Override
    public String getPersonality() {
        return "Triết học, thấu hiểu, và có khả năng tiên tri hành vi";
    }
}

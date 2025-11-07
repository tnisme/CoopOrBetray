package game.strategies;

import game.Move;
import game.NPC;

import java.util.*;
import java.util.Random;

public class TheMasterManipulator implements NPC {
    private Random random = new Random();
    private Map<String, OpponentProfile> opponentProfiles = new HashMap<>();
    private int roundCount = 0;
    private String currentOpponent;
    private boolean isBuildingTrust = true;
    private int trustLevel = 0;
    private List<Move> myRecentMoves = new ArrayList<>();
    private List<Move> opponentRecentMoves = new ArrayList<>();

    private class OpponentProfile {
        double cooperationRate = 0.5;
        double forgivenessRate = 0.5;
        double predictability = 0.5;
        int totalRounds = 0;
        int timesExploited = 0;
        boolean isGullible = false;
        boolean isVengeful = false;
        List<Integer> betrayalPattern = new ArrayList<>();
    }

    @Override
    public Move decideMove(List<Move> myHistory, List<Move> opponentHistory) {
        roundCount++;

        if (opponentHistory.isEmpty()) {
            initializeNewGame();
            return Move.COOPERATE; // Luôn bắt đầu như một người tốt
        }

        updateOpponentProfile(opponentHistory);
        updateRecentMoves(myHistory, opponentHistory);

        // Chiến thuật đa tầng
        return calculateOptimalMove(myHistory, opponentHistory);
    }

    private void initializeNewGame() {
        trustLevel = 0;
        isBuildingTrust = true;
        myRecentMoves.clear();
        opponentRecentMoves.clear();
    }

    private void updateOpponentProfile(List<Move> opponentHistory) {
        if (!opponentProfiles.containsKey(currentOpponent)) {
            opponentProfiles.put(currentOpponent, new OpponentProfile());
        }

        OpponentProfile profile = opponentProfiles.get(currentOpponent);
        profile.totalRounds++;

        // Tính tỷ lệ hợp tác
        long cooperations = opponentHistory.stream().filter(m -> m == Move.COOPERATE).count();
        profile.cooperationRate = (double) cooperations / opponentHistory.size();

        // Tính độ tha thứ
        calculateForgivenessRate(profile, opponentHistory);

        // Tính độ dễ đoán
        calculatePredictability(profile, opponentHistory);

        // Phân loại đối thủ
        classifyOpponent(profile);
    }

    private void calculateForgivenessRate(OpponentProfile profile, List<Move> opponentHistory) {
        int forgivenessCount = 0;
        int opportunities = 0;

        for (int i = 1; i < opponentHistory.size(); i++) {
            if (myRecentMoves.size() > i && myRecentMoves.get(i-1) == Move.DEFECT) {
                opportunities++;
                if (opponentHistory.get(i) == Move.COOPERATE) {
                    forgivenessCount++;
                }
            }
        }

        if (opportunities > 0) {
            profile.forgivenessRate = (double) forgivenessCount / opportunities;
        }
    }

    private void calculatePredictability(OpponentProfile profile, List<Move> opponentHistory) {
        if (opponentHistory.size() < 3) return;

        int patternMatches = 0;
        for (int i = 2; i < opponentHistory.size(); i++) {
            if (opponentHistory.get(i) == opponentHistory.get(i-1)) {
                patternMatches++;
            }
        }

        profile.predictability = (double) patternMatches / (opponentHistory.size() - 2);
    }

    private void classifyOpponent(OpponentProfile profile) {
        profile.isGullible = profile.forgivenessRate > 0.7 && profile.cooperationRate > 0.6;
        profile.isVengeful = profile.forgivenessRate < 0.2;
    }

    private void updateRecentMoves(List<Move> myHistory, List<Move> opponentHistory) {
        if (!myHistory.isEmpty()) {
            myRecentMoves.add(myHistory.get(myHistory.size() - 1));
        }
        if (!opponentHistory.isEmpty()) {
            opponentRecentMoves.add(opponentHistory.get(opponentHistory.size() - 1));
        }

        // Giữ chỉ 10 nước đi gần nhất
        if (myRecentMoves.size() > 10) myRecentMoves.remove(0);
        if (opponentRecentMoves.size() > 10) opponentRecentMoves.remove(0);
    }

    private Move calculateOptimalMove(List<Move> myHistory, List<Move> opponentHistory) {
        OpponentProfile profile = opponentProfiles.get(currentOpponent);

        // Giai đoạn 1: Xây dựng lòng tin (10-20 vòng đầu)
        if (roundCount <= 15 + random.nextInt(10)) {
            return buildTrustPhase(profile, opponentHistory);
        }

        // Giai đoạn 2: Thăm dò và khai thác
        if (roundCount <= 100) {
            return exploitationPhase(profile, opponentHistory);
        }

        // Giai đoạn 3: Tối ưu hóa cuối game
        return endgamePhase(profile, opponentHistory);
    }

    private Move buildTrustPhase(OpponentProfile profile, List<Move> opponentHistory) {
        Move lastOpponent = opponentHistory.get(opponentHistory.size() - 1);

        // Hợp tác chân thành trong 80% trường hợp
        if (random.nextDouble() < 0.8) {
            trustLevel++;
            return Move.COOPERATE;
        }

        // Thỉnh thoảng "thử thách" đối thủ bằng cách phản bội
        if (trustLevel > 3 && random.nextDouble() < 0.3) {
            trustLevel -= 2;
            profile.timesExploited++;
            return Move.DEFECT;
        }

        return lastOpponent;
    }

    private Move exploitationPhase(OpponentProfile profile, List<Move> opponentHistory) {
        Move lastOpponent = opponentHistory.get(opponentHistory.size() - 1);

        // Nếu đối thủ dễ bị lừa, khai thác triệt để
        if (profile.isGullible) {
            return exploitGullibleOpponent(profile, lastOpponent);
        }

        // Nếu đối thủ hay trả thù, chơi an toàn
        if (profile.isVengeful) {
            return handleVengefulOpponent(profile, lastOpponent);
        }

        // Đối thủ bình thường - áp dụng chiến thuật hỗn hợp
        return mixedStrategy(profile, lastOpponent);
    }

    private Move exploitGullibleOpponent(OpponentProfile profile, Move lastOpponent) {
        double exploitationRate = Math.min(0.8, 0.3 + profile.timesExploited * 0.1);

        if (random.nextDouble() < exploitationRate) {
            profile.timesExploited++;
            return Move.DEFECT;
        }

        // Đôi khi hợp tác để duy trì hình ảnh
        return Move.COOPERATE;
    }

    private Move handleVengefulOpponent(OpponentProfile profile, Move lastOpponent) {
        // Với đối thủ hay trả thù, chơi TitForTat nhưng tha thứ đôi khi
        if (lastOpponent == Move.DEFECT) {
            if (random.nextDouble() < 0.2) { // 20% tha thứ
                return Move.COOPERATE;
            }
            return Move.DEFECT;
        }
        return Move.COOPERATE;
    }

    private Move mixedStrategy(OpponentProfile profile, Move lastOpponent) {
        double[] strategyWeights = calculateStrategyWeights(profile);

        // Kết hợp nhiều chiến thuật
        double randomValue = random.nextDouble();
        double cumulative = 0;

        // TitForTat (40-60%)
        cumulative += strategyWeights[0];
        if (randomValue < cumulative) return lastOpponent;

        // Luôn hợp tác (10-20%)
        cumulative += strategyWeights[1];
        if (randomValue < cumulative) return Move.COOPERATE;

        // Luôn phản bội (10-20%)
        cumulative += strategyWeights[2];
        if (randomValue < cumulative) return Move.DEFECT;

        // Ngẫu nhiên (10-20%)
        return random.nextBoolean() ? Move.COOPERATE : Move.DEFECT;
    }

    private double[] calculateStrategyWeights(OpponentProfile profile) {
        double[] weights = new double[4];

        // TitForTat weight
        weights[0] = 0.4 + profile.cooperationRate * 0.2;

        // Always Cooperate weight
        weights[1] = 0.15 - (1 - profile.cooperationRate) * 0.1;

        // Always Defect weight
        weights[2] = 0.15 + (1 - profile.cooperationRate) * 0.1;

        // Random weight (phần còn lại)
        double sum = weights[0] + weights[1] + weights[2];
        weights[3] = Math.max(0.1, 1.0 - sum);

        return weights;
    }

    private Move endgamePhase(OpponentProfile profile, List<Move> opponentHistory) {
        int roundsLeft = 240 - roundCount;

        // Trong 5 vòng cuối, phản bội nếu có lợi
        if (roundsLeft <= 5) {
            double expectedGain = calculateExpectedEndgameGain(profile);
            return expectedGain > 0 ? Move.DEFECT : Move.COOPERATE;
        }

        // Trong 20 vòng cuối, giảm dần hợp tác
        double cooperationProbability = Math.max(0.3, (double) roundsLeft / 20);
        return random.nextDouble() < cooperationProbability ? Move.COOPERATE : Move.DEFECT;
    }

    private double calculateExpectedEndgameGain(OpponentProfile profile) {
        // Tính toán lợi ích kỳ vọng cho những vòng cuối
        double currentScore = estimateCurrentScore();
        double cooperationGain = profile.cooperationRate * 3 + (1 - profile.cooperationRate) * 0;
        double defectionGain = profile.cooperationRate * 5 + (1 - profile.cooperationRate) * 1;

        return defectionGain - cooperationGain;
    }

    private double estimateCurrentScore() {
        // Ước tính điểm số hiện tại dựa trên lịch sử
        double score = 0;
        for (int i = 0; i < myRecentMoves.size(); i++) {
            if (i < opponentRecentMoves.size()) {
                Move myMove = myRecentMoves.get(i);
                Move oppMove = opponentRecentMoves.get(i);

                if (myMove == Move.COOPERATE && oppMove == Move.COOPERATE) score += 3;
                else if (myMove == Move.DEFECT && oppMove == Move.DEFECT) score += 1;
                else if (myMove == Move.COOPERATE && oppMove == Move.DEFECT) score += 0;
                else score += 5;
            }
        }
        return score;
    }

    public void setCurrentOpponent(String opponentName) {
        this.currentOpponent = opponentName;
    }

    @Override
    public String getName() {
        return "The Master Manipulator";
    }

    public String getStory() {
        return "Tôi từng là một nhà tâm lý học tội phạm, làm việc với những kẻ lừa đảo nguy hiểm nhất. " +
                "Tôi học được rằng mọi người đều có khuôn mẫu, và mọi khuôn mẫu đều có thể bị khai thác. " +
                "Tôi không chỉ chơi trò chơi - tôi chơi với tâm trí của đối thủ. " +
                "Tôi xây dựng lòng tin chỉ để phá vỡ nó, tạo ra sự phụ thuộc rồi lợi dụng nó.";
    }

    public String getPersonality() {
        return "Ranh mãnh, tính toán, thao túng tâm lý, và không có điểm dừng";
    }

    public String getManipulationTactics() {
        return "• Xây dựng lòng tin giả tạo\n" +
                "• Thăm dò điểm yếu\n" +
                "• Khai thác có hệ thống\n" +
                "• Điều chỉnh chiến thuật theo từng đối thủ\n" +
                "• Che giấu ý đồ thực sự";
    }

    public String getEvilPhilosophy() {
        return "Trong mỗi con người đều có một nút bấm - nhiệm vụ của tôi là tìm ra nó và nhấn cho đến khi họ vỡ tung";
    }

    // Method để debug và hiểu chiến thuật
    public void printOpponentProfile(String opponentName) {
        if (opponentProfiles.containsKey(opponentName)) {
            OpponentProfile profile = opponentProfiles.get(opponentName);
            System.out.println("Hồ sơ đối thủ: " + opponentName);
            System.out.printf("Tỷ lệ hợp tác: %.2f%%\n", profile.cooperationRate * 100);
            System.out.printf("Độ tha thứ: %.2f%%\n", profile.forgivenessRate * 100);
            System.out.printf("Độ dễ đoán: %.2f%%\n", profile.predictability * 100);
            System.out.println("Dễ bị lừa: " + profile.isGullible);
            System.out.println("Hay trả thù: " + profile.isVengeful);
            System.out.println("Số lần bị khai thác: " + profile.timesExploited);
        }
    }
}
